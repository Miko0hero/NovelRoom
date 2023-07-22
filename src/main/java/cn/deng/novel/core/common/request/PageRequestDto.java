package cn.deng.novel.core.common.request;

import lombok.Data;

/**
 * @author Deng
 * @date 2023/7/20
 * @description
 */
@Data
public class PageRequestDto {

    /**
     * 请求页码，默认第 1 页
     * */
    private int pageNum = 1;

    /**
     * 每页大小，默认每页 10 条
     * */
    private int pageSize = 10;

    /**
     * 是否查询所有，默认不查所有
     * 为 true 时，pageNum 和 pageSize 无效
     * */
    private boolean fetchAll = false;
}
