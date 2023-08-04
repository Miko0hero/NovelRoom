package cn.deng.novel.core.auth;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import cn.deng.novel.core.common.exception.BusinessException;
import cn.deng.novel.core.util.JwtUtils;
import cn.deng.novel.dto.resp.AuthorInfoDto;
import cn.deng.novel.manager.cache.AuthorInfoCacheManager;
import cn.deng.novel.manager.cache.UserInfoCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deng
 * @date 2023/8/3
 * @description
 */
@Component
@RequiredArgsConstructor
public class AuthorAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final UserInfoCacheManager userInfoCacheManager;

    private final AuthorInfoCacheManager authorInfoCacheManager;

    @Override
    public void auth(String token, String requestUri) throws BusinessException {

        /**
         * 不需要进行作家权限认证的 URI
         */
        List<String> excludeUrls = new ArrayList<>();
        excludeUrls.add(ApiRouterConstants.API_AUTHOR_URL_PREFIX + "/register");
        excludeUrls.add(ApiRouterConstants.API_AUTHOR_URL_PREFIX + "/status");

        //先进行同一账号认证
        Long userId = authSingleSignOn(jwtUtils, userInfoCacheManager, token);
        if (excludeUrls.contains(requestUri)) {
            //不需要进行作家的权限认证
            return;
        }
        //作家权限认证
        AuthorInfoDto authorInfoDto = authorInfoCacheManager.getAuthor(userId);
        if (authorInfoDto == null) {
            //说明该用户不是作家账号
            throw new BusinessException(ErrorCodeEnum.USER_UN_AUTH);
        }
        UserHolder.setAuthorId(authorInfoDto.getId());
    }
}
