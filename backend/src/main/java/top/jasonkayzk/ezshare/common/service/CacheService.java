package top.jasonkayzk.ezshare.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.system.entity.Menu;
import top.jasonkayzk.ezshare.system.entity.Role;
import top.jasonkayzk.ezshare.system.entity.User;
import top.jasonkayzk.ezshare.system.entity.UserConfig;

import java.util.List;

/**
 * @author zk
 */
public interface CacheService {

    /**
     * 测试 Redis是否连接成功
     */
    void testConnect() throws CacheException;

    /**
     * 从缓存中获取用户
     *
     * @param username 用户名
     * @return User
     */
    User getUser(String username) throws CacheException, JsonProcessingException;

    /**
     * 从缓存中获取用户角色
     *
     * @param username 用户名
     * @return 角色集
     */
    List<Role> getRoles(String username) throws CacheException, JsonProcessingException;

    /**
     * 从缓存中获取用户权限
     *
     * @param username 用户名
     * @return 权限集
     */
    List<Menu> getPermissions(String username) throws CacheException, JsonProcessingException;

    /**
     * 从缓存中获取用户个性化配置
     *
     * @param userId 用户 ID
     * @return 个性化配置
     */
    UserConfig getUserConfig(String userId) throws CacheException, JsonProcessingException;

    /**
     * 缓存用户信息，只有当用户信息是查询出来的，完整的，才应该调用这个方法
     * 否则需要调用下面这个重载方法
     *
     * @param user 用户信息
     */
    void saveUser(User user) throws CacheException, JsonProcessingException;

    /**
     * 缓存用户信息
     *
     * @param username 用户名
     */
    void saveUser(String username) throws CacheException, JsonProcessingException;

    /**
     * 缓存用户角色信息
     *
     * @param username 用户名
     */
    void saveRoles(String username) throws CacheException, JsonProcessingException;

    /**
     * 缓存用户权限信息
     *
     * @param username 用户名
     */
    void savePermissions(String username) throws CacheException, JsonProcessingException;

    /**
     * 缓存用户个性化配置
     *
     * @param userId 用户 ID
     */
    void saveUserConfigs(String userId) throws CacheException, JsonProcessingException;

    /**
     * 删除用户信息
     *
     * @param username 用户名
     */
    void deleteUser(String username) throws CacheException;

    /**
     * 删除用户角色信息
     *
     * @param username 用户名
     */
    void deleteRoles(String username) throws CacheException;

    /**
     * 删除用户权限信息
     *
     * @param username 用户名
     */
    void deletePermissions(String username) throws CacheException;

    /**
     * 删除用户个性化配置
     *
     * @param userId 用户 ID
     */
    void deleteUserConfigs(String userId) throws CacheException;

}
