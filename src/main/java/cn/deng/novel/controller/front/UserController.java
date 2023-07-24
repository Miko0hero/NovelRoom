package cn.deng.novel.controller.front;

import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.UserRegisterReqDto;
import cn.deng.novel.dto.response.UserRegisterRespDto;
import cn.deng.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Deng
 * @date 2023/7/25
 * @description 前台门户-用户模块 API 控制器
 */
@RestController
@RequestMapping(ApiRouterConstants.API_FRONT_USER_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserService userservice;

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto userRegisterReqDto) {
        return userservice.register(userRegisterReqDto);
    }
}
