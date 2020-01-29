package top.jasonkayzk.ezshare.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author Jasonkay
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据username查询用户权限
     *
     * @param username 用户名
     *
     * @return 权限列表
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 根据用户名查询菜单列表
     *
     * @param username 用户名
     *
     * @return 用户菜单列表
     */
    List<Menu> findUserMenus(String username);


    /**
     * 查询菜单
     *
     * @param menu 查询参数
     *
     * @return 菜单map
     */
    Map<String, Object> findMenus(Menu menu);


    /**
     * 查询菜单列表
     *
     * @param menu 查询参数
     *
     * @return 菜单列表
     */
    List<Menu> findMenuList(Menu menu);


    /**
     * 创建菜单
     *
     * @param menu 菜单
     */
    void createMenu(Menu menu);


    /**
     * 更新菜单
     *
     * @param menu 菜单
     */
    void updateMenu(Menu menu) throws CacheException, JsonProcessingException;

    /**
     * 递归删除菜单/按钮
     *
     * @param menuIds menuIds
     */
    void deleteMenus(String[] menuIds) throws CacheException, JsonProcessingException;

}
