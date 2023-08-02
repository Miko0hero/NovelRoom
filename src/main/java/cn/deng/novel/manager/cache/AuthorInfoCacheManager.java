package cn.deng.novel.manager.cache;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.dto.resp.AuthorInfoDto;
import cn.deng.novel.entity.AuthorInfo;
import cn.deng.novel.mapper.AuthorInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Deng
 * @date 2023/8/1
 * @description
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorInfoCacheManager {

    private final AuthorInfoMapper authorInfoMapper;

    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER, value = CacheConstants.AUTHOR_INFO_CACHE_NAME, unless = "#result == null")
    public AuthorInfoDto getAuthor(Long userId) {
        LambdaQueryWrapper<AuthorInfo> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(AuthorInfo::getUserId,userId);
        wrapper.last("limit 1");
        AuthorInfo authorInfo=authorInfoMapper.selectOne((wrapper));
        if (Objects.isNull(authorInfo)) {
            return null;
        }
        return  AuthorInfoDto.builder()
                .id(authorInfo.getId())
                .penName(authorInfo.getPenName())
                .status(authorInfo.getStatus()).build();
    }

    /**
     * 调用此方法自动清除作家信息的缓存
     */
    @CacheEvict(cacheManager = CacheConstants.REDIS_CACHE_MANAGER,
            value = CacheConstants.AUTHOR_INFO_CACHE_NAME)
    public void evictAuthorCache() {
    }
}
