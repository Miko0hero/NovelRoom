package cn.deng.novel.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 小说评论回复
 * </p>
 *
 * @author Deng
 * @since 2023/07/23
 */
@Getter
@Setter
@TableName("book_comment_reply")
public class BookCommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 回复用户ID
     */
    private Long userId;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 审核状态;0-待审核 1-审核通过 2-审核不通过
     */
    private Integer auditStatus;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
