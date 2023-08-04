package cn.deng.novel.core.interceptor;

import cn.deng.novel.core.auth.AuthStrategy;
import cn.deng.novel.core.auth.UserHolder;
import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.constant.SystemConfigConstants;
import cn.deng.novel.core.common.exception.BusinessException;
import cn.deng.novel.core.common.response.RestResp;
import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Deng
 * @date 2023/8/3
 * @description 权限认证的拦截器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    //进行多实现注入
    private final Map<String, AuthStrategy> authStrategyMap;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        //获取登录的JWT
        String token = request.getHeader(SystemConfigConstants.HTTP_AUTH_HEADER_NAME);
        //获取请求的URI
        String requestUri = request.getRequestURI();
        if (requestUri.contains(ApiRouterConstants.API_URL_PREFIX)) {
            //根据请求的URI获得认证策略，也就是截取URI中的片段来判断
            String subUri = requestUri.substring(ApiRouterConstants.API_URL_PREFIX.length() + 1);
            String systemName = subUri.substring(0, subUri.indexOf('/'));
            String authStrategyName = String.format("%sAuthStrategy", systemName);

            //开始认证
            try {
                if (authStrategyMap.containsKey(authStrategyName)) {
                    //根据选中的策略来执行认证
                    authStrategyMap.get(authStrategyName).auth(token, requestUri);
                }
                return HandlerInterceptor.super.preHandle(request, response, handler);
            } catch (BusinessException exception) {
                //认证失败
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(JSON.toJSONString(RestResp.fail(exception.getErrorCodeEnum())));
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        UserHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
