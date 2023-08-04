package cn.deng.novel.core.auth;

import cn.deng.novel.core.common.constant.ErrorCodeEnum;
import cn.deng.novel.core.common.constant.SystemConfigConstants;
import cn.deng.novel.core.common.exception.BusinessException;
import cn.deng.novel.core.util.JwtUtils;
import cn.deng.novel.dto.UserInfoDto;
import cn.deng.novel.manager.cache.UserInfoCacheManager;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Deng
 * @date 2023/8/3
 * @description 策略模式实现用户认证授权功能
 */
public interface AuthStrategy {

    /**
     * 用户认证授权
     *
     * @param token      登录 token
     * @param requestUri 请求的 URI
     * @throws BusinessException 认证失败则抛出业务异常
     */
    void auth(String token,String requestUri) throws  BusinessException;

    /**
     * 前台多系统单点登录(Single Sign-On)统一账号认证授权（门户系统、作家系统以及后面会扩展的漫画系统和视频系统等）
     *
     * @param jwtUtils             jwt 工具类
     * @param userInfoCacheManager 用户缓存管理对象
     * @param token                token 登录 token
     * @return 用户ID
     */
    default Long authSingleSignOn(JwtUtils jwtUtils, UserInfoCacheManager userInfoCacheManager, String token){
        //如果token为空，则说明用户未登录，返回业务异常
        if (!StringUtils.hasText(token)){
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        Long userId=jwtUtils.parseToken(token, SystemConfigConstants.NOVEL_FRONT_KEY);
        //如果为获取到userId，说明token解析失败了
        if(Objects.isNull(userId)){
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        UserInfoDto userInfoDto=userInfoCacheManager.getUser(userId);
        //如果查询不到这个userId的信息，说明用户不存在
        if (Objects.isNull(userInfoDto)) {
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        //设置userId到当前的线程中
        UserHolder.setUserId(userId);
        //返回userId
        return userId;
    }
}
