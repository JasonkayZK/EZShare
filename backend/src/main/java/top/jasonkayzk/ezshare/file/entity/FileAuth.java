package top.jasonkayzk.ezshare.file.entity;

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

/**
 * 文件权限表
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_file_auth")
@Excel("文件权限表")
public class FileAuth implements Serializable {

    private static final long serialVersionUID = 3483586413693842315L;

    /**
     * 查看权限: 0游客不允许 1游客允许 2用户允许 3会员查看
     */
    public static final String VIEW_PERMS_GUEST_NOT_ALLOW = "0";
    public static final String VIEW_PERMS_GUEST_ALLOW = "1";
    public static final String VIEW_PERMS_USER_ALLOW = "2";
    public static final String VIEW_PERMS_VIP_ONLY = "3";

    /**
     * 下载权限: 0游客不允许 1游客下载 2用户允许 3会员下载
     */
    public static final String DOWNLOAD_PERMS_GUEST_NOT_ALLOW = "0";
    public static final String DOWNLOAD_PERMS_GUEST_ALLOW = "1";
    public static final String DOWNLOAD_PERMS_USER_ALLOW = "2";
    public static final String DOWNLOAD_PERMS_VIP_ONLY = "3";

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色Id
     */
    @NotBlank(message = "{required}")
    private Long roleId;

    /**
     * 角色名称
     */
    @ExcelField("角色名称")
    private String roleName;

    /**
     * 查看权限: 0游客不允许 1游客允许 2用户允许 3会员查看
     */
    @NotBlank(message = "{required}")
    @ExcelField(value = "文件查看权限", writeConverterExp = "0=游客不允许,1=游客允许,2=用户允许,3=会员查看")
    private String viewPerms;

    /**
     * 下载权限: 0游客不允许 1游客下载 2用户允许 3会员下载
     */
    @NotBlank(message = "{required}")
    @ExcelField(value = "文件下载权限", writeConverterExp = "0=游客不允许,1=游客下载,2=用户允许,3=会员下载")
    private String downloadPerms;

    /**
     * 创建时间
     */
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private LocalDateTime modifyTime;

    /**
     * 做数据库时间段的查询字段
     */
    private transient String timeFrom;
    private transient String timeTo;

}
