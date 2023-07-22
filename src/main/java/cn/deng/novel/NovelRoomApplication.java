package cn.deng.novel;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Deng
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "cn.deng.novel")
public class NovelRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelRoomApplication.class, args);
    }

}
