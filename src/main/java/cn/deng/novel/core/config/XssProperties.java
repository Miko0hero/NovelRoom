package cn.deng.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Deng
 * @date 2023/8/4
 * @description Xss 过滤配置属性
 */
@ConfigurationProperties(prefix = "novel.xss")
@Data
public class XssProperties {

    Boolean enabled;

    List<String> excludes;
}
