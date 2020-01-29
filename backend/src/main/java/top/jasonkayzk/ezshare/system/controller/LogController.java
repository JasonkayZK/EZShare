package top.jasonkayzk.ezshare.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.jasonkayzk.ezshare.common.annotation.LogAnnotation;
import top.jasonkayzk.ezshare.common.controller.BaseController;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.common.response.ApplicationResponse;
import top.jasonkayzk.ezshare.system.entity.Log;
import top.jasonkayzk.ezshare.system.service.ILogService;

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
@RequestMapping("/system/log")
@Api(value = "Log", tags = {"系统日志: Log"})
public class LogController extends BaseController {

    private String logMessage;

    private final ILogService logService;

    public LogController(ILogService logService) {
        this.logService = logService;
    }

    @GetMapping
    @ApiOperation("查询系统日志")
//    @RequiresPermissions("log:view")
    public ApplicationResponse<Map<String, Object>> getLogList(QueryRequest request, Log log) {
        return ApplicationResponse.successWithData(getDataTable(logService.findLogs(request, log)));
    }

    @LogAnnotation("删除系统日志")
    @DeleteMapping("/{ids}")
    @ApiOperation("删除系统日志")
//    @RequiresPermissions("log:delete")
    public void deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) throws EzShareException {
        try {
            String[] logIds = ids.split(StringPool.COMMA);
            this.logService.deleteLogs(logIds);
        } catch (Exception e) {
            logMessage = "删除日志失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @PostMapping("excel")
//    @RequiresPermissions("log:export")
    @ApiOperation("导出Excel")
    public ApplicationResponse<Object> export(QueryRequest request, Log sysLog, HttpServletResponse response) throws EzShareException {
        try {
            List<Log> sysLogs = this.logService.findLogs(request, sysLog).getRecords();
            ExcelKit.$Export(Log.class, response).downXlsx(sysLogs, false);
            return ApplicationResponse.success();
        } catch (Exception e) {
            logMessage = "导出Excel失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }
}
