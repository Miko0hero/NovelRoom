package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deng
 * @date 2023/7/28
 * @description 小说分类 响应DTO
 */
@Builder
@Data
public class BookCategoryRespDto {
    /**
     * 类别ID
     */
    private Long id;

    /**
     * 类别名
     */
    private String name;
}
