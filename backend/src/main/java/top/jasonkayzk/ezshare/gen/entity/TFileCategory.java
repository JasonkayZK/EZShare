package top.jasonkayzk.ezshare.gen.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件分类
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TFileCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;


}
