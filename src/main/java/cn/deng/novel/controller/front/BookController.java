package cn.deng.novel.controller.front;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.BookCategoryRespDto;
import cn.deng.novel.dto.resp.BookRankRespDto;
import cn.deng.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/28
 * @description 前台门户-小说模块 API控制器
 */
@RestController
@RequestMapping(ApiRouterConstants.API_FRONT_BOOK_URL_PREFIX)
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * 小说分类列表查询接口
     */
    @GetMapping("/category/list")
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return bookService.listCategory(workDirection);
    }

    /**
     * 小说点击榜查询接口
     */
    @GetMapping("visit_rank")
    public RestResp<List<BookRankRespDto>> listVisitRankBooks() {
        return bookService.listVisitRankBooks();
    }

    /**
     * 小说新书榜查询接口
     */
    @GetMapping("newest_rank")
    public RestResp<List<BookRankRespDto>> listNewestRankBooks() {
        return bookService.listNewestRankBooks();
    }

    /**
     * 小说更新榜查询接口
     */
    @GetMapping("update_rank")
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return bookService.listUpdateRankBooks();
    }

}
