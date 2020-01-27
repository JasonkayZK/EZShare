package top.jasonkayzk.ezshare.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jasonkayzk.ezshare.common.constant.ApplicationConstant;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.utils.SortUtil;
import top.jasonkayzk.ezshare.job.dao.mapper.JobMapper;
import top.jasonkayzk.ezshare.job.entity.Job;
import top.jasonkayzk.ezshare.job.enums.ScheduleStatusEnum;
import top.jasonkayzk.ezshare.job.service.IJobService;
import top.jasonkayzk.ezshare.job.util.ScheduleUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jasonkay
 */
@Slf4j
@Service("JobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

    private final Scheduler scheduler;

    public JobServiceImpl(@Qualifier("schedulerFactoryBean") Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<Job> scheduleJobList = this.baseMapper.queryList();
        // 如果不存在，则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }

    @Override
    public Job findJob(Long jobId) {
        return this.getById(jobId);
    }

    @Override
    public IPage<Job> findJobs(QueryRequest request, Job job) {
        try {
            LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(job.getBeanName())) {
                queryWrapper.eq(Job::getBeanName, job.getBeanName());
            }
            if (StringUtils.isNotBlank(job.getMethodName())) {
                queryWrapper.eq(Job::getMethodName, job.getMethodName());
            }
            if (StringUtils.isNotBlank(job.getParams())) {
                queryWrapper.like(Job::getParams, job.getParams());
            }
            if (StringUtils.isNotBlank(job.getRemark())) {
                queryWrapper.like(Job::getRemark, job.getRemark());
            }
            if (StringUtils.isNotBlank(job.getStatus())) {
                queryWrapper.eq(Job::getStatus, job.getStatus());
            }

            if (StringUtils.isNotBlank(job.getTimeFrom()) && StringUtils.isNotBlank(job.getTimeTo())) {
                queryWrapper
                        .ge(Job::getCreateTime, job.getTimeFrom())
                        .le(Job::getCreateTime, job.getTimeTo());
            }
            Page<Job> page = new Page<>(request.getPageNum(), request.getPageSize());
            SortUtil.handlePageSort(request, page, "createTime", ApplicationConstant.ORDER_DESC, true);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取任务失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createJob(Job job) {
        job.setCreateTime(LocalDateTime.now());
        job.setStatus(ScheduleStatusEnum.PAUSE.getValue());
        this.save(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    @Override
    @Transactional
    public void updateJob(Job job) {
        ScheduleUtils.updateScheduleJob(scheduler, job);
        this.baseMapper.updateById(job);
    }

    @Override
    @Transactional
    public void deleteJobs(String[] jobIds) {
        List<String> list = Arrays.asList(jobIds);
        list.forEach(jobId -> ScheduleUtils.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public int updateBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(jobIds.split(StringPool.COMMA));
        Job job = new Job();
        job.setStatus(status);
        return this.baseMapper.update(job, new LambdaQueryWrapper<Job>().in(Job::getId, list));
    }

    @Override
    @Transactional
    public void run(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.run(scheduler, this.findJob(Long.valueOf(jobId))));
    }

    @Override
    @Transactional
    public void pause(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, ScheduleStatusEnum.PAUSE.getValue());
    }

    @Override
    @Transactional
    public void resume(String jobIds) {
        String[] list = jobIds.split(StringPool.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, ScheduleStatusEnum.NORMAL.getValue());
    }
}
