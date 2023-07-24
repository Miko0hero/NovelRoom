package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.UserRegisterReqDto;
import cn.deng.novel.dto.response.UserRegisterRespDto;

/**
 * @author Deng
 * @date 2023/7/24
 * @description  用户服务模块
 */
public interface UserService {

    /**
     * 用户注册
     * @param dto
     * @return
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);
}
