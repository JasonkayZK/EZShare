package top.jasonkayzk.ezshare.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jasonkayzk.ezshare.common.constant.ApplicationConstant;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.utils.SortUtil;
import top.jasonkayzk.ezshare.job.entity.JobLog;
import top.jasonkayzk.ezshare.job.dao.mapper.JobLogMapper;
import top.jasonkayzk.ezshare.job.service.IJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jasonkay
 */
@Slf4j
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements IJobLogService {

    @Override
    public IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog) {
        try {
            LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(jobLog.getStatus())) {
                queryWrapper.eq(JobLog::getStatus, jobLog.getStatus());
            }
            if (StringUtils.isNotBlank(jobLog.getTimeFrom()) && StringUtils.isNotBlank(jobLog.getTimeTo())) {
                queryWrapper
                        .ge(JobLog::getCreateTime, jobLog.getTimeFrom())
                        .le(JobLog::getCreateTime, jobLog.getTimeTo());
            }
            Page<JobLog> page = new Page<>(request.getPageNum(), request.getPageSize());
            SortUtil.handlePageSort(request, page, "createTime", ApplicationConstant.ORDER_DESC, true);

            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取调度日志信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void saveJobLog(JobLog log) {
        this.save(log);
    }

    @Override
    @Transactional
    public void deleteJobLogs(String[] jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds);
        this.baseMapper.deleteBatchIds(list);
    }

}
