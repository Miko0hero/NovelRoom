package cn.deng.novel;

import cn.deng.novel.manager.redis.VerifyCodeManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author Deng
 * @date 2023/7/23
 * @description
 */
@SpringBootTest
@Slf4j
public class VerifyCodeTest {
    @Autowired
    private VerifyCodeManager verifyCodeManager;

    @Test
    public void test() throws IOException {
     String s=   verifyCodeManager.generateVerifyCode("ss");
        log.info(s);
    }
}
