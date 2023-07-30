package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.NewsInfoRespDto;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/30
 * @description
 */
public interface NewsService {
    /**
     * 最新新闻列表查询
     */
    RestResp<List<NewsInfoRespDto>> listLatestNews();
}
