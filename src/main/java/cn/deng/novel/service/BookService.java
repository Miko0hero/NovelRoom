package cn.deng.novel.service;

import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.resp.BookCategoryRespDto;

import java.util.List;

/**
 * @author Deng
 * @date 2023/7/28
 * @description
 */
public interface BookService {
    /**
     * 小说分类列表查询
     * @param workDirection 作品方向;0-男频 1-女频
     * @return 分类列表
     */
    RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection);


}
