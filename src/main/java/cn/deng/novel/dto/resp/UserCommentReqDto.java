package cn.deng.novel.dto.resp;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Deng
 * @date 2023/8/1
 * @description 用户评论 Dto
 */
@Data
public class UserCommentReqDto {

    private Long userId;

    private Long bookId;

    @Length(min = 10,max = 512)
    private String commentContent;
}
