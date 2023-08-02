package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.BookChapterRespDto;
import cn.deng.novel.entity.BookChapter;
import cn.deng.novel.mapper.BookChapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author Deng
 * @date 2023/7/31
 * @description
 */
@Component
@RequiredArgsConstructor
public class BookChapterCacheManager {

    private final BookChapterMapper bookChapterMapper;

    /**
     * 查询小说章节信息，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,value = CacheConstants.BOOK_CHAPTER_CACHE_NAME)
    public BookChapterRespDto getChapter(Long chapterId){
        BookChapter bookChapter=bookChapterMapper.selectById(chapterId);
        return BookChapterRespDto.builder()
                .id(chapterId)
                .bookId(bookChapter.getBookId())
                .chapterNum(bookChapter.getChapterNum())
                .chapterName(bookChapter.getChapterName())
                .chapterWordCount(bookChapter.getWordCount())
                .chapterUpdateTime(bookChapter.getUpdateTime())
                .isVip(bookChapter.getIsVip())
                .build();
    }

    /**
     * 调用此方法自动清除小说章节信息的缓存
     */
    @CacheEvict(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER, value = CacheConstants.BOOK_CHAPTER_CACHE_NAME)
    public void evictBookChapterCache(Long chapterId) {
    }
}
