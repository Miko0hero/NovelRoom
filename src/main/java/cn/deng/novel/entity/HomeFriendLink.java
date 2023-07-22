package cn.deng.novel.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 友情链接
 * </p>
 *
 * @author Deng
 * @since 2023/07/22
 */
@Getter
@Setter
@TableName("home_friend_link")
public class HomeFriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接url
     */
    private String linkUrl;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 是否开启;0-不开启 1-开启
     */
    private Integer isOpen;

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
