package top.jasonkayzk.ezshare.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jasonkay
 */
public interface IUserService extends IService<User> {

    /**
     * 通过用户名查找用户
     *
     * @param username username
     * @return user
     */
    User findByName(String username);

    /**
     * 获取单个用户详情
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findDetailByUsername(String username);

    /**
     * 查询用户详情，包括基本信息，用户角色，用户部门
     *
     * @param user user
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<User> findUserDetail(User user, QueryRequest queryRequest);

    /**
     * 更新用户登录时间
     *
     * @param username username
     */
    void updateLoginTime(String username) throws CacheException, JsonProcessingException;

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(User user) throws CacheException, JsonProcessingException;

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(User user) throws CacheException, JsonProcessingException;

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds) throws CacheException;

    /**
     * 更新个人信息
     *
     * @param user 个人信息
     */
    void updateProfile(User user) throws CacheException, JsonProcessingException;

    /**
     * 更新用户头像
     *
     * @param username 用户名
     * @param avatar   用户头像
     */
    void updateAvatar(String username, String avatar) throws CacheException, JsonProcessingException;

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param password 新密码
     */
    void updatePassword(String username, String password) throws CacheException, JsonProcessingException;

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    void regist(String username, String password) throws CacheException, JsonProcessingException;

    /**
     * 重置密码
     *
     * @param username 用户集合
     */
    void resetPassword(String[] username) throws CacheException, JsonProcessingException;

}
