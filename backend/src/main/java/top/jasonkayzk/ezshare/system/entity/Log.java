package top.jasonkayzk.ezshare.system.entity;

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
@TableName("t_log")
@Excel("系统日志表")
public class Log implements Serializable {

    private static final long serialVersionUID = 1503463893284457902L;

    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    @ExcelField(value = "操作用户")
    private String username;

    /**
     * 操作内容
     */
    @ExcelField(value = "操作描述")
    private String operation;

    /**
     * 耗时
     */
    @ExcelField(value = "耗时（毫秒）")
    private BigDecimal time;

    /**
     * 操作方法
     */
    @ExcelField(value = "执行方法")
    private String method;

    /**
     * 方法参数
     */
    @ExcelField(value = "方法参数")
    private String params;

    /**
     * 操作者IP
     */
    @ExcelField(value = "IP地址")
    private String ip;

    /**
     * 操作地点
     */
    @ExcelField(value = "操作地点")
    private String location;

    /**
     * 创建时间
     */
    @ExcelField(value = "操作时间", writeConverter = TimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 做数据库时间段的查询字段
     */
    private transient String timeFrom;
    private transient String timeTo;

}
