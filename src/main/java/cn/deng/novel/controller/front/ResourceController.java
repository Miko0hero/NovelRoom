package cn.deng.novel.controller.front;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.ImgVerifyCodeResponseDto;
import cn.deng.novel.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Deng
 * @date 2023/7/23
 * @description
 */
@RestController
@RequestMapping(ApiRouterConstants.API_FRONT_RESOURCE_URL_PREFIX)
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    /**
     * 获取图片验证码接口
     */
    @GetMapping("/img_verify_code")
    public RestResp<ImgVerifyCodeResponseDto> getImgVerifyCode() throws IOException {
        return resourceService.getImgVerifyCode();
    }

    /**
     * 图片上传接口
     */
    @PostMapping("/image")
    public RestResp<String> uploadImage(MultipartFile file) throws IOException {
        return resourceService.uploadImage(file);
    }
}
