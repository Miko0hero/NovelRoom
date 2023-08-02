package cn.deng.novel.core.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;

/**
 * @author Deng
 * @date 2023/7/29
 * @description 注意此配置类对SpringCache的注解的缓存无效
 */

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        // 我们为了自己开发方便，一般直接使用 <String, Object>
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(factory);
//        // Json序列化配置
//        FastJsonRedisSerializer fastJsonRedisSerializer = new  FastJsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        // String 的序列化
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        template.setValueSerializer(fastJsonRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(fastJsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }
}