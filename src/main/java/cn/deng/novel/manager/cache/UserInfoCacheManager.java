package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.UserInfoDto;
import cn.deng.novel.entity.UserInfo;
import cn.deng.novel.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Deng
 * @date 2023/8/3
 * @description
 */
@Component
@RequiredArgsConstructor
public class UserInfoCacheManager {

    private final UserInfoMapper userInfoMapper;

    /**
     * 查询用户信息，并放入缓存中
     */
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER, value = CacheConstants.USER_INFO_CACHE_NAME)
    public UserInfoDto getUser(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return UserInfoDto.builder()
                .id(userInfo.getId())
                .status(userInfo.getStatus()).build();
    }

}
