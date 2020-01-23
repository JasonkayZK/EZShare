package top.jasonkayzk.ezshare.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.jasonkayzk.ezshare.common.converter.TimeConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu")
@Excel("菜单信息表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 3538578751612345719L;

    /**
     * 菜单类型
     */
    public static final String TYPE_MENU = "0";

    /**
     * 按钮类型
     */
    public static final String TYPE_BUTTON = "1";

    /**
     * 菜单/按钮ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级菜单ID
     */
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ExcelField(value = "名称")
    private String name;

    /**
     * 对应路由path
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "路由path")
    private String path;

    /**
     * 对应路由组件component
     */
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "对应Vue组件")
    private String component;

    /**
     * 权限标识
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "权限")
    private String perms;

    /**
     * 图标
     */
    @ExcelField(value = "图标")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @NotBlank(message = "{required}")
    @ExcelField(value = "类型", writeConverterExp = "0=按钮,1=菜单")
    private String type;

    /**
     * 排序方式
     */
    private Double orderNum;

    /**
     * 创建时间
     */
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private LocalDateTime modifyTime;

    /**
     * 做数据库时间段的查询字段
     */
    private transient String timeFrom;
    private transient String timeTo;
}
