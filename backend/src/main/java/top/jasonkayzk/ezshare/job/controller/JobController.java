package top.jasonkayzk.ezshare.job.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.jasonkayzk.ezshare.common.annotation.LogAnnotation;
import top.jasonkayzk.ezshare.common.controller.BaseController;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.common.response.ApplicationResponse;
import top.jasonkayzk.ezshare.job.entity.Job;
import top.jasonkayzk.ezshare.job.service.IJobService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Jasonkay
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/job")
@Api(value = "Job", tags = {"定时任务: Job"})
public class JobController extends BaseController {

    private String logMessage;

    private final IJobService jobService;

    public JobController(IJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
//    @RequiresPermissions("job:view")
    public ApplicationResponse<Map<String, Object>> jobList(QueryRequest request, Job job) {
        return ApplicationResponse.successWithData(getDataTable(this.jobService.findJobs(request, job)));
    }

    @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @LogAnnotation("新增定时任务")
    @PostMapping
//    @RequiresPermissions("job:add")
    public ApplicationResponse<Void> addJob(@Valid Job job) throws EzShareException {
        try {
            this.jobService.createJob(job);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "新增定时任务失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("删除定时任务")
    @DeleteMapping("/{jobIds}")
//    @RequiresPermissions("job:delete")
    public ApplicationResponse<Void> deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws EzShareException {
        try {
            String[] ids = jobIds.split(StringPool.COMMA);
            this.jobService.deleteJobs(ids);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "删除定时任务失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("修改定时任务")
    @PutMapping
//    @RequiresPermissions("job:update")
    public ApplicationResponse<Void> updateJob(@Valid Job job) throws EzShareException {
        try {
            this.jobService.updateJob(job);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "修改定时任务失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("执行定时任务")
    @GetMapping("run/{jobId}")
//    @RequiresPermissions("job:run")
    public ApplicationResponse<Void> runJob(@NotBlank(message = "{required}") @PathVariable String jobId) throws EzShareException {
        try {
            this.jobService.run(jobId);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "执行定时任务失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("暂停定时任务")
    @GetMapping("pause/{jobId}")
//    @RequiresPermissions("job:pause")
    public ApplicationResponse<Void> pauseJob(@NotBlank(message = "{required}") @PathVariable String jobId) throws EzShareException {
        try {
            this.jobService.pause(jobId);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "暂停定时任务失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("恢复定时任务")
    @GetMapping("resume/{jobId}")
//    @RequiresPermissions("job:resume")
    public ApplicationResponse<Void> resumeJob(@NotBlank(message = "{required}") @PathVariable String jobId) throws EzShareException {
        try {
            this.jobService.resume(jobId);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "恢复定时任务失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @PostMapping("excel")
//    @RequiresPermissions("job:export")
    public ApplicationResponse<Void> export(QueryRequest request, Job job, HttpServletResponse response) throws EzShareException {
        try {
            List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
            ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "导出Excel失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }
}
