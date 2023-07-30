package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.HomeFriendLinkRespDto;
import cn.deng.novel.entity.HomeFriendLink;
import cn.deng.novel.mapper.HomeFriendLinkMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/30
 * @description 友情链接缓存管理类
 */
@Component
@RequiredArgsConstructor
public class FriendLinkCacheManager {
    private final HomeFriendLinkMapper homeFriendLinkMapper;

    /**
     * 友情链接列表查询，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER,value = CacheConstants.HOME_FRIEND_LINK_CACHE_NAME)
    public List<HomeFriendLinkRespDto> listFriendLinks() {
        LambdaQueryWrapper<HomeFriendLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(HomeFriendLink::getSort);
        return homeFriendLinkMapper.selectList(wrapper).stream().map(homeFriendLink -> {
            HomeFriendLinkRespDto homeFriendLinkRespDto = new HomeFriendLinkRespDto();
            homeFriendLinkRespDto.setLinkName(homeFriendLink.getLinkName());
            homeFriendLinkRespDto.setLinkUrl(homeFriendLink.getLinkUrl());
            return homeFriendLinkRespDto;
        }).collect(Collectors.toList());
    }
}
