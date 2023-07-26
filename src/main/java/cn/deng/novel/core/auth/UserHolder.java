package cn.deng.novel.core.auth;

import lombok.experimental.UtilityClass;

/**
 * @author Deng
 * @date 2023/7/26
 * @description 保存用户信息
 */
@UtilityClass
public class UserHolder {

    /**
     * 当前线程用户ID
     */
    private static final ThreadLocal<Long> USER_ID_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 当前线程作家ID
     */
    private static final ThreadLocal<Long> AUTHOR_ID_THREAD_LOCAL = new ThreadLocal<>();

    public void setUserId(Long userId) {
        USER_ID_THREAD_LOCAL.set(userId);
    }

    public Long getUserId() {
        return USER_ID_THREAD_LOCAL.get();
    }

    public void setAuthorId(Long authorId) {
        AUTHOR_ID_THREAD_LOCAL.set(authorId);
    }

    public Long getAuthorId() {
        return  AUTHOR_ID_THREAD_LOCAL.get();
    }

    public void clear() {
        USER_ID_THREAD_LOCAL.remove();
        AUTHOR_ID_THREAD_LOCAL.remove();
    }
}
