package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.NewsInfoRespDto;
import cn.deng.novel.entity.NewsContent;
import cn.deng.novel.entity.NewsInfo;
import cn.deng.novel.manager.cache.NewsCacheManager;
import cn.deng.novel.mapper.NewsContentMapper;
import cn.deng.novel.mapper.NewsInfoMapper;
import cn.deng.novel.service.NewsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    private final NewsInfoMapper newsInfoMapper;

    private final NewsContentMapper newsContentMapper;
    @Override
    public RestResp<List<NewsInfoRespDto>> listLatestNews() {
        return RestResp.success(newsCacheManager.listLatestNews());
    }

    @Override
    public RestResp<NewsInfoRespDto> getNews(Long id) {
        NewsInfo newsInfo=newsInfoMapper.selectById(id);
        LambdaQueryWrapper<NewsContent> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(NewsContent::getNewsId,id);
        NewsContent newsContent=newsContentMapper.selectOne(wrapper);
        return RestResp.success(NewsInfoRespDto.builder()
                .title(newsInfo.getTitle())
                .sourceName(newsInfo.getSourceName())
                .updateTime(newsInfo.getUpdateTime())
                .content(newsContent.getContent())
                .build());
    }
}
