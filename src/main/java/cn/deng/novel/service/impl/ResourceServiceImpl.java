package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.response.ImgVerifyCodeResponseDto;
import cn.deng.novel.manager.redis.VerifyCodeManager;
import cn.deng.novel.service.ResourceService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Deng
 * @date 2023/7/23
 * @description
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final VerifyCodeManager verifyCodeManager;

    @Override
    public RestResp<ImgVerifyCodeResponseDto> getImgVerifyCode() throws IOException {
        //生成一个UUId,在返回给前端验证码的同时一起返回
        String sessionId = IdWorker.get32UUID();
        return RestResp.success(ImgVerifyCodeResponseDto.builder()
                .sessionId(sessionId)
                .img(verifyCodeManager.generateVerifyCode(sessionId))
                .build());
    }
}
