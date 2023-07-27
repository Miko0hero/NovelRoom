package cn.deng.novel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Deng
 * @date 2023/7/27
 * @description 读取图片文件 控制器
 */
@RestController
@RequestMapping

public class ImageController {
    @Value("${novel.file.upload.path}")
    private  String prefixPath;

    /**
     *根据路径返回图片
     */
    @GetMapping("/localPic/{year}/{month}/{day}/{filename}")
    public void readImage( @PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable String filename, HttpServletResponse response) {
        String filePath =prefixPath+ File.separator+ "localPic"+ File.separator + year+ File.separator + month + File.separator+ day+ File.separator;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath + filename);
            ServletOutputStream servletOutputStream=response.getOutputStream();
            int len;
            byte[] bytes = new byte[2048];
            while ((len=fileInputStream.read(bytes))!=-1){
                servletOutputStream.write(bytes,0,len);
                servletOutputStream.flush();
            }
            servletOutputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
