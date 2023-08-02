package cn.deng.novel.controller.front;

import cn.deng.novel.core.auth.UserHolder;
import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.UserLoginReqDto;
import cn.deng.novel.dto.req.UserRegisterReqDto;
import cn.deng.novel.dto.resp.UserCommentReqDto;
import cn.deng.novel.dto.resp.UserInfoRespDto;
import cn.deng.novel.dto.resp.UserLoginRespDto;
import cn.deng.novel.dto.resp.UserRegisterRespDto;
import cn.deng.novel.service.BookService;
import cn.deng.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    private final BookService bookService;
    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto userRegisterReqDto) {
        return userservice.register(userRegisterReqDto);
    }

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto userLoginReqDto){
        return userservice.login(userLoginReqDto);
    }

    /**
     * 用户信息查询接口
     */
    @GetMapping
    public RestResp<UserInfoRespDto> getUserInfo(){
        return userservice.getUserInfo(UserHolder.getUserId());
    }

    /**
     * 发表评论接口
     */
    @PostMapping("comment")
    public RestResp<Void> comment(@Valid @RequestBody UserCommentReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return bookService.saveComment(dto);
    }

    /**
     * 修改评论接口
     */
    @PutMapping("comment/{id}")
    public RestResp<Void> updateComment(@PathVariable Long id, String content) {
        return bookService.updateComment(UserHolder.getUserId(), id, content);
    }

    /**
     * 删除评论接口
     */
    @DeleteMapping("comment/{id}")
    public RestResp<Void> deleteComment(@PathVariable Long id) {
        return bookService.deleteComment(UserHolder.getUserId(), id);
    }
}
