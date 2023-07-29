package cn.deng.novel.service;

import cn.deng.novel.core.common.response.PageRespDto;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.BookSearchReqDto;
import cn.deng.novel.dto.resp.BookInfoRespDto;

/**
 * @author Deng
 * @date 2023/7/29
 * @description 搜索服务类
 */
public interface SearchService {
    /**
     * 小说搜索 根据传进来的条件进行搜索
     * 搜索条件与返回结果都提供了冗余字段
     */
    RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto condition);
}
