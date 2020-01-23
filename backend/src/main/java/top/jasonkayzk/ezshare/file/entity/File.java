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
import javax.validation.constraints.Size;

/**
 * 文件列表
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_file")
@Excel("文件列表")
public class File implements Serializable {

    private static final long serialVersionUID = 3850530533236412674L;

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
     * 文件名
     */
    @NotBlank(message = "{required}")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "文件名")
    private String name;

    /**
     * 文件后缀
     */
    @NotBlank(message = "{required}")
    @Size(max = 16, message = "{noMoreThan}")
    @ExcelField(value = "文件后缀")
    private String suffix;

    /**
     * 本地路径
     */
    @NotBlank(message = "{required}")
    @Size(max = 1024, message = "{noMoreThan}")
    @ExcelField(value = "本地路径")
    private String localUrl;

    /**
     * 客户端访问路径
     */
    @NotBlank(message = "{required}")
    @Size(max = 1024, message = "{noMoreThan}")
    @ExcelField(value = "客户端访问路径")
    private String visitUrl;

    /**
     * 文件大小，单位bit
     */
    @NotBlank(message = "{required}")
    @ExcelField(value = "文件大小(bit)")
    private Long size;

    /**
     * 文件描述
     */
    @Size(max = 1024, message = "{noMoreThan}")
    @ExcelField(value = "文件描述")
    private String description;

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
     * 查看次数
     */
    @ExcelField(value = "查看次数")
    private Long checkTimes;

    /**
     * 下载次数
     */
    @ExcelField(value = "下载次数")
    private Long downloadTimes;

    /**
     * 文件标签
     */
    @Size(max = 45, message = "{noMoreThan}")
    @ExcelField(value = "文件标签")
    private String tag;

    /**
     * 文件上传者
     */
    @NotBlank(message = "{required}")
    private Long uploaderId;

    /**
     * 上传者用户名
     */
    @ExcelField(value = "上传者")
    private String uploaderName;

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    @ExcelField(value = "分类名称")
    private String categoryName;

    /**
     * 上传时间
     */
    @ExcelField(value = "上传时间", writeConverter = TimeConverter.class)
    private LocalDateTime uploadTime;

    /**
     * 最近一次修改时间
     */
    @ExcelField(value = "最近一次修改时间", writeConverter = TimeConverter.class)
    private LocalDateTime modifyTime;

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
