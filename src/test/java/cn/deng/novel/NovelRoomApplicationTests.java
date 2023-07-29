package cn.deng.novel;

import cn.deng.novel.core.common.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
@SpringBootTest
class NovelRoomApplicationTests {

    @Test
    @Cacheable(cacheManager = CacheConstants.REDIS_CACHE_MANAGER)
    public String test(){
        return "widjask";
    }
}
