package top.jasonkayzk.ezshare.system.service;

import top.jasonkayzk.ezshare.system.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Jasonkay
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 根据roleId删除
     *
     * @param roleIds 角色ID
     */
    void deleteRoleMenusByRoleId(String[] roleIds);


    /**
     * 根据menuId删除
     *
     * @param menuIds 菜单Id
     */
    void deleteRoleMenusByMenuId(String[] menuIds);


    /**
     * 根据角色Id获取MenuId
     *
     * @param roleId 角色Id
     *
     * @return 角色-菜单映射
     */
    List<RoleMenu> getRoleMenusByRoleId(String roleId);

    /**
     * 插入数据
     *
     * @param roleMenu roleMenu
     */
    void insert(RoleMenu roleMenu);

}
