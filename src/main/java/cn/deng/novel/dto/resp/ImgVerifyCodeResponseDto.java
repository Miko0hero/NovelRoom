package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/7/23
 * @description 图像验证码 响应Dto
 */
@Data
@Builder
public class ImgVerifyCodeResponseDto {
    /**
     * 当前会话ID，用于标识改图形验证码属于哪个会话
     * */
    private String sessionId;

    /**
     * Base64 编码的验证码图片
     * */
    private String img;
}
