package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/7/25
 * @description 用户登录 响应DTO
 */
@Data
@Builder
public class UserLoginRespDto {
    private Long uid;

    private String nickName;

    private String token;
}
