package cn.deng.novel.mapper;

import cn.deng.novel.entity.UserComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户评论 Mapper 接口
 * </p>
 *
 * @author Deng
 * @since 2023/07/24
 */
@Mapper
public interface UserCommentMapper extends BaseMapper<UserComment> {

}
