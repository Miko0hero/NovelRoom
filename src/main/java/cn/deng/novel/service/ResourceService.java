package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.ImgVerifyCodeResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
     * @return Base64编码的图片
     * @throws IOException 验证码图片生成失败
     */
    RestResp<ImgVerifyCodeResponseDto> getImgVerifyCode() throws IOException;

    /**
     * 图片上传
     *
     * @param file 需要上传的图片文件
     * @return 图片文件访问的相对路径
     */
    RestResp<String> uploadImage(MultipartFile file) throws IOException;

    /**
     * 流式读取文件
     * @param fileName 文件名
     * @param response 响应对象
     * @param filePath 文件路径
     */
    void readFile(@PathVariable String fileName, HttpServletResponse response, String filePath);
}
