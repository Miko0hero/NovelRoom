package cn.deng.novel.controller.image;

import cn.deng.novel.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author Deng
 * @date 2023/7/27
 * @description 读取图片文件 控制器
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ImageController {
    @Value("${novel.file.upload.path}")
    private String prefixPath;

    private final ResourceService resourceService;

    /**
     * 根据路径返回图片
     */
    @GetMapping("/localPic/{year}/{month}/{day}/{fileName}")
    public void readLocalPic(@PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable String fileName, HttpServletResponse response) {
        String filePath = prefixPath + File.separator + "localPic" + File.separator + year + File.separator + month + File.separator + day + File.separator;
        resourceService.readFile(fileName, response, filePath);
    }


    @GetMapping("/images/{fileName}")
    public void readDefaultImage(HttpServletResponse response, @PathVariable String fileName) {
        //路径为图片文件路径的根目录
        String filePath = prefixPath+File.separator;
        resourceService.readFile(fileName, response, filePath);
    }

}
