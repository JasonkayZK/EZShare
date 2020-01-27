package top.jasonkayzk.ezshare.job.dao.mapper;

import top.jasonkayzk.ezshare.job.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Jasonkay
 */
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 返回任务计划列表
     *
     * @return 任务计划列表
     */
    List<Job> queryList();

}
