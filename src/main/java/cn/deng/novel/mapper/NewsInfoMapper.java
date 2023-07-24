package cn.deng.novel.mapper;

import cn.deng.novel.entity.NewsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 新闻信息 Mapper 接口
 * </p>
 *
 * @author Deng
 * @since 2023/07/24
 */
@Mapper
public interface NewsInfoMapper extends BaseMapper<NewsInfo> {

}
