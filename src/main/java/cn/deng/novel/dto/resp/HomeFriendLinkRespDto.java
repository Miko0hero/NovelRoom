package cn.deng.novel.dto.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Deng
 * @date 2023/7/30
 * @description 首页友情链接 响应Dto
 */
@Data
public class HomeFriendLinkRespDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接url
     */
    private String linkUrl;
}
