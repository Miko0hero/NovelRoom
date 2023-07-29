package cn.deng.novel.mapper;

import cn.deng.novel.entity.BookCommentReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 小说评论回复 Mapper 接口
 * </p>
 *
 * @author Deng
 * @since 2023/07/29
 */
@Mapper
public interface BookCommentReplyMapper extends BaseMapper<BookCommentReply> {

}
