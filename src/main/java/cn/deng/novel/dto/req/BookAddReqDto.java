package cn.deng.novel.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Deng
 * @date 2023/8/2
 * @description
 */
@Data
public class BookAddReqDto {

    /**
     * 作品方向;0-男频 1-女频
     */
    @NotNull
    private Integer workDirection;

    /**
     * 类别ID
     */
    @NotNull
    private Long categoryId;

    /**
     * 类别名
     */
    @NotBlank
    private String categoryName;

    /**
     * 小说封面地址
     */
    @NotBlank
    private String picUrl;

    /**
     * 小说名
     */
    @NotBlank
    private String bookName;

    /**
     * 书籍描述
     */
    @NotBlank
    private String bookDesc;

    /**
     * 是否收费;1-收费 0-免费
     */
    @NotNull
    private Integer isVip;
}
