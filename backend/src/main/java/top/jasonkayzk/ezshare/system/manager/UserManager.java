package top.jasonkayzk.ezshare.system.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jasonkayzk.ezshare.common.entity.view.RouterMeta;
import top.jasonkayzk.ezshare.common.entity.view.VueRouter;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.common.service.CacheService;
import top.jasonkayzk.ezshare.common.utils.ApplicationUtil;
import top.jasonkayzk.ezshare.common.utils.TreeUtil;
import top.jasonkayzk.ezshare.system.entity.Menu;
import top.jasonkayzk.ezshare.system.entity.Role;
import top.jasonkayzk.ezshare.system.entity.User;
import top.jasonkayzk.ezshare.system.entity.UserConfig;
import top.jasonkayzk.ezshare.system.service.IMenuService;
import top.jasonkayzk.ezshare.system.service.IRoleService;
import top.jasonkayzk.ezshare.system.service.IUserConfigService;
import top.jasonkayzk.ezshare.system.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 封装一些和 User相关的业务操作
 *
 * @author zk
 */
@Service("userManager")
public class UserManager {

    private CacheService cacheService;

    private IRoleService roleService;

    private IMenuService menuService;

    private IUserService userService;

    private IUserConfigService userConfigService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserConfigService(IUserConfigService userConfigService) {
        this.userConfigService = userConfigService;
    }

    /**
     * 通过用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息
     */
    public User getUser(String username) {
        return ApplicationUtil.selectCacheByTemplate(
                () -> this.cacheService.getUser(username),
                () -> this.userService.findByName(username));
    }

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    public Set<String> getUserRoles(String username) {
        List<Role> roleList = ApplicationUtil.selectCacheByTemplate(
                () -> this.cacheService.getRoles(username),
                () -> this.roleService.findUserRole(username));
        return roleList.stream().map(Role::getName).collect(Collectors.toSet());
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    public Set<String> getUserPermissions(String username) {
        List<Menu> permissionList = ApplicationUtil.selectCacheByTemplate(
                () -> this.cacheService.getPermissions(username),
                () -> this.menuService.findUserPermissions(username));
        return permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
    }

    /**
     * 通过用户名构建 Vue路由
     *
     * @param username 用户名
     * @return 路由集合
     */
    public ArrayList<VueRouter<Menu>> getUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.menuService.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setIcon(menu.getIcon());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getName());
            route.setMeta(new RouterMeta(true, null));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

    /**
     * 通过用户 ID获取前端系统个性化配置
     *
     * @param userId 用户 ID
     * @return 前端系统个性化配置
     */
    public UserConfig getUserConfig(String userId) {
        return ApplicationUtil.selectCacheByTemplate(
                () -> this.cacheService.getUserConfig(userId),
                () -> this.userConfigService.findByUserId(userId));
    }

    /**
     * 将用户相关信息添加到 Redis缓存中
     *
     * @param user user
     */
    public void loadUserRedisCache(User user) throws CacheException, JsonProcessingException {
        // 缓存用户
        cacheService.saveUser(user.getUsername());

        // 缓存用户角色
        cacheService.saveRoles(user.getUsername());

        // 缓存用户权限
        cacheService.savePermissions(user.getUsername());

        // 缓存用户个性化配置
        cacheService.saveUserConfigs(String.valueOf(user.getId()));
    }

    /**
     * 将用户角色和权限添加到 Redis缓存中
     *
     * @param userIds userIds
     */
    public void loadUserPermissionRoleRedisCache(List<String> userIds) throws CacheException, JsonProcessingException {
        for (String userId : userIds) {
            User user = userService.getById(userId);

            // 缓存用户角色
            cacheService.saveRoles(user.getUsername());

            // 缓存用户权限
            cacheService.savePermissions(user.getUsername());
        }
    }

    /**
     * 通过用户 id集合批量删除用户 Redis缓存
     *
     * @param userIds userIds
     */
    public void deleteUserRedisCache(String... userIds) throws CacheException {
        for (String userId : userIds) {
            User user = userService.getById(userId);
            if (user != null) {
                cacheService.deleteUser(user.getUsername());
                cacheService.deleteRoles(user.getUsername());
                cacheService.deletePermissions(user.getUsername());
            }
            cacheService.deleteUserConfigs(userId);
        }
    }

}