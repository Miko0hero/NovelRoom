package cn.deng.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/22
 * @description 跨域配置的属性
 */
@ConfigurationProperties(prefix = "novel.cors")
@Data
public class CorsProperties {
    /**
     * 允许跨域的域名
     */
    private List<String> allowOrigins;
}
