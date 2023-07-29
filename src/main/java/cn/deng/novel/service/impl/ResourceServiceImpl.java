package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import cn.deng.novel.core.common.constant.SystemConfigConstants;
import cn.deng.novel.core.common.exception.BusinessException;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.ImgVerifyCodeResponseDto;
import cn.deng.novel.manager.redis.VerifyCodeManager;
import cn.deng.novel.service.ResourceService;
import cn.deng.novel.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Deng
 * @date 2023/7/23
 * @description
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final UserService userService;

    private final VerifyCodeManager verifyCodeManager;

    @Value("${novel.file.upload.path}")
    private String fileUploadPath;

    @Override
    public RestResp<ImgVerifyCodeResponseDto> getImgVerifyCode() throws IOException {
        //生成一个UUId,在返回给前端验证码的同时一起返回
        String sessionId = IdWorker.get32UUID();
        return RestResp.success(ImgVerifyCodeResponseDto.builder()
                .sessionId(sessionId)
                .img(verifyCodeManager.generateVerifyCode(sessionId))
                .build());
    }

    @Override
    public RestResp<String> uploadImage(MultipartFile file) throws IOException {
        LocalDateTime currentTime = LocalDateTime.now();
        //构建图片保存路径
        String savePath = SystemConfigConstants.IMAGE_UPLOAD_DIRECTORY
                + currentTime.format(DateTimeFormatter.ofPattern("yyyy")) + File.separator
                + currentTime.format(DateTimeFormatter.ofPattern("MM")) + File.separator
                + currentTime.format(DateTimeFormatter.ofPattern("dd"));
        //获取上传图片的原始名称
        String originalName = file.getOriginalFilename();
        //图片名称为空，直接抛出业务异常
        if (!StringUtils.hasText(originalName)) {
            throw new BusinessException(ErrorCodeEnum.USER_UPLOAD_FILE_NAME_EMPTY);
        }
        //设置保存图片的名称
        String saveFileName = IdWorker.get32UUID() + originalName.substring(originalName.lastIndexOf("."));
        File saveFile = new File(fileUploadPath + savePath, saveFileName);
        //判断文件路径是否存在，如果不存在就创建
        //getParentFile获取除了最后一段路径的路径
        if (!saveFile.getParentFile().exists()) {
            boolean success = saveFile.getParentFile().mkdirs();
            //如果创建失败，抛出业务异常
            if (!success) {
                throw new BusinessException(ErrorCodeEnum.USER_UPLOAD_FILE_ERROR);
            }
        }
        //将file的内容传输到saveFile中
        file.transferTo(saveFile);
        //ImageIo读不到流，说明该文件不是图片类型，则删除该文件，抛出业务异常
        if (Objects.isNull(ImageIO.read(saveFile))) {
            Files.delete(saveFile.toPath());
            throw new BusinessException(ErrorCodeEnum.USER_UPLOAD_FILE_TYPE_NOT_MATCH);
        }

        //返回相对路径
        return RestResp.success(savePath + File.separator + saveFileName);
    }

    @Override
    public void readFile(String fileName, HttpServletResponse response, String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath + fileName);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] bytes = new byte[2048];
            while ((len = fileInputStream.read(bytes)) != -1) {
                servletOutputStream.write(bytes, 0, len);
            }
            servletOutputStream.flush();
            servletOutputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
