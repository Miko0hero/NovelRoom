package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.BookCategoryRespDto;
import cn.deng.novel.entity.BookCategory;
import cn.deng.novel.mapper.BookCategoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/28
 * @description
 */
@Component
@RequiredArgsConstructor
public class BookCategoryCacheManager {

    private final BookCategoryMapper bookCategoryMapper;

    /**
     * 根据作品方向查询小说分类列表，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER, value = CacheConstants.BOOK_CATEGORY_LIST_CACHE_NAME)
    public List<BookCategoryRespDto> listCategory(Integer workDirection) {
        LambdaQueryWrapper<BookCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookCategory::getWorkDirection, workDirection);
        return bookCategoryMapper.selectList(wrapper).stream().map(bookCategory ->
                BookCategoryRespDto.builder()
                        .id(bookCategory.getId())
                        .name(bookCategory.getName())
                        .build()).collect(Collectors.toList());
    }
}
