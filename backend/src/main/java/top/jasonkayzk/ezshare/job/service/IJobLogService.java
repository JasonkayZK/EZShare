package top.jasonkayzk.ezshare.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.job.entity.JobLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jasonkay
 */
public interface IJobLogService extends IService<JobLog> {

    /**
     * 分页查询定时任务日志
     *
     * @param request 分页请求
     *
     * @param jobLog 定时任务日志
     *
     * @return 日志列表
     */
    IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog);

    /**
     * 保存日志
     *
     * @param log 保存日志
     */
    void saveJobLog(JobLog log);

    /**
     * 根据Id批量删除日志
     *
     * @param jobLogIds 要删除的日志id
     */
    void deleteJobLogs(String[] jobLogIds);

}
