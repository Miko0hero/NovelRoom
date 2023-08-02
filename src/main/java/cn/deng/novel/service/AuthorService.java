package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.AuthorRegisterReqDto;

/**
 * @author Deng
 * @date 2023/8/1
 * @description
 */
public interface AuthorService {

    /**
     * 查询作家状态
     *
     * @param userId 用户ID
     * @return 作家状态
     */
    RestResp<Integer> getStatus(Long userId);

    /**
     * 作家注册
     *
     * @param dto 注册参数
     * @return void
     */
    RestResp<Void> register(AuthorRegisterReqDto dto);
}
