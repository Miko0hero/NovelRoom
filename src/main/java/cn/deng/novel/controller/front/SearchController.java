package cn.deng.novel.controller.front;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.response.PageRespDto;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.BookSearchReqDto;
import cn.deng.novel.dto.resp.BookInfoRespDto;
import cn.deng.novel.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Deng
 * @date 2023/7/29
 * @description
 */
@RestController
@RequestMapping(ApiRouterConstants.API_FRONT_SEARCH_URL_PREFIX)
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    /**
     * 小说搜索接口
     */
    @GetMapping("/books")
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks( BookSearchReqDto condition){
        return searchService.searchBooks(condition);
    }
}
