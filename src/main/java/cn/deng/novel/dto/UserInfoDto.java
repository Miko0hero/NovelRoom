package cn.deng.novel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Deng
 * @date 2023/7/24
 * @description 用户信息DTO
 */
@Data
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer status;
}
