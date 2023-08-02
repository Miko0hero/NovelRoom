package cn.deng.novel.controller.author;

import cn.deng.novel.core.auth.UserHolder;
import cn.deng.novel.core.common.constant.ApiRouterConstants;
import cn.deng.novel.core.common.request.PageReqDto;
import cn.deng.novel.core.common.response.PageRespDto;
import cn.deng.novel.core.common.response.RestResp;
import cn.deng.novel.dto.req.AuthorRegisterReqDto;
import cn.deng.novel.dto.req.BookAddReqDto;
import cn.deng.novel.dto.req.ChapterAddReqDto;
import cn.deng.novel.dto.req.ChapterUpdateReqDto;
import cn.deng.novel.dto.resp.BookChapterRespDto;
import cn.deng.novel.dto.resp.BookInfoRespDto;
import cn.deng.novel.dto.resp.ChapterContentRespDto;
import cn.deng.novel.service.AuthorService;
import cn.deng.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Deng
 * @date 2023/8/1
 * @description
 */
@RestController
@RequestMapping(ApiRouterConstants.API_AUTHOR_URL_PREFIX)
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final BookService bookService;

    /**
     * 查询作家状态接口
     */
    @GetMapping("status")
    public RestResp<Integer> getStatus() {
        return authorService.getStatus(UserHolder.getUserId());
    }

    /**
     * 作家注册接口
     */
    @PostMapping("register")
    public RestResp<Void> register(@Valid @RequestBody AuthorRegisterReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return authorService.register(dto);
    }

    /**
     * 小说发布接口
     */
    @PostMapping("book")
    public RestResp<Void> publishBook(@Valid @RequestBody BookAddReqDto dto) {
        return bookService.saveBook(dto);
    }

    /**
     * 小说发布列表查询接口
     */
    @GetMapping("books")
    public RestResp<PageRespDto<BookInfoRespDto>> listBooks(PageReqDto dto) {
        return bookService.listAuthorBooks(dto);
    }

    /**
     * 小说章节发布接口
     */
    @PostMapping("book/chapter/{bookId}")
    public RestResp<Void> publishBookChapter(@PathVariable("bookId") Long bookId, @Valid @RequestBody ChapterAddReqDto dto) {
        dto.setBookId(bookId);
        return bookService.saveBookChapter(dto);
    }

    /**
     * 小说章节删除接口
     */
    @DeleteMapping("book/chapter/{chapterId}")
    public RestResp<Void> deleteBookChapter(@PathVariable("chapterId") Long chapterId) {
        return bookService.deleteBookChapter(chapterId);
    }

    /**
     * 小说章节查询接口
     */
    @GetMapping("book/chapter/{chapterId}")
    public RestResp<ChapterContentRespDto> getBookChapter(@PathVariable("chapterId") Long chapterId) {
        return bookService.getBookChapter(chapterId);
    }

    /**
     * 小说章节更新接口
     */
    @PutMapping("book/chapter/{chapterId}")
    public RestResp<Void> updateBookChapter(@PathVariable("chapterId") Long chapterId, @Valid @RequestBody ChapterUpdateReqDto dto) {
        return bookService.updateBookChapter(chapterId, dto);
    }

    /**
     * 小说章节发布列表查询接口
     */
    @GetMapping("book/chapters/{bookId}")
    public RestResp<PageRespDto<BookChapterRespDto>> listBookChapters(@PathVariable("bookId") Long bookId, PageReqDto dto) {
        return bookService.listBookChapters(bookId, dto);
    }
}
