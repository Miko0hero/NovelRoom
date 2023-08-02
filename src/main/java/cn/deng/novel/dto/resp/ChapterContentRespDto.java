package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/8/2
 * @description
 */
@Data
@Builder
public class ChapterContentRespDto {

    /**
     * 章节标题
     */
    private String chapterName;

    /**
     * 章节内容
     */
    private String chapterContent;

    /**
     * 是否收费;1-收费 0-免费
     */
    private Integer isVip;
}
