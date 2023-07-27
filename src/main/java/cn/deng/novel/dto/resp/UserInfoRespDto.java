package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/7/26
 * @description
 */
@Data
@Builder
public class UserInfoRespDto {
    /**
     * 昵称
     * */
    private String nickName;

    /**
     * 用户头像
     * */
    private String userPhoto;

    /**
     * 用户性别
     * */
    private Integer userSex;
}
