package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.UserLoginReqDto;
import cn.deng.novel.dto.req.UserRegisterReqDto;
import cn.deng.novel.dto.response.UserInfoRespDto;
import cn.deng.novel.dto.response.UserLoginRespDto;
import cn.deng.novel.dto.response.UserRegisterRespDto;

/**
 * @author Deng
 * @date 2023/7/24
 * @description 用户服务模块
 */
public interface UserService {

    /**
     * 用户注册
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);

    /**
     * 用户登录
     *
     * @param userLoginReqDto 账号密码
     * @return 用户的uid、昵称以及JWT
     */
    RestResp<UserLoginRespDto> login(UserLoginReqDto userLoginReqDto);

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 昵称、头像、性别
     */
    RestResp<UserInfoRespDto> getUserInfo(Long userId);
}
