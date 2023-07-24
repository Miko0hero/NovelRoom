package cn.deng.novel.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Deng
 * @date 2023/7/24
 * @description
 */
@Slf4j
@SpringBootTest
public class JwtUtilsTest {
    @Autowired
    JwtUtils jwtUtils;
    @Test
    public void test() {
        String token = jwtUtils.generateToken(123L, "user");
        log.info(token);
        Long id= jwtUtils.parseToken(token,"user");
        log.info(id.toString());
    }
}
