package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/7/31
 * @description 小说章节相关响应 Dto
 */
@Data
@Builder
public class BookChapterAboutRespDto {

    private BookChapterRespDto chapterInfo;

    /**
     * 章节总数
     */
    private Long chapterTotal;

    /**
     * 内容概要（30字）
     */
    private String contentSummary;
}
