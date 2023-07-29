package cn.deng.novel.mapper;

import cn.deng.novel.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author Deng
 * @since 2023/07/29
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
