package cn.deng.novel.core.config;


import cn.deng.novel.core.interceptor.AuthInterceptor;
import cn.deng.novel.core.interceptor.TokenParseInterceptor;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author Deng
 * @date 2023/7/23
 * @description
 */
@Configuration
@RequiredArgsConstructor
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenParseInterceptor tokenParseInterceptor;

    private final AuthInterceptor authInterceptor;
    /**
     * 添加FastJson到消息转换器中
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置...
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
        //设置序列化时吧Long类型转换成String类型，防止前端接收参数发生精度丢失
        config.setWriterFeatures(JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.PrettyFormat,JSONWriter.Feature.WriteLongAsString);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(0, converter);
    }

    /**
     * 增加拦截器
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
       // registry.addInterceptor(tokenParseInterceptor);
        registry.addInterceptor(authInterceptor);
    }

    @Override
    public void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }
}

