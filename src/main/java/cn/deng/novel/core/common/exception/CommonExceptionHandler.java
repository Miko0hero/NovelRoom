package cn.deng.novel.core.common.exception;

import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import cn.deng.novel.core.common.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Deng
 * @date 2023/7/21
 * @description 通用异常处理器
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * 处理数据校验异常
     * */
    @ExceptionHandler(BindException.class)
    public RestResponse<Void> handlerBindException(BindException e){
        log.error(e.getMessage(),e);
        return RestResponse.fail(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR);
    }

    /**
     * 处理业务异常
     * */
    @ExceptionHandler(BusinessException.class)
    public RestResponse<Void> handlerBusinessException(BusinessException e){
        log.error(e.getMessage(),e);
        return RestResponse.fail(e.getErrorCodeEnum());
    }

    /**
     * 处理系统异常
     * */
    @ExceptionHandler(Exception.class)
    public RestResponse<Void> handlerException(Exception e){
        log.error(e.getMessage(),e);
        return RestResponse.error();
    }

}
