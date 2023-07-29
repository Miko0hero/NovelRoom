package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.BookCategoryRespDto;
import cn.deng.novel.dto.resp.BookRankRespDto;
import cn.deng.novel.manager.cache.BookCategoryManager;
import cn.deng.novel.manager.cache.BookRankCacheManager;
import cn.deng.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/28
 * @description
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookCategoryManager bookCategoryManager;

    private final BookRankCacheManager bookRankCacheManager;

    @Override
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return RestResp.success(bookCategoryManager.listCategory(workDirection));
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
}
