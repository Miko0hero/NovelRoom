package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.HomeBookRespDto;
import cn.deng.novel.entity.BookInfo;
import cn.deng.novel.entity.HomeBook;
import cn.deng.novel.mapper.BookInfoMapper;
import cn.deng.novel.mapper.HomeBookMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/27
 * @description 首页推荐小说缓存管理类，包含一个查询方法
 */
@Component
@RequiredArgsConstructor
public class HomeBookCacheManager {

    private final HomeBookMapper homeBookMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询首页小说推荐，缓存中存在数据则直接查缓存
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER,value = CacheConstants.HOME_BOOK_CACHE_NAME)
    public List<HomeBookRespDto> listHomeBooks() {
        //从首页小说推荐表中查询,根据sort升序
        LambdaQueryWrapper<HomeBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(HomeBook::getSort);
        List<HomeBook> homeBookList = homeBookMapper.selectList(wrapper);
        //获取小说的ID列表
        if (!homeBookList.isEmpty()) {
            List<Long> bookIds = homeBookList.stream().map(HomeBook::getBookId).collect(Collectors.toList());
            //根据小说ID列表查询出所有相关的小说信息
            LambdaQueryWrapper<BookInfo> bookInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            bookInfoLambdaQueryWrapper.in(BookInfo::getId, bookIds);
            List<BookInfo> bookInfoList = bookInfoMapper.selectList(bookInfoLambdaQueryWrapper);

            //组装列表数据并返回
            if (!CollectionUtils.isEmpty(bookInfoList)) {
                //Id-BookInfo的map
                Map<Long, BookInfo> bookInfoMap = bookInfoList.stream().collect(Collectors.toMap(BookInfo::getId, Function.identity()));
                List<HomeBookRespDto> homeBookRespDtoList = homeBookList.stream().map(
                        homeBook -> {
                            BookInfo bookInfo = bookInfoMap.get(homeBook.getBookId());
                            HomeBookRespDto homeBookRespDto = HomeBookRespDto.builder()
                                    .bookId(homeBook.getBookId())
                                    .bookName(bookInfo.getBookName())
                                    .type(homeBook.getType())
                                    .authorName(bookInfo.getAuthorName())
                                    .picUrl(bookInfo.getPicUrl())
                                    .bookDesc(bookInfo.getBookDesc())
                                    .build();
                            return homeBookRespDto;
                        }
                ).collect(Collectors.toList());
                return homeBookRespDtoList;
            }
        }
        return Collections.emptyList();
    }
}
