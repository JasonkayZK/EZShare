package top.jasonkayzk.ezshare.entity;

import top.jasonkayzk.ezshare.util.BeanUtils;

import java.sql.Timestamp;

/**
 * 分类表
 *
 * @author pantao
 * @since 2018/1/11
 */
public class Category {

    private int id;

    /**
     * 分类名称
     */
    private String name;

    private Timestamp createTime;


    @Override
    public String toString() {
        return BeanUtils.toPrettyJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
