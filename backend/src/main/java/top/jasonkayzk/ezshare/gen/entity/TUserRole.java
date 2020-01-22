package top.jasonkayzk.ezshare.gen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class TUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
        @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
        @TableField("ROLE_ID")
    private Long roleId;


}
