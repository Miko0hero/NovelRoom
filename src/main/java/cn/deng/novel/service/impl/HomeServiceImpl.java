package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.HomeBookRespDto;
import cn.deng.novel.dto.resp.HomeFriendLinkRespDto;
import cn.deng.novel.manager.cache.FriendLinkCacheManager;
import cn.deng.novel.manager.cache.HomeBookCacheManager;
import cn.deng.novel.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/27
 * @description
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeBookCacheManager homeBookCacheManager;

    private final FriendLinkCacheManager friendLinkCacheManager;
    @Override
    public RestResp<List<HomeBookRespDto>> listHomeBooks() {
        return RestResp.success(homeBookCacheManager.listHomeBooks());
    }

    @Override
    public RestResp<List<HomeFriendLinkRespDto>> listHomeFriendLinks() {
        return RestResp.success(friendLinkCacheManager.listFriendLinks());
    }
}
