package cn.deng.novel.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Deng
 * @date 2023/7/31
 * @description 小说章节响应Dto
 */
@Data
@Builder
public class BookChapterRespDto {
    /**
     * 章节ID
     */
    private Long id;

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 章节号
     */
    private Integer chapterNum;

    /**
     * 章节名
     */
    private String chapterName;

    /**
     * 章节字数
     */
    private Integer chapterWordCount;

    /**
     * 章节更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime chapterUpdateTime;

    /**
     * 是否收费;1-收费 0-免费
     */
    private Integer isVip;
}