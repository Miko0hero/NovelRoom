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
public class RestResp<T> {
    /**
     * 响应码
     */
    private final String code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 响应数据
     */
    private T data;
    private RestResp() {
        this.code = ErrorCodeEnum.OK.getCode();
        this.message = ErrorCodeEnum.OK.getMessage();
    }

    private RestResp(ErrorCodeEnum errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private RestResp(T data) {
        this();
        this.data = data;
    }

    /**
     * 业务处理成功,无数据返回
     */
    public static RestResp<Void> success() {
        return new RestResp<>();
    }

    /**
     * 业务处理成功，有数据返回
     */
    public static <T> RestResp<T> success(T data) {
        return new RestResp<>(data);
    }
    /**
     * 业务处理失败
     */
    public static RestResp<Void> fail(ErrorCodeEnum errorCode) {
        return new RestResp<>(errorCode);
    }

    /**
     * 系统错误
     */
    public static RestResp<Void> error() {
        return new RestResp<>(ErrorCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 判断是否成功,会一同被序列化
     */
    public boolean isOk() {
        return Objects.equals(this.code, ErrorCodeEnum.OK.getCode());
    }
}
