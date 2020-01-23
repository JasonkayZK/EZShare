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
import top.jasonkayzk.ezshare.common.constant.RegexpConstant;
import top.jasonkayzk.ezshare.common.converter.TimeConverter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
@Excel("用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = -2555002954542245098L;

    /**
     * 账户状态
     */
    public static final String STATUS_VALID = "1";
    public static final String STATUS_LOCK = "0";

    /**
     * 默认头像
     */
    public static final String DEFAULT_AVATAR = "default.jpg";

    /**
     * 性别
     */
    public static final String SEX_MALE = "0";
    public static final String SEX_FEMALE = "1";
    public static final String SEX_UNKNOWN = "2";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @Size(min = 4, max = 10, message = "{range}")
    @ExcelField(value = "用户名")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    @Size(max = 50, message = "{noMoreThan}")
    @Email(message = "{email}")
    @ExcelField(value = "邮箱")
    private String email;

    /**
     * 联系电话
     */
    @Pattern(regexp = RegexpConstant.MOBILE_REG, message = "{mobile}")
    @ExcelField(value = "手机号")
    private String mobile;

    /**
     * 状态 0锁定 1有效
     */
    @NotBlank(message = "{required}")
    @ExcelField(value = "状态", writeConverterExp = "0=锁定,1=有效")
    private String status;

    /**
     * 性别 0男 1女 2保密
     */
    @NotBlank(message = "{required}")
    @ExcelField(value = "性别", writeConverterExp = "0=男,1=女,2=保密")
    private String sex;

    /**
     * 描述
     */
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "个人描述")
    private String description;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 最近访问时间
     */
    @ExcelField(value = "最后登录时间", writeConverter = TimeConverter.class)
    private LocalDateTime lastLoginTime;

    /**
     * 创建时间
     */
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ExcelField(value = "个人信息修改时间", writeConverter = TimeConverter.class)
    private LocalDateTime modifyTime;

    @NotBlank(message = "{required}")
    private transient String roleId;

    @ExcelField(value = "角色")
    private transient String roleName;

    /**
     * 排序字段
     */
    private transient String sortField;

    /**
     * 排序规则: ascend 升序 descend 降序
     */
    private transient String sortOrder;

    /**
     * 做数据库时间段的查询字段
     */
    private transient String timeFrom;
    private transient String timeTo;

}
