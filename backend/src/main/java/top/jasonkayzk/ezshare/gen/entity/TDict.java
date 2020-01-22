package top.jasonkayzk.ezshare.gen.entity;

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
public class TDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    private Long dictKey;

    /**
     * 值
     */
    private String dictValue;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;


}
