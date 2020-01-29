package top.jasonkayzk.ezshare.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.jasonkayzk.ezshare.system.entity.UserRole;

import java.util.List;

/**
 * @author Jasonkay
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 根据角色Id删除
     *
     * @param roleIds 角色Id表
     */
    void deleteUserRolesByRoleId(String[] roleIds);


    /**
     * 根据userId删除
     *
     * @param userIds userId表
     */
    void deleteUserRolesByUserId(String[] userIds);


    /**
     * 根据RoleId查找userId
     *
     * @param roleIds 角色Id
     *
     * @return userId列表
     */
    List<String> findUserIdsByRoleId(String[] roleIds);

    /**
     * 插入数据
     *
     * @param userRole userRole
     */
    void insert(UserRole userRole);

}
