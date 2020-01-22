package top.jasonkayzk.ezshare.gen.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TJobLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * 任务状态 0：成功 1：失败
     */
    private String status;

    /**
     * 失败信息
     */
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    private BigDecimal times;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
