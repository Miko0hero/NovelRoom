package cn.deng.novel.core.filter;


import cn.deng.novel.core.config.XssProperties;
import cn.deng.novel.core.wrapper.XssHttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Deng
 * @date 2023/8/4
 * @description 防止Xss攻击的过滤器
 */
@Component
@ConditionalOnProperty(value = "novel.xss.enabled",havingValue = "true")
@WebFilter(urlPatterns = "/*", filterName = "xssFilter")
@EnableConfigurationProperties(value = XssProperties.class)
@RequiredArgsConstructor
public class XssFilter implements Filter {

    private final XssProperties xssProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        //如果是忽略的路径，直接通过
        if (handleExcludeUrl(req)) {
            chain.doFilter(request, response);
        }
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(req);
        chain.doFilter(xssHttpServletRequestWrapper, response);
    }

    private boolean handleExcludeUrl(HttpServletRequest request) {
        if (CollectionUtils.isEmpty(xssProperties.getExcludes())) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : xssProperties.getExcludes()) {
            //使用正则表达式来匹配
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                //匹配成功，返回true
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
