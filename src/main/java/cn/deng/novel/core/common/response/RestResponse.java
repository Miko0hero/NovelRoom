package cn.deng.novel.core.common.response;

import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Deng
 * @date 2023/7/20
 * @description     响应数据格式
 */
@Getter
public class RestResponse<T> {
    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    private RestResponse() {
        this.code = ErrorCodeEnum.OK.getCode();
        this.message = ErrorCodeEnum.OK.getMessage();
    }

    private RestResponse(ErrorCodeEnum errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private RestResponse(T data) {
        this.data = data;
    }

    /**
     * 业务处理成功,无数据返回
     */
    public static RestResponse<Void> success() {
        return new RestResponse<>();
    }

    /**
     * 业务处理成功，有数据返回
     */
    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(data);
    }
    /**
     * 业务处理失败
     */
    public static RestResponse<Void> fail(ErrorCodeEnum errorCode) {
        return new RestResponse<>(errorCode);
    }

    /**
     * 系统错误
     */
    public static RestResponse<Void> error() {
        return new RestResponse<>(ErrorCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 判断是否成功
     */
    public boolean isOk() {
        return Objects.equals(this.code, ErrorCodeEnum.OK.getCode());
    }
}
