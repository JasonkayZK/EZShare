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
import top.jasonkayzk.ezshare.common.entity.view.VueRouter;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.system.entity.Menu;
import top.jasonkayzk.ezshare.system.manager.UserManager;
import top.jasonkayzk.ezshare.system.service.IMenuService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jasonkay
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    private String logMessage;

    private final UserManager userManager;

    private final IMenuService menuService;

    public MenuController(UserManager userManager, IMenuService menuService) {
        this.userManager = userManager;
        this.menuService = menuService;
    }

    @GetMapping("/{username}")
    public ArrayList<VueRouter<Menu>> getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userManager.getUserRouters(username);
    }

    @GetMapping
    @RequiresPermissions("menu:view")
    public Map<String, Object> menuList(Menu menu) {
        return this.menuService.findMenus(menu);
    }

    @LogAnnotation("新增菜单/按钮")
    @PostMapping
    @RequiresPermissions("menu:add")
    public void addMenu(@Valid Menu menu) throws EzShareException {
        try {
            this.menuService.createMenu(menu);
        } catch (Exception e) {
            logMessage = "新增菜单/按钮失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("删除菜单/按钮")
    @DeleteMapping("/{menuIds}")
    @RequiresPermissions("menu:delete")
    public void deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) throws EzShareException {
        try {
            String[] ids = menuIds.split(StringPool.COMMA);
            this.menuService.deleteMenus(ids);
        } catch (Exception e) {
            logMessage = "删除菜单/按钮失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("修改菜单/按钮")
    @PutMapping
    @RequiresPermissions("menu:update")
    public void updateMenu(@Valid Menu menu) throws EzShareException {
        try {
            this.menuService.updateMenu(menu);
        } catch (Exception e) {
            logMessage = "修改菜单/按钮失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("menu:export")
    public void export(Menu menu, HttpServletResponse response) throws EzShareException {
        try {
            List<Menu> menus = this.menuService.findMenuList(menu);
            ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
        } catch (Exception e) {
            logMessage = "导出Excel失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

}
