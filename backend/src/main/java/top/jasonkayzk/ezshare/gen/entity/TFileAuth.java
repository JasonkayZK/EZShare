package top.jasonkayzk.ezshare.gen.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件权限表
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TFileAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 查看权限: 0游客不允许 1游客允许 2用户允许 3会员查看
     */
    private String viewPerms;

    /**
     * 下载权限: 0游客不允许 1游客下载 2用户允许 3会员下载
     */
    private String downloadPerms;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    private LocalDateTime modifyTime;


}
