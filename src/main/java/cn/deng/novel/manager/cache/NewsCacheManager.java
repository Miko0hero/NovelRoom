package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.NewsInfoRespDto;
import cn.deng.novel.entity.NewsInfo;
import cn.deng.novel.mapper.NewsInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/30
 * @description
 */
@Component
@RequiredArgsConstructor
public class NewsCacheManager {

    private final NewsInfoMapper newsInfoMapper;

    /**
     * 最新新闻列表查询
     */
    @Cacheable(cacheManager = CacheConstants.CAFFEINE_CACHE_MANAGER, value = CacheConstants.LATEST_NEWS_CACHE_NAME)
    public List<NewsInfoRespDto> listLatestNews() {
        //从新闻列表中查询最新发布的两条新闻
        LambdaQueryWrapper<NewsInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(NewsInfo::getCreateTime).last("limit 2");
        return newsInfoMapper.selectList(wrapper).stream().map(
                newsInfo -> NewsInfoRespDto.builder()
                        .id(newsInfo.getId())
                        .categoryId(newsInfo.getCategoryId())
                        .categoryName(newsInfo.getCategoryName())
                        .title(newsInfo.getTitle())
                        .sourceName(newsInfo.getSourceName())
                        .updateTime(newsInfo.getUpdateTime())
                        .build()).collect(Collectors.toList());
    }
}

