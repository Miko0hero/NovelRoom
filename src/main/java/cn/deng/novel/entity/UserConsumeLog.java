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
 * 用户消费记录
 * </p>
 *
 * @author Deng
 * @since 2023/07/24
 */
@Getter
@Setter
@TableName("user_consume_log")
public class UserConsumeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消费用户ID
     */
    private Long userId;

    /**
     * 消费使用的金额;单位：屋币
     */
    private Integer amount;

    /**
     * 消费商品类型;0-小说VIP章节
     */
    private Integer productType;

    /**
     * 消费的的商品ID;例如：章节ID
     */
    private Long productId;

    /**
     * 消费的的商品名;例如：章节名
     */
    private String producName;

    /**
     * 消费的的商品值;例如：1
     */
    private Integer producValue;

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
