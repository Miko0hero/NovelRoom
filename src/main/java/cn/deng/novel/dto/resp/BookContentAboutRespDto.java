package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/8/1
 * @description 小说内容相关 响应Dto
 */
@Data
@Builder
public class BookContentAboutRespDto {

    /**
     * 小说信息
     */
    private BookInfoRespDto bookInfo;

    /**
     * 章节信息
     */
    private BookChapterRespDto chapterInfo;

    /**
     * 章节内容
     */
    private String bookContent;
}
