package cn.deng.novel.core.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Deng
 * @date 2023/7/21
 * @description 通用常量类
 */
public class CommonConstants {

    /**
     * 是
     */
    public static final Integer YES = 1;

    /**
     * 否
     */
    public static final Integer NO = 0;

    /**
     * 性别常量
     */
    @AllArgsConstructor
    @Getter
    public enum SexEnum {

        /**
         * 男
         */
        MALE(0, "男"),

        /**
         * 女
         */
        FEMALE(1, "女");

        private final int code;
        private final String desc;
    }
}
