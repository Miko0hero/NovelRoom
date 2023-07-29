package cn.deng.novel.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Deng
 * @date 2023/7/24
 * @description 公共字段处理器，设置公共字段自动填充
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //与setValue设置的作用是一样的
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        log.info("自动填充公共字段");
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    /**
     * 更新时的填充策略
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("自动填充公共字段");
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
