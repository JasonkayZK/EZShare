package top.jasonkayzk.ezshare.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jasonkayzk.ezshare.common.annotation.LogAnnotation;
import top.jasonkayzk.ezshare.common.controller.BaseController;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.system.entity.Role;
import top.jasonkayzk.ezshare.system.entity.RoleMenu;
import top.jasonkayzk.ezshare.system.service.IRoleMenuService;
import top.jasonkayzk.ezshare.system.service.IRoleService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jasonkay
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    private final IRoleMenuService roleMenuService;

    public RoleController(IRoleService roleService, IRoleMenuService roleMenuService) {
        this.roleService = roleService;
        this.roleMenuService = roleMenuService;
    }

    private String logMessage;

    @GetMapping
    @RequiresPermissions("role:view")
    public Map<String, Object> roleList(QueryRequest queryRequest, Role role) {
        return getDataTable(roleService.findRoles(role, queryRequest));
    }

    @GetMapping("check/{roleName}")
    public boolean checkRoleName(@NotBlank(message = "{required}") @PathVariable String roleName) {
        Role result = this.roleService.findByName(roleName);
        return result == null;
    }

    @GetMapping("menu/{roleId}")
    public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String roleId) {
        List<RoleMenu> list = this.roleMenuService.getRoleMenusByRoleId(roleId);
        return list.stream().map(roleMenu -> String.valueOf(roleMenu.getMenuId())).collect(Collectors.toList());
    }

    @LogAnnotation("新增角色")
    @PostMapping
//    @RequiresPermissions("role:add")
    public void addRole(@Valid Role role) throws EzShareException {
        try {
            this.roleService.createRole(role);
        } catch (Exception e) {
            logMessage = "新增角色失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("删除角色")
    @DeleteMapping("/{roleIds}")
//    @RequiresPermissions("role:delete")
    public void deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) throws EzShareException {
        try {
            String[] ids = roleIds.split(StringPool.COMMA);
            this.roleService.deleteRoles(ids);
        } catch (Exception e) {
            logMessage = "删除角色失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("修改角色")
    @PutMapping
//    @RequiresPermissions("role:update")
    public void updateRole(Role role) throws EzShareException {
        try {
            this.roleService.updateRole(role);
        } catch (Exception e) {
            logMessage = "修改角色失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @PostMapping("excel")
//    @RequiresPermissions("role:export")
    public void export(QueryRequest queryRequest, Role role, HttpServletResponse response) throws EzShareException {
        try {
            List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
            ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
        } catch (Exception e) {
            logMessage = "导出Excel失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }


}
