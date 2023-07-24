package cn.deng.novel.manager.redis;

import cn.deng.novel.core.common.constant.CacheConstants;
import cn.deng.novel.core.common.util.ImgVerifyCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

/**
 * @author Deng
 * @date 2023/7/23
 * @description 验证码管理类
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class VerifyCodeManager {

    private final StringRedisTemplate stringRedisTemplate ;

    /**
     * 生成图形验证码，并放入到Redis中
     */
    public String generateVerifyCode(String sessionId) throws IOException {
        //验证码为4位
        String verifyCode= ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String img =ImgVerifyCodeUtils.genVerifyCodeImg(verifyCode);
        stringRedisTemplate.opsForValue().set(CacheConstants.IMG_VERIFY_CODE_CACHE_KEY+sessionId,verifyCode, Duration.ofMinutes(5));
        return img;
    }

    /**
     * 验证图形验证码
     */

    public boolean imgVerifyCodeOk(String sessionId,String verifyCode){
        String redisVerifyCode=stringRedisTemplate.opsForValue().get(CacheConstants.IMG_VERIFY_CODE_CACHE_KEY+sessionId);
        if (redisVerifyCode != null) {
            return redisVerifyCode.equals(verifyCode);
        }
        return false;
    }

    /**
     * 从Redis中时删除验证码
     */
    public void removeImgVerifyCode(String sessionId){
        stringRedisTemplate.delete(CacheConstants.IMG_VERIFY_CODE_CACHE_KEY+sessionId);
    }
}
