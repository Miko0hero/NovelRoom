package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import cn.deng.novel.core.common.constant.SystemConfigConstants;
import cn.deng.novel.core.common.exception.BusinessException;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.core.util.JwtUtils;
import cn.deng.novel.dto.req.UserRegisterReqDto;
import cn.deng.novel.dto.response.UserRegisterRespDto;
import cn.deng.novel.entity.UserInfo;
import cn.deng.novel.manager.redis.VerifyCodeManager;
import cn.deng.novel.mapper.UserInfoMapper;
import cn.deng.novel.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author Deng
 * @date 2023/7/24
 * @description
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VerifyCodeManager verifyCodeManager;

    private final UserInfoMapper userInfoMapper;

    private final JwtUtils jwtUtils;

    @Override
    public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {
        //验证图形验证码是否正确，不正确则返回业务异常
        if (!verifyCodeManager.imgVerifyCodeOk(dto.getSessionId(), dto.getVelCode())) {
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        //用户名就是手机号，判断手机号是否已经注册
        wrapper.eq(UserInfo::getUsername, dto.getUsername());
        if (userInfoMapper.selectCount(wrapper) != 0) {
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }
        //判断可以注册，则将数据存入数据库
        UserInfo userInfo = new UserInfo();
        //密码进行md5加密
        userInfo.setPassword(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes()));
        userInfo.setUsername(dto.getUsername());
        userInfo.setNickName(dto.getUsername());
        userInfo.setSalt("0");
        userInfoMapper.insert(userInfo);

        //删除验证码
        verifyCodeManager.removeImgVerifyCode(dto.getSessionId());
        //生成JWT并返回
        return RestResp.success(
                UserRegisterRespDto.builder()
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConstants.NOVEL_FRONT_KEY))
                        .uid(userInfo.getId())
                        .build()
        );
    }
}
