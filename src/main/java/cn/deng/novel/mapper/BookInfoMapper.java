package cn.deng.novel.mapper;

import cn.deng.novel.dto.req.BookSearchReqDto;
import cn.deng.novel.dto.resp.BookInfoRespDto;
import cn.deng.novel.entity.BookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 小说信息 Mapper 接口
 * </p>
 *
 * @author Deng
 * @since 2023/07/29
 */
@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {

    /**
     * 根据条件查询小说信息，
     * @param page Mybatis-Plus的分页对象，查询结果会传入到IPage中，也会传入到返回结果中
     * @param condition 查询条件
     * @return 小说信息列表
     */
    List<BookInfo> searchBooks(IPage<BookInfoRespDto> page, @Param("condition") BookSearchReqDto condition);
}
