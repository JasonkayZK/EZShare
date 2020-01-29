package top.jasonkayzk.ezshare.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jasonkayzk.ezshare.system.dao.mapper.UserRoleMapper;
import top.jasonkayzk.ezshare.system.entity.UserRole;
import top.jasonkayzk.ezshare.system.service.IUserRoleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jasonkay
 */
@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    @Transactional
    public void deleteUserRolesByRoleId(String[] roleIds) {
        this.baseMapper.delete(new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, Arrays.asList(roleIds)));
    }

    @Override
    @Transactional
    public void deleteUserRolesByUserId(String[] userIds) {
        this.baseMapper.delete(new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId, Arrays.asList(userIds)));
    }

    @Override
    public List<String> findUserIdsByRoleId(String[] roleIds) {
        List<UserRole> list = baseMapper.selectList(new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, String.join(",", roleIds)));
        return list.stream().map(userRole -> String.valueOf(userRole.getUserId())).collect(Collectors.toList());
    }

    @Override
    public void insert(UserRole userRole) {
        this.baseMapper.insert(userRole);
    }

}
