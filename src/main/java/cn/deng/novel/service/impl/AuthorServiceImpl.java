package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.AuthorRegisterReqDto;
import cn.deng.novel.dto.resp.AuthorInfoDto;
import cn.deng.novel.entity.AuthorInfo;
import cn.deng.novel.manager.cache.AuthorInfoCacheManager;
import cn.deng.novel.mapper.AuthorInfoMapper;
import cn.deng.novel.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Deng
 * @date 2023/8/1
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorInfoCacheManager authorInfoCacheManager;

    private final AuthorInfoMapper authorInfoMapper;
    @Override
    public RestResp<Integer> getStatus(Long userId) {
        AuthorInfoDto authorInfoDto=authorInfoCacheManager.getAuthor(userId);
        return Objects.isNull(authorInfoDto) ? RestResp.success(null) : RestResp.success(authorInfoDto.getStatus());
    }

    @Override
    public RestResp<Void> register(AuthorRegisterReqDto dto) {
        // 校验该用户是否已注册为作家
        AuthorInfoDto author = authorInfoCacheManager.getAuthor(dto.getUserId());
        if (Objects.nonNull(author)) {
//            // 该用户已经是作家，直接返回
            return RestResp.success();
        }
        // 保存作家注册信息
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.setUserId(dto.getUserId());
        authorInfo.setChatAccount(dto.getChatAccount());
        authorInfo.setEmail(dto.getEmail());
        authorInfo.setInviteCode("0");
        authorInfo.setTelPhone(dto.getTelPhone());
        authorInfo.setPenName(dto.getPenName());
        authorInfo.setWorkDirection(dto.getWorkDirection());
        authorInfo.setCreateTime(LocalDateTime.now());
        authorInfo.setUpdateTime(LocalDateTime.now());
        authorInfoMapper.insert(authorInfo);
        // 清除作家缓存
        authorInfoCacheManager.evictAuthorCache();
        return RestResp.success();
    }
}
