package top.jasonkayzk.ezshare.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Jasonkay
 */
public interface IRoleService extends IService<Role> {

    /**
     * 分页查询角色
     *
     * @param role 其他查询参数
     *
     * @param request 分页参数
     *
     * @return 角色列表
     */
    IPage<Role> findRoles(Role role, QueryRequest request);

    /**
     * 根据username
     *
     * @param username 查询角色信息
     *
     * @return 用户角色列表
     */
    List<Role> findUserRole(String username);

    /**
     * 根据角色名称查询角色信息
     *
     * @param roleName 角色名称
     *
     * @return 角色信息
     */
    Role findByName(String roleName);

    /**
     * 增加角色
     *
     * @param role 角色
     */
    void createRole(Role role);

    /**
     * 根据Id删除角色
     *
     * @param roleIds 角色Id
     */
    void deleteRoles(String[] roleIds) throws CacheException, JsonProcessingException;

    /**
     * 更新角色信息
     *
     * @param role 角色
     */
    void updateRole(Role role) throws CacheException, JsonProcessingException;

}
