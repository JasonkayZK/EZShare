package top.jasonkayzk.ezshare.gen.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 下载历史表
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TFileDownloadLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long fileId;

    /**
     * 下载时间
     */
    private LocalDateTime createTime;


}
