package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.BookRankRespDto;
import cn.deng.novel.entity.BookInfo;
import cn.deng.novel.mapper.BookInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/29
 * @description
 */
@Component
@RequiredArgsConstructor
public class BookRankCacheManager {
    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询小说点击列表，并放入Redis缓存中
     */
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER, value = CacheConstants.BOOK_VISIT_RANK_CACHE_NAME)
    public List<BookRankRespDto> listVisitRankBooks() {
        LambdaQueryWrapper<BookInfo> bookInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bookInfoLambdaQueryWrapper.orderByDesc(BookInfo::getVisitCount);
        return listRankBooks(bookInfoLambdaQueryWrapper);
    }

    /**
     * 查询小说新书榜列表，并放入Redis缓存中
     */
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER, value = CacheConstants.BOOK_NEWEST_RANK_CACHE_NAME)
    public List<BookRankRespDto> listNewestRankBooks() {
        LambdaQueryWrapper<BookInfo> bookInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bookInfoLambdaQueryWrapper.orderByDesc(BookInfo::getCreateTime);
        return listRankBooks(bookInfoLambdaQueryWrapper);
    }


    /**
     * 查询小说更新榜列表，并放入Redis缓存中
     */
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER, value = CacheConstants.BOOK_UPDATE_RANK_CACHE_NAME)
    public List<BookRankRespDto> listUpdateRankBooks() {
        LambdaQueryWrapper<BookInfo> bookInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bookInfoLambdaQueryWrapper.orderByDesc(BookInfo::getUpdateTime);
        return listRankBooks(bookInfoLambdaQueryWrapper);
    }

    /**
     * 组装小说榜单信息Dto的通用方法
     */
    private List<BookRankRespDto> listRankBooks(LambdaQueryWrapper<BookInfo> bookInfoLambdaWrapper) {
        //只显示小说字数不为0的行
        bookInfoLambdaWrapper.gt(BookInfo::getWordCount, 0);
        //sql最后面拼接limit语句，只输出数据的前30条
        bookInfoLambdaWrapper.last("limit 30");
        return bookInfoMapper.selectList(bookInfoLambdaWrapper).stream().map(bookInfo ->
                BookRankRespDto.builder()
                        .id(bookInfo.getId())
                        .categoryId(bookInfo.getCategoryId())
                        .categoryName(bookInfo.getCategoryName())
                        .bookName(bookInfo.getBookName())
                        .authorName(bookInfo.getAuthorName())
                        .picUrl(bookInfo.getPicUrl())
                        .bookDesc(bookInfo.getBookDesc())
                        .lastChapterName(bookInfo.getLastChapterName())
                        .lastChapterUpdateTime(bookInfo.getLastChapterUpdateTime())
                        .wordCount(bookInfo.getWordCount()).build()).collect(Collectors.toList());


    }
}
