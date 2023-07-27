package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.HomeBookRespDto;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/27
 * @description 首页模块
 */
public interface HomeService {
    /**
     * 查询首页小说推荐列表
     * @return  返回推荐小说列表
     */
    RestResp<List<HomeBookRespDto>> listHomeBooks();
}
