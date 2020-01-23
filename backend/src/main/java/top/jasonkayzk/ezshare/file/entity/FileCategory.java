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
 * 文件分类
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_file_category")
@Excel("文件分类表")
public class FileCategory implements Serializable {

    private static final long serialVersionUID = -1001715739156191975L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Size(max = 50, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String name;

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
