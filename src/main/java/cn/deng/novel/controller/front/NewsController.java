package cn.deng.novel.controller.front;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.NewsInfoRespDto;
import cn.deng.novel.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/30
 * @description 前台门户-新闻模块
 */
@RestController
@RequestMapping(ApiRouterConstants.API_FRONT_NEWS_URL_PREFIX)
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    /**
     * 查询最新的两条新闻
     */
    @GetMapping("latest_list")
    public RestResp<List<NewsInfoRespDto>> listLatestNews(){
        return newsService.listLatestNews();
    }
}
