package cn.deng.novel.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Deng
 * @date 2023/7/24
 * @description JWT工具类  因为使用了spring的依赖注入，所以不能设置为静态工具类
 */
@Slf4j
@Component
@ConditionalOnProperty("novel.jwt.secret")
public class JwtUtils {

    /**
     * 注入JWT加密密钥
     */
    @Value("${novel.jwt.secret}")
    private  String secret;

    /**
     * 定义系统表示头常量
     */
    private final String HEADER_SYSTEM_KEY = "HeaderSystemKey";

    /**
     * @param uid       用户ID
     * @param systemKey 系统标识，从系统配置常量类中设置
     * @return JWT
     */
    public String generateToken(Long uid, String systemKey) {
        log.info(secret);
        return Jwts.builder()
                //标识头常量加上系统标识符组成标头
                .setHeaderParam(HEADER_SYSTEM_KEY, systemKey)
                //设置受众人字段，值是用户的id
                .setAudience(uid.toString())
                //使用自己定义的秘钥进行加密
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                //最终生成
                .compact();
    }

    /**
     * 解析JWT，若该JWT是当前systemKey，就返回解析后的用户ID
     * @param token     JWT
     * @param systemKey     系统标识
     * @return  用户ID
     */
    public Long parseToken(String token, String systemKey) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                    //设置用来解密的秘钥
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    //返回一个解析器
                    .build()
                    //解析，并且会验证JWT是否被篡改
                    .parseClaimsJws(token);
            //判断该JWT是否属于指定的系统
            if (Objects.equals(claimsJws.getHeader().get(HEADER_SYSTEM_KEY), systemKey)) {
                return Long.parseLong(claimsJws.getBody().getAudience());
            }
        } catch (JwtException e) {
            log.info("JWT解析失败{}", token);
        }
        return 0L;
    }


}
