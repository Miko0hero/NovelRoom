package cn.deng.novel.core.config;

import cn.deng.novel.core.common.constant.CacheConstants;
import com.alibaba.fastjson2.support.spring.data.redis.FastJsonRedisSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Deng
 * @date 2023/7/22
 * @description 缓存相关的配置
 */
@Configuration
public class CacheConfig {
    /**
     * Caffeine缓存管理器
     * 设置为主要的缓存管理器
     */
    @Primary
    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = new ArrayList<>(CacheConstants.CacheEnum.values().length);
        for (CacheConstants.CacheEnum c : CacheConstants.CacheEnum.values()) {
            if (c.isLocal()) {
                Caffeine<Object, Object> caffeine = Caffeine.newBuilder().recordStats().maximumSize(c.getMaxSize());
                if (c.getTtl() > 0) {
                    // 设置最后一次写入或访问后经过固定时间过期,单位为秒
                    caffeine.expireAfterWrite(Duration.ofSeconds(c.getTtl()));
                }
                caches.add(new CaffeineCache(c.getName(), caffeine.build()));
            }
        }

        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     * Redis 缓存管理器
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                //设置序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith((RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class))))
                .disableCachingNullValues()
                .prefixCacheNameWith(CacheConstants.REDIS_CACHE_PREFIX);

        Map<String, RedisCacheConfiguration> cacheMap = new LinkedHashMap<>(CacheConstants.CacheEnum.values().length);
        //给每个枚举类设置一个配置类，注意这会覆盖掉默认的配置类
        for (CacheConstants.CacheEnum c : CacheConstants.CacheEnum.values()) {
            //判断是否是远程缓存类型
            if (c.isRemote()) {
                //有失效时间则设置失效时间，否则为持久保存
                if (c.getTtl() > 0) {
                    cacheMap.put(c.getName(), RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                            .prefixCacheNameWith(CacheConstants.REDIS_CACHE_PREFIX)
                            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                            .serializeValuesWith((RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class))))
                            .entryTtl(Duration.ofSeconds(c.getTtl())));
                } else {
                    cacheMap.put(c.getName(), RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                            .prefixCacheNameWith(CacheConstants.REDIS_CACHE_PREFIX));
                }
            }
        }

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, cacheMap);
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.initializeCaches();
        return redisCacheManager;
    }
}
