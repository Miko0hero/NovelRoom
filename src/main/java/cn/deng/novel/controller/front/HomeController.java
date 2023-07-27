package cn.deng.novel.controller.front;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.HomeBookRespDto;
import cn.deng.novel.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/27
 * @description 前台门户-首页模块 API 控制器
 */
@RestController
@RequestMapping(ApiRouterConstants.API_FRONT_HOME_URL_PREFIX)
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    /**
     * 首页小说推荐查询接口
     * @return  推荐小说列表
     */
    @GetMapping("/books")
    public RestResp<List<HomeBookRespDto>> listHomeBooks(){
        return homeService.listHomeBooks();
    }
}
