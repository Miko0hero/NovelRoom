package cn.deng.novel.service.impl;

import cn.deng.novel.core.auth.UserHolder;
import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import cn.deng.novel.core.common.request.PageReqDto;
import cn.deng.novel.core.common.response.PageRespDto;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.BookAddReqDto;
import cn.deng.novel.dto.req.ChapterAddReqDto;
import cn.deng.novel.dto.req.ChapterUpdateReqDto;
import cn.deng.novel.dto.resp.*;
import cn.deng.novel.entity.*;
import cn.deng.novel.manager.cache.*;
import cn.deng.novel.mapper.*;
import cn.deng.novel.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/28
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Integer REC_BOOK_COUNT = 4;

    private final BookCategoryCacheManager bookCategoryCacheManager;

    private final BookRankCacheManager bookRankCacheManager;

    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookChapterMapper bookChapterMapper;

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterCacheManager bookChapterCacheManager;

    private final BookContentCacheManager bookContentCacheManager;

    private final BookCommentMapper bookCommentMapper;

    private final UserInfoMapper userInfoMapper;

    private final AuthorInfoCacheManager authorInfoCacheManager;

    private final BookContentMapper bookContentMapper;

    @Override
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return RestResp.success(bookCategoryCacheManager.listCategory(workDirection));
    }

    @Override
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return RestResp.success(bookRankCacheManager.listVisitRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listNewestRankBooks() {
        return RestResp.success(bookRankCacheManager.listNewestRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return RestResp.success(bookRankCacheManager.listUpdateRankBooks());
    }

    @Override
    public RestResp<BookInfoRespDto> getBookById(Long bookId) {
        return RestResp.success(bookInfoCacheManager.getBookInfo(bookId));
    }

    @Override
    public RestResp<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException {
        //获取当前的小说的分类id
        Long categoryId = bookInfoCacheManager.getBookInfo(bookId).getCategoryId();
        //获取此分类下的最近更新小说
        List<Long> lastUpdateIdList = bookInfoCacheManager.getLastUpdateIdList(categoryId);
        List<BookInfoRespDto> respDtoList = new ArrayList<>();
        List<Integer> recIdIndexList = new ArrayList<>();
        //从中随机选取四个返回
        int count = 0;
        Random rand = SecureRandom.getInstanceStrong();
        while (count < REC_BOOK_COUNT) {
            //设置随机数的范围在0-最近更新小说列表大小，表示推荐小说的索引值
            int recIdIndex = rand.nextInt(lastUpdateIdList.size());
            if (!recIdIndexList.contains(recIdIndex)) {
                recIdIndexList.add(recIdIndex);
                bookId = lastUpdateIdList.get(recIdIndex);
                BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookId);
                respDtoList.add(bookInfo);
                count++;
            }
        }
        return RestResp.success(respDtoList);
    }

    @Override
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        LambdaQueryWrapper<BookChapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookChapter::getBookId, bookId);
        wrapper.orderByAsc(BookChapter::getChapterNum);
        return RestResp.success(bookChapterMapper.selectList(wrapper).stream().map(bookChapter ->
                BookChapterRespDto.builder()
                        .id(bookChapter.getId())
                        .chapterName(bookChapter.getChapterName())
                        .isVip(bookChapter.getIsVip())
                        .build()
        ).collect(Collectors.toList()));
    }

    @Override
    public RestResp<Void> addVisitCount(Long bookId) {
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        bookInfo.setVisitCount(bookInfo.getVisitCount() + 1);
        bookInfoMapper.updateById(bookInfo);
        return RestResp.success();
    }

    @Override
    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(Long bookId) {
        //查询小说信息
        BookInfoRespDto bookInfoRespDto = bookInfoCacheManager.getBookInfo(bookId);
        //查询最新章节信息
        BookChapterRespDto bookChapterRespDto = bookChapterCacheManager.getChapter(bookInfoRespDto.getLastChapterId());
        //查询最新章节内容
        String content = bookContentCacheManager.getBookContent(bookInfoRespDto.getLastChapterId());
        log.info(content.replaceAll("<[^nb>]`>", "").substring(0, 30));
        //查询章节总数
        LambdaQueryWrapper<BookChapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookChapter::getBookId, bookId);
        Long chapterCount = bookChapterMapper.selectCount(wrapper);
        return RestResp.success(BookChapterAboutRespDto.builder()
                .chapterInfo(bookChapterRespDto)
                .chapterTotal(chapterCount)
                .contentSummary(content.substring(0, 300))
                .build());
    }

    @Override
    public RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId) {
        // 查询章节信息
        BookChapterRespDto bookChapter = bookChapterCacheManager.getChapter(chapterId);

        // 查询章节内容
        String content = bookContentCacheManager.getBookContent(chapterId);

        // 查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(bookChapter.getBookId());

        // 组装数据并返回
        return RestResp.success(BookContentAboutRespDto.builder()
                .bookInfo(bookInfo)
                .chapterInfo(bookChapter)
                .bookContent(content)
                .build());
    }

    @Override
    public RestResp<Long> getPreChapterId(Long chapterId) {
        // 查询小说ID 和 章节号
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // 查询上一章信息并返回章节ID
        LambdaQueryWrapper<BookChapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookChapter::getBookId, bookId);
        wrapper.lt(BookChapter::getChapterNum, chapterNum);
        wrapper.orderByDesc(BookChapter::getChapterNum);
        wrapper.last("limit 1");
        return RestResp.success(
                Optional.ofNullable(bookChapterMapper.selectOne(wrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }


    @Override
    public RestResp<Long> getNextChapterId(Long chapterId) {
        // 查询小说ID 和 章节号
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        Long bookId = chapter.getBookId();
        Integer chapterNum = chapter.getChapterNum();

        // 查询上一章信息并返回章节ID
        LambdaQueryWrapper<BookChapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookChapter::getBookId, bookId);
        wrapper.gt(BookChapter::getChapterNum, chapterNum);
        wrapper.orderByAsc(BookChapter::getChapterNum);
        wrapper.last("limit 1");
        return RestResp.success(
                Optional.ofNullable(bookChapterMapper.selectOne(wrapper))
                        .map(BookChapter::getId)
                        .orElse(null)
        );
    }

    @Override
    public RestResp<BookCommentRespDto> listNewestComments(Long bookId) {
        // 查询评论总数
        LambdaQueryWrapper<BookComment> commentQueryWrapper = new LambdaQueryWrapper<>();
        commentQueryWrapper.eq(BookComment::getBookId, bookId);
        Long commentTotal = bookCommentMapper.selectCount(commentQueryWrapper);
        BookCommentRespDto bookCommentRespDto = BookCommentRespDto.builder().commentTotal(commentTotal).build();
        if (commentTotal > 0) {
            // 查询最新的评论列表
            commentQueryWrapper.orderByDesc(BookComment::getCreateTime);
            commentQueryWrapper.last("limit 5");
            List<BookComment> bookComments = bookCommentMapper.selectList(commentQueryWrapper);

            // 查询评论用户信息，并设置需要返回的评论用户名
            List<Long> userIds = bookComments.stream().map(BookComment::getUserId).collect(Collectors.toList());
            //根据用户Id批量查询用户信息列表
            LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(UserInfo::getId, userIds);
            List<UserInfo> userInfos = userInfoMapper.selectList(wrapper);

            Map<Long, UserInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
            List<BookCommentRespDto.CommentInfo> commentInfos = bookComments.stream()
                    .map(v ->
                            {
                                String userName = userInfoMap.get(v.getUserId()).getUsername();
                                return BookCommentRespDto.CommentInfo.builder()
                                        .id(v.getId())
                                        .commentUserId(v.getUserId())
                                        //将用户名（也就是手机号）截取拼接，只展示部分给前端
                                        .commentUser(userName.substring(0, 4) + "****" + userName.substring(0, 4))
                                        .commentUserPhoto(userInfoMap.get(v.getUserId()).getUserPhoto())
                                        .commentContent(v.getCommentContent())
                                        .commentTime(v.getCreateTime()).build();
                            }
                    ).collect(Collectors.toList());
            bookCommentRespDto.setComments(commentInfos);

        } else {
            bookCommentRespDto.setComments(Collections.emptyList());
        }
        return RestResp.success(bookCommentRespDto);
    }

    @Override
    public RestResp<Void> saveComment(UserCommentReqDto dto) {
        LambdaQueryWrapper<BookComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookComment::getUserId, dto.getUserId());
        wrapper.eq(BookComment::getBookId, dto.getBookId());
        if (bookCommentMapper.selectCount((wrapper)) > 0) {
            //用户已发表评论
            return RestResp.fail(ErrorCodeEnum.USER_COMMENTED);
        }
        BookComment bookComment = new BookComment();
        bookComment.setBookId(dto.getBookId());
        bookComment.setUserId(dto.getUserId());
        bookComment.setCommentContent(dto.getCommentContent());
        bookCommentMapper.insert(bookComment);
        return RestResp.success();
    }

    @Override
    public RestResp<Void> deleteComment(Long userId, Long commentId) {
        LambdaQueryWrapper<BookComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookComment::getUserId, userId);
        wrapper.eq(BookComment::getId, commentId);
        bookCommentMapper.delete(wrapper);
        return RestResp.success();
    }

    @Override
    public RestResp<Void> saveBook(BookAddReqDto dto) {
        // 校验小说名是否已存在
        LambdaQueryWrapper<BookInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookInfo::getBookName, dto.getBookName());
        if (bookInfoMapper.selectCount(wrapper) > 0) {
            return RestResp.fail(ErrorCodeEnum.AUTHOR_BOOK_NAME_EXIST);
        }
        BookInfo bookInfo = new BookInfo();
        // 设置作家信息
        AuthorInfoDto author = authorInfoCacheManager.getAuthor(UserHolder.getUserId());
        bookInfo.setAuthorId(author.getId());
        bookInfo.setAuthorName(author.getPenName());
        // 设置其他信息
        bookInfo.setWorkDirection(dto.getWorkDirection());
        bookInfo.setCategoryId(dto.getCategoryId());
        bookInfo.setCategoryName(dto.getCategoryName());
        bookInfo.setBookName(dto.getBookName());
        bookInfo.setPicUrl(dto.getPicUrl());
        bookInfo.setBookDesc(dto.getBookDesc());
        bookInfo.setIsVip(dto.getIsVip());
        bookInfo.setScore(0);
        bookInfo.setCreateTime(LocalDateTime.now());
        bookInfo.setUpdateTime(LocalDateTime.now());
        // 保存小说信息
        bookInfoMapper.insert(bookInfo);
        return RestResp.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResp<Void> saveBookChapter(ChapterAddReqDto dto) {
        // 校验该作品是否属于当前作家
        BookInfo bookInfo = bookInfoMapper.selectById(dto.getBookId());
        if (!Objects.equals(bookInfo.getAuthorId(), UserHolder.getAuthorId())) {
            return RestResp.fail(ErrorCodeEnum.USER_UN_AUTH);
        }
        // 1) 保存章节相关信息到小说章节表
        //  a) 查询最新章节号
        int chapterNum = 0;
        QueryWrapper<BookChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("book_id", dto.getBookId())
                .orderByDesc("chapter_num")
                .last("limit 1");
        BookChapter bookChapter = bookChapterMapper.selectOne(chapterQueryWrapper);
        if (Objects.nonNull(bookChapter)) {
            chapterNum = bookChapter.getChapterNum() + 1;
        }
        //  b) 设置章节相关信息并保存
        BookChapter newBookChapter = new BookChapter();
        newBookChapter.setBookId(dto.getBookId());
        newBookChapter.setChapterName(dto.getChapterName());
        newBookChapter.setChapterNum(chapterNum);
        newBookChapter.setWordCount(dto.getChapterContent().length());
        newBookChapter.setIsVip(dto.getIsVip());
        newBookChapter.setCreateTime(LocalDateTime.now());
        newBookChapter.setUpdateTime(LocalDateTime.now());
        bookChapterMapper.insert(newBookChapter);

        // 2) 保存章节内容到小说内容表
        BookContent bookContent = new BookContent();
        bookContent.setContent(dto.getChapterContent());
        bookContent.setChapterId(newBookChapter.getId());
        bookContent.setCreateTime(LocalDateTime.now());
        bookContent.setUpdateTime(LocalDateTime.now());
        bookContentMapper.insert(bookContent);

        // 3) 更新小说表最新章节信息和小说总字数信息
        //  a) 更新小说表关于最新章节的信息
        BookInfo newBookInfo = new BookInfo();
        newBookInfo.setId(dto.getBookId());
        newBookInfo.setLastChapterId(newBookChapter.getId());
        newBookInfo.setLastChapterName(newBookChapter.getChapterName());
        newBookInfo.setLastChapterUpdateTime(LocalDateTime.now());
        newBookInfo.setWordCount(bookInfo.getWordCount() + newBookChapter.getWordCount());
        newBookChapter.setUpdateTime(LocalDateTime.now());
        bookInfoMapper.updateById(newBookInfo);
        //  b) 清除小说信息缓存
        bookInfoCacheManager.evictBookInfoCache(dto.getBookId());
        //  c) 发送小说信息更新的 MQ 消息
        // amqpMsgManager.sendBookChangeMsg(dto.getBookId());
        return RestResp.success();
    }

    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> listAuthorBooks(PageReqDto dto) {
        IPage<BookInfo> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        LambdaQueryWrapper<BookInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookInfo::getAuthorId, UserHolder.getAuthorId());
        wrapper.orderByDesc(BookInfo::getCreateTime);
        IPage<BookInfo> bookInfoPage = bookInfoMapper.selectPage(page, wrapper);
        return RestResp.success(PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookInfoPage.getRecords().stream().map(v -> BookInfoRespDto.builder()
                        .id(v.getId())
                        .bookName(v.getBookName())
                        .picUrl(v.getPicUrl())
                        .categoryName(v.getCategoryName())
                        .wordCount(v.getWordCount())
                        .visitCount(v.getVisitCount())
                        .updateTime(v.getUpdateTime())
                        .build()).collect(Collectors.toList())));
    }

    @Override
    public RestResp<PageRespDto<BookChapterRespDto>> listBookChapters(Long bookId, PageReqDto dto) {
        IPage<BookChapter> page = new Page<>();
        page.setCurrent(dto.getPageNum());
        page.setSize(dto.getPageSize());
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId)
                .orderByDesc("chapter_num");
        IPage<BookChapter> bookChapterPage = bookChapterMapper.selectPage(page, queryWrapper);
        return RestResp.success(PageRespDto.of(dto.getPageNum(), dto.getPageSize(), page.getTotal(),
                bookChapterPage.getRecords().stream().map(v -> BookChapterRespDto.builder()
                        .id(v.getId())
                        .chapterName(v.getChapterName())
                        .chapterUpdateTime(v.getUpdateTime())
                        .isVip(v.getIsVip())
                        .build()).collect(Collectors.toList())));
    }

    @Override
    public RestResp<PageRespDto<UserCommentRespDto>> listComments(Long userId, PageReqDto pageReqDto) {
        IPage<BookComment> page = new Page<>();
        page.setCurrent(pageReqDto.getPageNum());
        page.setSize(pageReqDto.getPageSize());
        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .orderByDesc("update_time");
        IPage<BookComment> bookCommentPage = bookCommentMapper.selectPage(page, queryWrapper);
        List<BookComment> comments = bookCommentPage.getRecords();
        if (!CollectionUtils.isEmpty(comments)) {
            List<Long> bookIds = comments.stream().map(BookComment::getBookId).collect(Collectors.toList());
            QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
            bookInfoQueryWrapper.in("id", bookIds);
            Map<Long, BookInfo> bookInfoMap = bookInfoMapper.selectList(bookInfoQueryWrapper).stream()
                    .collect(Collectors.toMap(BookInfo::getId, Function.identity()));
            return RestResp.success(PageRespDto.of(pageReqDto.getPageNum(), pageReqDto.getPageSize(), page.getTotal(),
                    comments.stream().map(v -> UserCommentRespDto.builder()
                            .commentContent(v.getCommentContent())
                            .commentBook(bookInfoMap.get(v.getBookId()).getBookName())
                            .commentBookPic(bookInfoMap.get(v.getBookId()).getPicUrl())
                            .commentTime(v.getCreateTime())
                            .build()).collect(Collectors.toList())));

        }
        return RestResp.success(PageRespDto.of(pageReqDto.getPageNum(), pageReqDto.getPageSize(), page.getTotal(),
                Collections.emptyList()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RestResp<Void> deleteBookChapter(Long chapterId) {
        // 1.查询章节信息
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        // 2.查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(chapter.getBookId());
        // 3.删除章节信息
        bookChapterMapper.deleteById(chapterId);
        // 4.删除章节内容
        QueryWrapper<BookContent> bookContentQueryWrapper = new QueryWrapper<>();
        bookContentQueryWrapper.eq("chapter_id", chapterId);
        bookContentMapper.delete(bookContentQueryWrapper);
        // 5.更新小说信息
        BookInfo newBookInfo = new BookInfo();
        newBookInfo.setId(chapter.getBookId());
        newBookInfo.setUpdateTime(LocalDateTime.now());
        newBookInfo.setWordCount(bookInfo.getWordCount() - chapter.getChapterWordCount());
        if (Objects.equals(bookInfo.getLastChapterId(), chapterId)) {
            // 设置最新章节信息
            QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
            bookChapterQueryWrapper.eq("book_id", chapter.getBookId())
                    .orderByDesc("chapter_num")
                    .last("limit 1");
            BookChapter bookChapter = bookChapterMapper.selectOne(bookChapterQueryWrapper);
            Long lastChapterId = 0L;
            String lastChapterName = "";
            LocalDateTime lastChapterUpdateTime = null;
            if (Objects.nonNull(bookChapter)) {
                lastChapterId = bookChapter.getId();
                lastChapterName = bookChapter.getChapterName();
                lastChapterUpdateTime = bookChapter.getUpdateTime();
            }
            newBookInfo.setLastChapterId(lastChapterId);
            newBookInfo.setLastChapterName(lastChapterName);
            newBookInfo.setLastChapterUpdateTime(lastChapterUpdateTime);
        }
        bookInfoMapper.updateById(newBookInfo);
        // 6.清理章节信息缓存
        bookChapterCacheManager.evictBookChapterCache(chapterId);
        // 7.清理章节内容缓存
        bookContentCacheManager.evictBookContentCache(chapterId);
        // 8.清理小说信息缓存
        bookInfoCacheManager.evictBookInfoCache(chapter.getBookId());
        // 9.发送小说信息更新的 MQ 消息
        //  amqpMsgManager.sendBookChangeMsg(chapter.getBookId());
        return RestResp.success();
    }

    @Override
    public RestResp<ChapterContentRespDto> getBookChapter(Long chapterId) {
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        String bookContent = bookContentCacheManager.getBookContent(chapterId);
        return RestResp.success(
                ChapterContentRespDto.builder()
                        .chapterName(chapter.getChapterName())
                        .chapterContent(bookContent)
                        .isVip(chapter.getIsVip())
                        .build());
    }

    @Override
    @Transactional
    public RestResp<Void> updateBookChapter(Long chapterId, ChapterUpdateReqDto dto) {
        // 1.查询章节信息
        BookChapterRespDto chapter = bookChapterCacheManager.getChapter(chapterId);
        // 2.查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfo(chapter.getBookId());
        // 3.更新章节信息
        BookChapter newChapter = new BookChapter();
        newChapter.setId(chapterId);
        newChapter.setChapterName(dto.getChapterName());
        newChapter.setWordCount(dto.getChapterContent().length());
        newChapter.setIsVip(dto.getIsVip());
        newChapter.setUpdateTime(LocalDateTime.now());
        bookChapterMapper.updateById(newChapter);
        // 4.更新章节内容
        BookContent newContent = new BookContent();
        newContent.setContent(dto.getChapterContent());
        newContent.setUpdateTime(LocalDateTime.now());
        QueryWrapper<BookContent> bookContentQueryWrapper = new QueryWrapper<>();
        bookContentQueryWrapper.eq("chapter_id", chapterId);
        bookContentMapper.update(newContent, bookContentQueryWrapper);
        // 5.更新小说信息
        BookInfo newBookInfo = new BookInfo();
        newBookInfo.setId(chapter.getBookId());
        newBookInfo.setUpdateTime(LocalDateTime.now());
        newBookInfo.setWordCount(
                bookInfo.getWordCount() - chapter.getChapterWordCount() + dto.getChapterContent().length());
        if (Objects.equals(bookInfo.getLastChapterId(), chapterId)) {
            // 更新最新章节信息
            newBookInfo.setLastChapterName(dto.getChapterName());
            newBookInfo.setLastChapterUpdateTime(LocalDateTime.now());
        }
        bookInfoMapper.updateById(newBookInfo);
        // 6.清理章节信息缓存
        bookChapterCacheManager.evictBookChapterCache(chapterId);
        // 7.清理章节内容缓存
        bookContentCacheManager.evictBookContentCache(chapterId);
        // 8.清理小说信息缓存
        bookInfoCacheManager.evictBookInfoCache(chapter.getBookId());
        // 9.发送小说信息更新的 MQ 消息
        //  amqpMsgManager.sendBookChangeMsg(chapter.getBookId());
        return RestResp.success();
    }

    @Override
    public RestResp<Void> updateComment(Long userId, Long id, String content) {
        LambdaQueryWrapper<BookComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookComment::getUserId, userId);
        wrapper.eq(BookComment::getId, id);
        BookComment bookComment = new BookComment();
        bookComment.setCommentContent(content);
        bookCommentMapper.update(bookComment, wrapper);
        return RestResp.success();
    }
}
