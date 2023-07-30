package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.NewsInfoRespDto;
import cn.deng.novel.manager.cache.NewsCacheManager;
import cn.deng.novel.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/30
 * @description
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsCacheManager newsCacheManager;
    @Override
    public RestResp<List<NewsInfoRespDto>> listLatestNews() {
        return RestResp.success(newsCacheManager.listLatestNews());
    }
}
