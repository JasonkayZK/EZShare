package top.jasonkayzk.ezshare.job.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.jasonkayzk.ezshare.common.controller.BaseController;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.common.response.ApplicationResponse;
import top.jasonkayzk.ezshare.job.entity.JobLog;
import top.jasonkayzk.ezshare.job.service.IJobLogService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Jasonkay
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/job/log")
@Api(value = "JobLog", tags = {"定时任务日志: JobLog"})
public class JobLogController extends BaseController {

    private String logMessage;

    private final IJobLogService jobLogService;

    public JobLogController(IJobLogService jobLogService) {
        this.jobLogService = jobLogService;
    }

    @GetMapping
//    @RequiresPermissions("jobLog:view")
    public ApplicationResponse<Map<String, Object>> jobLogList(QueryRequest request, JobLog log) {
        return ApplicationResponse.successWithData(getDataTable(this.jobLogService.findJobLogs(request, log)));
    }

    @DeleteMapping("/{jobIds}")
//    @RequiresPermissions("jobLog:delete")
    public ApplicationResponse<Void> deleteJobLog(@NotBlank(message = "{required}") @PathVariable String jobIds) throws EzShareException {
        try {
            String[] ids = jobIds.split(StringPool.COMMA);
            this.jobLogService.deleteJobLogs(ids);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "删除调度日志失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @PostMapping("excel")
//    @RequiresPermissions("jobLog:export")
    public ApplicationResponse<Void> export(QueryRequest request, JobLog jobLog, HttpServletResponse response) throws EzShareException {
        try {
            List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
            ExcelKit.$Export(JobLog.class, response).downXlsx(jobLogs, false);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "导出Excel失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }
}
