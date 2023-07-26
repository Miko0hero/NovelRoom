package cn.deng.novel.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Deng
 * @date 2023/7/25
 * @description 用户登录DTO
 */
@Data
public class UserLoginReqDto {
    @NotBlank(message = "手机号不能为空！")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$", message = "手机号格式不正确！")
    private String username;

    @NotBlank(message = "密码不能为空！")
    private String password;
}
