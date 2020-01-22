package top.jasonkayzk.ezshare.gen.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件列表
 *
 * @author Jasonkay
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 本地路径
     */
    private String localUrl;

    /**
     * 客户端访问路径
     */
    private String visitUrl;

    /**
     * 文件大小，单位bit
     */
    private Long size;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 查看权限: 0游客不允许 1游客允许 2用户允许 3会员查看
     */
    private String viewPerms;

    /**
     * 下载权限: 0游客不允许 1游客下载 2用户允许 3会员下载
     */
    private String downloadPerms;

    /**
     * 查看次数
     */
    private Long checkTimes;

    /**
     * 下载次数
     */
    private Long downloadTimes;

    /**
     * 文件标签
     */
    private String tag;

    /**
     * 文件上传者
     */
    private Long uploaderId;

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 最近一次修改时间
     */
    private LocalDateTime modifyTime;


}
