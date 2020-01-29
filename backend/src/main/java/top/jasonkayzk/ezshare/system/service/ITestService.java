package top.jasonkayzk.ezshare.system.service;

import top.jasonkayzk.ezshare.system.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Jasonkay
 */
public interface ITestService extends IService<Test> {

    /**
     * 测试
     *
     * @return 测试列表
     */
    List<Test> findTests();

    /**
     * 批量插入
     *
     * @param list List<Test>
     */
    void batchInsert(List<Test> list);

}
