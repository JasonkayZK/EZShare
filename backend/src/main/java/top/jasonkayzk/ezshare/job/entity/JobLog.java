package top.jasonkayzk.ezshare.job.entity;

import java.math.BigDecimal;

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

/**
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_job_log")
@Excel("定时任务日志信息表")
public class JobLog implements Serializable {

    private static final long serialVersionUID = -8724464044182873719L;

    /**
     * 任务执行成功
     */
    public static final String SUCCESS = "0";

    /**
     * 任务执行失败
     */
    public static final String FAIL = "1";

    /**
     * 任务日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * Bean名称
     */
    @ExcelField(value = "Bean名称")
    private String beanName;

    /**
     * 方法名称
     */
    @ExcelField(value = "方法名称")
    private String methodName;

    /**
     * 方法参数
     */
    @ExcelField(value = "方法参数")
    private String params;

    /**
     * 任务状态 0：成功 1：失败
     */
    @ExcelField(value = "状态", writeConverterExp = "0=成功,1=失败")
    private String status;

    /**
     * 失败信息
     */
    @ExcelField(value = "异常信息")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @ExcelField(value = "耗时（毫秒）")
    private BigDecimal times;

    /**
     * 创建时间
     */
    @ExcelField(value = "执行时间", writeConverter = TimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 做数据库时间段的查询字段
     */
    private transient String timeFrom;
    private transient String timeTo;

}
