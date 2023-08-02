package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Deng
 * @date 2023/8/2
 * @description
 */
@Data
@Builder
public class UserCommentRespDto {
    private String commentContent;

    private String commentBookPic;

    private String commentBook;

    private LocalDateTime commentTime;
}
