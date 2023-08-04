package cn.deng.novel.core.auth;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.exception.BusinessException;
import cn.deng.novel.core.util.JwtUtils;
import cn.deng.novel.manager.cache.UserInfoCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deng
 * @date 2023/8/3
 * @description 前台门户系统 认证策略
 */
@Component
@RequiredArgsConstructor
public class FrontAuthStrategy implements AuthStrategy{

    private final JwtUtils jwtUtils;

    private final UserInfoCacheManager userInfoCacheManager;

    @Override
    public void auth(String token, String requestUri) throws BusinessException {
        /**
         * 不需要进行作家权限认证的 URI
         */
        List<String> excludeUrls = new ArrayList<>();
        excludeUrls.add(ApiRouterConstants.API_FRONT_RESOURCE_URL_PREFIX + "/image");

        if (excludeUrls.contains(requestUri)) {
            //不需要进行作家的权限认证
            return;
        }
        authSingleSignOn(jwtUtils,userInfoCacheManager,token);
    }
}
