package top.jasonkayzk.ezshare.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.system.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 操作日志业务类
 *
 * @author Jasonkay
 */
public interface ILogService extends IService<Log> {

    /**
     * 查询操作日志
     *
     * @param request 分页请求
     *
     * @param sysLog 日志类
     *
     * @return 满足sysLog条件的日志
     */
    IPage<Log> findLogs(QueryRequest request, Log sysLog);

    /**
     * 批量删除日志
     *
     * @param logIds 删除日志Id
     */
    void deleteLogs(String[] logIds);


    /**
     * 异步保存操作日志
     *
     * @param point 获取实现类的信息
     *
     * @param log 日志
     *
     * @throws JsonProcessingException Json处理错误
     *
     */
    @Async
    void saveLog(ProceedingJoinPoint point, Log log) throws JsonProcessingException;

}
