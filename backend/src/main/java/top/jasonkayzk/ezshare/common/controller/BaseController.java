package top.jasonkayzk.ezshare.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用Controller类, 用在存在分页场景下(返回分页信息)
 *
 * @author zk
 */
public abstract class BaseController {

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> response = new HashMap<>(4);
        response.put("rows", pageInfo.getRecords());
        response.put("total", pageInfo.getTotal());
        return response;
    }

}
