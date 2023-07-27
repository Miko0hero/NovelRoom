package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.ImgVerifyCodeResponseDto;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 图片上传
     * @param file 需要上传的图片文件
     * @return  图片文件访问的相对路径
     */
    RestResp<String> uploadImage(MultipartFile file) throws IOException;
}
