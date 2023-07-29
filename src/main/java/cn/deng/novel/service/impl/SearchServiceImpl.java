package cn.deng.novel.service.impl;

import cn.deng.novel.core.common.response.PageRespDto;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.BookSearchReqDto;
import cn.deng.novel.dto.resp.BookInfoRespDto;
import cn.deng.novel.entity.BookInfo;
import cn.deng.novel.mapper.BookInfoMapper;
import cn.deng.novel.service.SearchService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deng
 * @date 2023/7/29
 * @description 搜索功能
 */
@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final BookInfoMapper bookInfoMapper;
    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto condition) {
        Page<BookInfoRespDto> page=new Page<>(condition.getPageNum(),condition.getPageSize());
        List<BookInfo> bookInfos=bookInfoMapper.searchBooks(page,condition);
        return RestResp.success(PageRespDto.of(condition.getPageNum(), condition.getPageSize(),page.getTotal(),
                bookInfos.stream().map(v -> BookInfoRespDto.builder()
                        .id(v.getId())
                        .bookName(v.getBookName())
                        .categoryId(v.getCategoryId())
                        .categoryName(v.getCategoryName())
                        .authorId(v.getAuthorId())
                        .authorName(v.getAuthorName())
                        .wordCount(v.getWordCount())
                        .lastChapterName(v.getLastChapterName())
                        .build()).collect(Collectors.toList())));
    }
}
