package cn.deng.novel.mapper;

import cn.deng.novel.entity.UserReadHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户阅读历史 Mapper 接口
 * </p>
 *
 * @author Deng
 * @since 2023/07/24
 */
@Mapper
public interface UserReadHistoryMapper extends BaseMapper<UserReadHistory> {

}
