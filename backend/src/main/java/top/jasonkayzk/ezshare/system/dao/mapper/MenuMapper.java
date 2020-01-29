package top.jasonkayzk.ezshare.system.dao.mapper;

import top.jasonkayzk.ezshare.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Jasonkay
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户名查询用户权限t_user -> t_user_role -> t_role -> t_role_menu -> t_menu
     *
     * @param userName 用户名
     *
     * @return 用户权限表
     */
    List<Menu> findUserPermissions(String userName);

    /**
     * 根据用户名查询菜单栏
     *
     * @param userName 用户名
     *
     * @return 菜单栏内容
     */
    List<Menu> findUserMenus(String userName);

    /**
     * 查找当前菜单/按钮关联的用户 ID
     *
     * @param menuId menuId
     *
     * @return 用户 Id集合
     */
    List<String> findUserIdsByMenuId(String menuId);

}
