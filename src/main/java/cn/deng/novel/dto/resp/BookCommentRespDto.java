package cn.deng.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Deng
 * @date 2023/8/1
 * @description 小说评论响应Dto
 */
@Data
@Builder
public class BookCommentRespDto {

    private Long commentTotal;

    private List<CommentInfo> comments;

    @Data
    @Builder
    public static class CommentInfo {

        private Long id;

        private String commentContent;

        /**
         *  使用@JSONField(serializeUsing = UsernameSerializer.class)自定义序列化
         *  出现未知Bug，没有进到自定义的序列化器中
         *  ！待解决
         */
        private String commentUser;

        private Long commentUserId;

        private String commentUserPhoto;

        private LocalDateTime commentTime;

    }

}
