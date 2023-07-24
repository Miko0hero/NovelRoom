package cn.deng.novel.mapper;

import cn.deng.novel.entity.BookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 小说信息 Mapper 接口
 * </p>
 *
 * @author Deng
 * @since 2023/07/24
 */
@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {

}
