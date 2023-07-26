package cn.deng.novel.core.interceptor;

import cn.deng.novel.core.auth.UserHolder;
import cn.deng.novel.core.common.constant.SystemConfigConstants;
import cn.deng.novel.core.util.JwtUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Deng
 * @date 2023/7/26
 * @description Token解析拦截器
 */
@Component
@RequiredArgsConstructor
public class TokenParseInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    /**
     * 解析token，并且保存用户ID到localThread中
     * 关闭浏览器后再次登录，服务端没有保存用户数据，所以从前端保存的token中解析出用户id
     * 从而达到保存用户登录状态的效果
     * （以后可以在JWT生成过程中加入有效时间的标签，这样可以控制保存状态的有效时间）
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        //获取登录JWT
        String token = request.getHeader(SystemConfigConstants.HTTP_AUTH_HEADER_NAME);
        if (StringUtils.hasText(token)) {
            UserHolder.setUserId(jwtUtils.parseToken(token,SystemConfigConstants.NOVEL_FRONT_KEY));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * DispatcherServlet 完全处理完请求后调用，出现异常照常调用
     */
    @Override
    @SuppressWarnings("NullableProblems")//忽略警告
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 清理当前线程保存的用户数据
        UserHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
