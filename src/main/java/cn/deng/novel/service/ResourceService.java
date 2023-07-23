package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.response.ImgVerifyCodeResponseDto;

import java.io.IOException;

/**
 * @author Deng
 * @date 2023/7/23
 * @description
 */
public interface ResourceService {
    /**
     * 获取图片验证码
     *
     * @throws IOException 验证码图片生成失败
     * @return Base64编码的图片
     */
    RestResp<ImgVerifyCodeResponseDto> getImgVerifyCode() throws IOException;
}
