package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.entity.BookContent;
import cn.deng.novel.mapper.BookContentMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class BookContentCacheManager {

    private final BookContentMapper bookContentMapper;

    /**
     * 查询小说内容，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER, value = CacheConstants.BOOK_CONTENT_CACHE_NAME)
    public String getBookContent(Long chapterId) {
        LambdaQueryWrapper<BookContent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookContent::getChapterId, chapterId);
        BookContent bookContent = bookContentMapper.selectOne(wrapper);
        return bookContent.getContent();
    }

    @CacheEvict(cacheManager = CacheConstants.REDIS_CACHE_MANAGER,
            value = CacheConstants.BOOK_CONTENT_CACHE_NAME)
    public void evictBookContentCache(Long chapterId) {
        // 调用此方法自动清除小说内容信息的缓存
    }
}
