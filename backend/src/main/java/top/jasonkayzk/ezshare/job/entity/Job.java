package top.jasonkayzk.ezshare.job.entity;

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
import top.jasonkayzk.ezshare.common.annotation.IsCron;
import top.jasonkayzk.ezshare.common.converter.TimeConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_job")
@Excel("定时任务信息表")
public class Job implements Serializable {

    private static final long serialVersionUID = 1281272529961285579L;

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 任务id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spring bean名称
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "Bean名称")
    private String beanName;

    /**
     * 方法名
     */
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "方法名称")
    private String methodName;

    /**
     * 参数
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "方法参数")
    private String params;

    /**
     * cron表达式
     */
    @NotBlank(message = "{required}")
    @IsCron(message = "{invalid}")
    @ExcelField(value = "Cron表达式")
    private String cronExp;

    /**
     * 任务状态 0: 正常 1: 暂停
     */
    @ExcelField(value = "状态", writeConverterExp = "0=正常,1=暂停")
    private String status;

    /**
     * 备注
     */
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 做数据库时间段的查询字段
     */
    private transient String timeFrom;
    private transient String timeTo;

}
