package cn.deng.novel.core.common.exception;

import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Deng
 * @date 2023/7/20
 * @description 自定义业务异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException{
    private final ErrorCodeEnum errorCodeEnum;
    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        // 不调用父类 Throwable的fillInStackTrace() 方法生成栈追踪信息，提高应用性能
        // 构造器之间的调用必须在第一行
        super(errorCodeEnum.getMessage(), null, false, false);
        this.errorCodeEnum = errorCodeEnum;
    }
}
