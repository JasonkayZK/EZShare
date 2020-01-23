package top.jasonkayzk.ezshare.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.jasonkayzk.ezshare.common.converter.TimeConverter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 下载历史表
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_file_download_log")
@Excel("下载历史表")
public class FileDownloadLog implements Serializable {

    private static final long serialVersionUID = 5129201741672406668L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @ExcelField("下载用户名")
    private String username;

    private Long fileId;

    @ExcelField("文件名称")
    private String fileName;

    /**
     * 下载时间
     */
    @ExcelField(value = "下载时间", writeConverter = TimeConverter.class)
    private LocalDateTime createTime;

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
