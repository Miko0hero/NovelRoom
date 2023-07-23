package cn.deng.novel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 作家邀请码
 * </p>
 *
 * @author Deng
 * @since 2023/07/23
 */
@Getter
@Setter
@TableName("author_code")
public class AuthorCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 有效时间
     */
    private Date validityTime;

    /**
     * 是否使用过;0-未使用 1-使用过
     */
    private Integer isUsed;

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
