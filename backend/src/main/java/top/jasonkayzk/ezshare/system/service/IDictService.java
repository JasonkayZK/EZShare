package top.jasonkayzk.ezshare.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.system.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jasonkay
 */
public interface IDictService extends IService<Dict> {

    /**
     * 分页查询字典
     *
     * @param request 分页请求
     *
     * @param dict 查询字典实体
     *
     * @return 字典列表
     */
    IPage<Dict> findDicts(QueryRequest request, Dict dict);

    /**
     * 根据Id查询Dict
     *
     * @param id 字典的Id
     *
     * @return Dict
     */
    Dict findDictById(Long id);

    /**
     * 插入字典
     *
     * @param dict 字典实体
     */
    void createDict(Dict dict);

    /**
     * 更新字典
     *
     * @param dict 字典实体
     */
    void updateDict(Dict dict);

    /**
     * 根据Id批量删除字典
     *
     * @param dictIds 字典Id列表
     */
    void deleteDicts(String[] dictIds);

}
