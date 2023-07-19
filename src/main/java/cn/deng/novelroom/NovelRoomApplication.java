package cn.deng.novelroom;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author Deng
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "cn.deng.novelroom")
public class NovelRoomApplication {

    public static void main(String[] args) {
        log.info("是靠的就是到那时可能大佬打瞌睡");
        SpringApplication.run(NovelRoomApplication.class, args);
    }

}
