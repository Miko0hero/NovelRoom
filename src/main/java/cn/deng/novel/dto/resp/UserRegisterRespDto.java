package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/7/24
 * @description 用户注册 响应DTO
 */
@Data
@Builder
public class UserRegisterRespDto {
    private Long uid;
    private String token;
}
