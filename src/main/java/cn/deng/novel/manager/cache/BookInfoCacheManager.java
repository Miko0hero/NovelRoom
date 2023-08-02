package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.BookInfoRespDto;
import cn.deng.novel.entity.BookChapter;
import cn.deng.novel.entity.BookInfo;
import cn.deng.novel.mapper.BookChapterMapper;
import cn.deng.novel.mapper.BookInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/31
 * @description 小说信息缓存管理类
 */
@Component
@RequiredArgsConstructor
public class BookInfoCacheManager {

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

    /**
     * 从缓存中查询小说信息（先判断缓存中是否已存在，存在则直接从缓存中取，否则执行方法体中的逻辑后缓存结果）
     */
    public BookInfoRespDto getBookInfo(Long id) {
        return cachePutBookInfo(id);
    }

    /**
     * 缓存小说信息，不管缓存中是否存在，都会执行方法体并更新缓存
     *
     * @param id BookId
     * @return 小说信息响应Dto
     */
    @CachePut(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER, value = CacheConstants.BOOK_INFO_CACHE_NAME)
    public BookInfoRespDto cachePutBookInfo(Long id) {
        //查询小说的基础信息
        BookInfo bookInfo = bookInfoMapper.selectById(id);
        LambdaQueryWrapper<BookChapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookChapter::getBookId, id);
        wrapper.orderByAsc(BookChapter::getChapterNum);
        wrapper.last("limit 1");
        BookChapter firstBookChapter = bookChapterMapper.selectOne(wrapper);
        // 组装响应对象
        return BookInfoRespDto.builder()
                .id(bookInfo.getId())
                .bookName(bookInfo.getBookName())
                .bookDesc(bookInfo.getBookDesc())
                .bookStatus(bookInfo.getBookStatus())
                .authorId(bookInfo.getAuthorId())
                .authorName(bookInfo.getAuthorName())
                .categoryId(bookInfo.getCategoryId())
                .categoryName(bookInfo.getCategoryName())
                .commentCount(bookInfo.getCommentCount())
                .firstChapterId(firstBookChapter.getId())
                .lastChapterId(bookInfo.getLastChapterId())
                .picUrl(bookInfo.getPicUrl())
                .visitCount(bookInfo.getVisitCount())
                .wordCount(bookInfo.getWordCount())
                .build();
    }

    /**
     * 空方法，调用此方法清除小说信息缓存
     * @param bookId BookId
     */
    @CacheEvict(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER, value = CacheConstants.BOOK_INFO_CACHE_NAME)
    public void evictBookInfoCache(Long bookId) {
        // 调用此方法自动清除小说信息的缓存
    }

    /**
     * 查询每个小说分类下的最新更新的500个小说Id，放入缓存1个小时
     * @param categoryId
     * @return
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,value = CacheConstants.LAST_UPDATE_BOOK_ID_LIST_CACHE_NAME)
    public List<Long> getLastUpdateIdList(Long categoryId){
        LambdaQueryWrapper<BookInfo> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(BookInfo::getCategoryId,categoryId)
                .gt(BookInfo::getWordCount,0)
                .orderByDesc(BookInfo::getLastChapterUpdateTime)
                .last("limit 500");
        return bookInfoMapper.selectList(wrapper).stream().map(BookInfo::getId).collect(Collectors.toList());

    }
}
