package cn.deng.novel;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Deng
 */
@Slf4j
@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "cn.deng.novel.mapper")
@EnableConfigurationProperties
public class NovelRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelRoomApplication.class, args);
    }

}
