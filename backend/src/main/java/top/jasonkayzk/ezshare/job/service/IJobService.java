package top.jasonkayzk.ezshare.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.job.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jasonkay
 */
public interface IJobService extends IService<Job> {

    /**
     * 通过id查询定时任务
     *
     * @param jobId 定时任务Id
     *
     * @return 定时任务
     */
    Job findJob(Long jobId);

    /**
     * 分页查询定时任务
     *
     * @param request 分页查询分页
     * @param job 定时任务
     * @return 分页查定时任务结果
     */
    IPage<Job> findJobs(QueryRequest request, Job job);

    /**
     * 创建定时任务
     *
     * @param job 定时任务
     */
    void createJob(Job job);

    /**
     * 更新定时任务内容
     *
     * @param job 定时任务
     */
    void updateJob(Job job);

    /**
     * 删除定时任务
     *
     * @param jobIds 定时任务ID
     */
    void deleteJobs(String[] jobIds);

    /**
     * 根据Id批量更新定时任务状态
     *
     * @param jobIds 定时任务Id
     * @param status 定时任务状态
     * @return 更新条数
     */
    int updateBatch(String jobIds, String status);

    /**
     * 批量执行指定Id的任务
     *
     * @param jobIds 任务Id
     */
    void run(String jobIds);

    /**
     * 批量暂停指定Id的任务
     *
     * @param jobIds 任务Id
     */
    void pause(String jobIds);

    /**
     * 批量恢复指定Id的任务
     *
     * @param jobIds 任务Id
     */
    void resume(String jobIds);

}
