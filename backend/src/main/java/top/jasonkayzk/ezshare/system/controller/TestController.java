package top.jasonkayzk.ezshare.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.jasonkayzk.ezshare.common.constant.ApplicationConstant;
import top.jasonkayzk.ezshare.common.controller.BaseController;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.common.response.ApplicationResponse;
import top.jasonkayzk.ezshare.system.entity.Test;
import top.jasonkayzk.ezshare.system.service.ITestService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author Jasonkay
 */
@Slf4j
@RestController
@RequestMapping("/system/test")
public class TestController extends BaseController {

    private String logMessage;

    private final ITestService testService;

    public TestController(ITestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public Map<String, Object> findTests(QueryRequest request) {
        Page<Test> page = new Page<>(request.getPageNum(), request.getPageSize());
        return getDataTable(testService.page(page, null));
    }

    /**
     * 生成 Excel导入模板
     */
    @PostMapping("template")
    public void generateImportTemplate(HttpServletResponse response) {
        // 构建数据
        List<Test> list = new ArrayList<>();
        IntStream.range(0, 20).forEach(i -> list.add(new Test().setField1("字段1").setField2(i + 1).setField3("Jasonkay" + i + "@163.com")));

        // 构建模板
        ExcelKit.$Export(Test.class, response).downXlsx(list, true);
    }

    /**
     * 导入Excel数据，并批量插入 T_TEST表
     */
    @PostMapping("import")
    public ApplicationResponse<ImmutableMap<String, Object>> importExcels(@RequestParam("file") MultipartFile file) throws EzShareException {
        try {
            if (file.isEmpty()) {
                throw new EzShareException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ApplicationConstant.EXCEL_SUFFIX)) {
                throw new EzShareException("只支持.xlsx类型文件导入");
            }

            // 开始导入操作
            long beginTimeMillis = System.currentTimeMillis();
            final List<Test> data = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(Test.class).readXlsx(file.getInputStream(), new ExcelReadHandler<Test>() {
                @Override
                public void onSuccess(int sheet, int row, Test test) {
                    // 数据校验成功时，加入集合
                    test.setCreateTime(LocalDateTime.now());
                    data.add(test);
                }
                @Override
                public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
                }
            });

            if (!data.isEmpty()) {
                // 将合法的记录批量入库
                this.testService.batchInsert(data);
            }
            long time = ((System.currentTimeMillis() - beginTimeMillis));
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", time,
                    "data", data,
                    "error", error
            );

            return ApplicationResponse.successWithData(result);
        } catch (Exception e) {
            logMessage = "导入Excel数据失败," + e.getMessage();
            log.error(logMessage);
            throw new EzShareException(logMessage);
        }
    }

    /**
     * 导出 Excel
     */
    @PostMapping("export")
    public void export(HttpServletResponse response) throws EzShareException {
        try {
            List<Test> list = this.testService.findTests();
            ExcelKit.$Export(Test.class, response).downXlsx(list, false);
        } catch (Exception e) {
            logMessage = "导出Excel失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }


}
