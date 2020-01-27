package top.jasonkayzk.ezshare.job.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("定时任务日志信息")
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
    @ApiModelProperty(value = "任务日志id", name = "id")
    private Long id;

    /**
     * 任务id
     */
    @ApiModelProperty(name = "jobId", value = "任务id")
    private Long jobId;

    /**
     * 任务状态 0：成功 1：失败
     */
    @ApiModelProperty(name = "status", value = "状态", example = "0：成功 1：失败")
    @ExcelField(value = "状态", writeConverterExp = "0=成功,1=失败")
    private String status;

    /**
     * 失败信息
     */
    @ExcelField(value = "异常信息")
    @ApiModelProperty(name = "error", value = "失败信息")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @ExcelField(value = "耗时（毫秒）")
    @ApiModelProperty(name = "times", value = "耗时(单位：毫秒)")
    private BigDecimal times;

    /**
     * 创建时间
     */
    @ExcelField(value = "执行时间", writeConverter = TimeConverter.class)
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 做数据库时间段的查询字段
     */
    private transient String timeFrom;
    private transient String timeTo;

}
