package cn.deng.novel.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Deng
 * @date 2023/8/2
 * @description
 */
@Data
public class ChapterUpdateReqDto {

    /**
     * 章节名
     */
    @NotBlank
    private String chapterName;

    /**
     * 章节内容
     */
    @NotBlank
    @Length(min = 50)
    private String chapterContent;

    /**
     * 是否收费;1-收费 0-免费
     */
    @NotNull
    private Integer isVip;
}
