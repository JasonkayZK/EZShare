package top.jasonkayzk.ezshare.common.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * 分页请求实体类
 *
 * @author zk
 */
@Data
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = 9042586558580572850L;

    /**
     * 分页大小
     */
    @Value("ezshare.pageSize")
    private int pageSize;

    /**
     * 页数
     */
    @Value("ezshare.pageNum")
    private int pageNum;

    /**
     * 排序列
     */
    private String sortField;

    /**
     * 排序顺序
     */
    private String sortOrder;

}
