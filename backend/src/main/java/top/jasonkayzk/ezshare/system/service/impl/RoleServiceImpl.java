package top.jasonkayzk.ezshare.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.common.utils.SortUtil;
import top.jasonkayzk.ezshare.system.entity.Role;
import top.jasonkayzk.ezshare.system.dao.mapper.RoleMapper;
import top.jasonkayzk.ezshare.system.entity.RoleMenu;
import top.jasonkayzk.ezshare.system.manager.UserManager;
import top.jasonkayzk.ezshare.system.service.IRoleMenuService;
import top.jasonkayzk.ezshare.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.jasonkayzk.ezshare.system.service.IUserRoleService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jasonkay
 */
@Slf4j
@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private IRoleMenuService roleMenuService;

    private IUserRoleService userRoleService;

    private UserManager userManager;

    @Autowired
    public void setRoleMenuService(IRoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @Autowired
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public IPage<Role> findRoles(Role role, QueryRequest request) {
        try {
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(role.getName())) {
                queryWrapper.eq(Role::getName, role.getName());
            }
            if (StringUtils.isNotBlank(role.getTimeFrom()) && StringUtils.isNotBlank(role.getTimeTo())) {
                queryWrapper
                        .ge(Role::getCreateTime, role.getTimeFrom())
                        .le(Role::getCreateTime, role.getTimeTo());
            }

            Page<Role> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);

            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取角色信息失败", e);
            return null;
        }
    }

    @Override
    public List<Role> findUserRole(String username) {
        return this.baseMapper.findUserRole(username);
    }

    @Override
    public Role findByName(String roleName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName, roleName));
    }

    @Override
    @Transactional
    public void createRole(Role role) {
        role.setCreateTime(LocalDateTime.now());
        this.save(role);

        String[] menuIds = role.getMenuId().split(StringPool.COMMA);
        setRoleMenus(role, menuIds);
    }

    @Override
    @Transactional
    public void deleteRoles(String[] roleIds) throws CacheException, JsonProcessingException {
        // 查找这些角色关联了那些用户
        List<String> userIds = this.userRoleService.findUserIdsByRoleId(roleIds);

        List<String> list = Arrays.asList(roleIds);

        baseMapper.deleteBatchIds(list);

        this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
        this.userRoleService.deleteUserRolesByRoleId(roleIds);

        // 重新将这些用户的角色和权限缓存到 Redis中
        this.userManager.loadUserPermissionRoleRedisCache(userIds);
    }

    @Override
    @Transactional
    public void updateRole(Role role) throws CacheException, JsonProcessingException {
        // 查找这些角色关联了那些用户
        String[] roleId = {String.valueOf(role.getId())};
        List<String> userIds = this.userRoleService.findUserIdsByRoleId(roleId);

        role.setModifyTime(LocalDateTime.now());
        baseMapper.updateById(role);

        roleMenuService.deleteRoleMenusByRoleId(new String[] {String.valueOf(role.getId())});

        String[] menuIds = role.getMenuId().split(StringPool.COMMA);
        setRoleMenus(role, menuIds);

        // 重新将这些用户的角色和权限缓存到 Redis中
        this.userManager.loadUserPermissionRoleRedisCache(userIds);
    }

    private void setRoleMenus(Role role, String[] menuIds) {
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu rm = new RoleMenu();
            rm.setMenuId(Long.valueOf(menuId));
            rm.setRoleId(role.getId());
            this.roleMenuService.insert(rm);
        });
    }

}
