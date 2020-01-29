package top.jasonkayzk.ezshare.system.dao.mapper;

import top.jasonkayzk.ezshare.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Jasonkay
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户名查询角色: t_user -> t_user_role -> t_role
     *
     * @param userName 用户名
     *
     * @return 用户名对应的角色列表
     */
    List<Role> findUserRole(String userName);

}
