package top.jasonkayzk.ezshare.common.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jasonkayzk.ezshare.common.constant.ApplicationConstant;
import top.jasonkayzk.ezshare.common.exception.CacheException;
import top.jasonkayzk.ezshare.common.service.CacheService;
import top.jasonkayzk.ezshare.common.service.RedisService;
import top.jasonkayzk.ezshare.system.entity.Menu;
import top.jasonkayzk.ezshare.system.entity.Role;
import top.jasonkayzk.ezshare.system.entity.User;
import top.jasonkayzk.ezshare.system.entity.UserConfig;
import top.jasonkayzk.ezshare.system.service.IMenuService;
import top.jasonkayzk.ezshare.system.service.IRoleService;
import top.jasonkayzk.ezshare.system.service.IUserConfigService;
import top.jasonkayzk.ezshare.system.service.IUserService;

import java.util.List;

/**
 * 缓存相关业务类
 *
 * @author zk
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    private RedisService redisService;

    private IRoleService roleService;

    private IMenuService menuService;

    private IUserConfigService userConfigService;

    private IUserService userService;

    private ObjectMapper mapper;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
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
    public void setUserConfigService(IUserConfigService userConfigService) {
        this.userConfigService = userConfigService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void testConnect() throws CacheException {
        this.redisService.exists("test");
    }

    @Override
    public User getUser(String username) throws CacheException, JsonProcessingException {
        String userString = this.redisService.get(ApplicationConstant.USER_CACHE_PREFIX + username);
        if (StringUtils.isBlank(userString)) {
            throw new CacheException("无用户缓存");
        } else {
            return this.mapper.readValue(userString, User.class);
        }
    }

    @Override
    public List<Role> getRoles(String username) throws CacheException, JsonProcessingException {
        String roleListString = this.redisService.get(ApplicationConstant.USER_ROLE_CACHE_PREFIX + username);
        if (StringUtils.isBlank(roleListString)) {
            throw new CacheException("无角色缓存");
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, Role.class);
            return this.mapper.readValue(roleListString, type);
        }
    }

    @Override
    public List<Menu> getPermissions(String username) throws CacheException, JsonProcessingException {
        String permissionListString = this.redisService.get(ApplicationConstant.USER_PERMISSION_CACHE_PREFIX + username);
        if (StringUtils.isBlank(permissionListString)) {
            throw new CacheException("无权限缓存");
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, Menu.class);
            return this.mapper.readValue(permissionListString, type);
        }
    }

    @Override
    public UserConfig getUserConfig(String userId) throws CacheException, JsonProcessingException {
        String userConfigString = this.redisService.get(ApplicationConstant.USER_CONFIG_CACHE_PREFIX + userId);
        if (StringUtils.isBlank(userConfigString)) {
            throw new CacheException("无用户配置缓存");
        } else {
            return this.mapper.readValue(userConfigString, UserConfig.class);
        }
    }

    @Override
    public void saveUser(User user) throws CacheException, JsonProcessingException {
        String username = user.getUsername();
        this.deleteUser(username);
        redisService.set(ApplicationConstant.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void saveUser(String username) throws CacheException, JsonProcessingException {
        User user = userService.findDetailByUsername(username);
        this.deleteUser(username);
        redisService.set(ApplicationConstant.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void saveRoles(String username) throws CacheException, JsonProcessingException {
        List<Role> roleList = this.roleService.findUserRole(username);
        if (!roleList.isEmpty()) {
            this.deleteRoles(username);
            redisService.set(ApplicationConstant.USER_ROLE_CACHE_PREFIX + username, mapper.writeValueAsString(roleList));
        }

    }

    @Override
    public void savePermissions(String username) throws CacheException, JsonProcessingException {
        List<Menu> permissionList = this.menuService.findUserPermissions(username);
        if (!permissionList.isEmpty()) {
            this.deletePermissions(username);
            redisService.set(ApplicationConstant.USER_PERMISSION_CACHE_PREFIX + username, mapper.writeValueAsString(permissionList));
        }
    }

    @Override
    public void saveUserConfigs(String userId) throws CacheException, JsonProcessingException {
        UserConfig userConfig = this.userConfigService.findByUserId(userId);
        if (userConfig != null) {
            this.deleteUserConfigs(userId);
            redisService.set(ApplicationConstant.USER_CONFIG_CACHE_PREFIX + userId, mapper.writeValueAsString(userConfig));
        }
    }

    @Override
    public void deleteUser(String username) throws CacheException {
        username = username.toLowerCase();
        redisService.del(ApplicationConstant.USER_CACHE_PREFIX + username);
    }

    @Override
    public void deleteRoles(String username) throws CacheException {
        username = username.toLowerCase();
        redisService.del(ApplicationConstant.USER_ROLE_CACHE_PREFIX + username);
    }

    @Override
    public void deletePermissions(String username) throws CacheException {
        username = username.toLowerCase();
        redisService.del(ApplicationConstant.USER_PERMISSION_CACHE_PREFIX + username);
    }

    @Override
    public void deleteUserConfigs(String userId) throws CacheException {
        redisService.del(ApplicationConstant.USER_CONFIG_CACHE_PREFIX + userId);
    }
}
