package cn.deng.novel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 小说章节
 * </p>
 *
 * @author Deng
 * @since 2023/07/24
 */
@Getter
@Setter
@TableName("book_chapter")
public class BookChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    private Integer wordCount;

    /**
     * 是否收费;1-收费 0-免费
     */
    private Integer isVip;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
