package top.jasonkayzk.ezshare.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.jasonkayzk.ezshare.common.annotation.LogAnnotation;
import top.jasonkayzk.ezshare.common.controller.BaseController;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.system.entity.Dict;
import top.jasonkayzk.ezshare.system.service.IDictService;

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
@RequestMapping("/system/dict")
public class DictController extends BaseController {

    private String logMessage;

    private final IDictService dictService;

    public DictController(IDictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping
//    @RequiresPermissions("dict:view")
    public Map<String, Object> getDictList(QueryRequest request, Dict dict) {
        return getDataTable(this.dictService.findDicts(request, dict));
    }

    @GetMapping("/{id}")
//    @RequiresPermissions("dict:select")
    public Dict getDict(@PathVariable Long id) {
        return this.dictService.findDictById(id);
    }

    @LogAnnotation("新增字典")
    @PostMapping
//    @RequiresPermissions("dict:add")
    public void addDict(@Valid Dict dict) throws EzShareException {
        try {
            this.dictService.createDict(dict);
        } catch (Exception e) {
            logMessage = "新增字典失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("修改字典")
    @PutMapping
//    @RequiresPermissions("dict:update")
    public void updateDict(@Valid Dict dict) throws EzShareException {
        try {
            this.dictService.updateDict(dict);
        } catch (Exception e) {
            logMessage = "修改字典失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @LogAnnotation("删除字典")
    @DeleteMapping("/{dictIds}")
//    @RequiresPermissions("dict:delete")
    public void deleteDicts(@NotBlank(message = "{required}") @PathVariable String dictIds) throws EzShareException {
        try {
            String[] ids = dictIds.split(StringPool.COMMA);
            this.dictService.deleteDicts(ids);
        } catch (Exception e) {
            logMessage = "删除字典失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

    @PostMapping("excel")
//    @RequiresPermissions("dict:export")
    public void export(QueryRequest request, Dict dict, HttpServletResponse response) throws EzShareException {
        try {
            List<Dict> dicts = this.dictService.findDicts(request, dict).getRecords();
            ExcelKit.$Export(Dict.class, response).downXlsx(dicts, false);
        } catch (Exception e) {
            logMessage = "导出Excel失败";
            log.error(logMessage, e);
            throw new EzShareException(logMessage);
        }
    }

}
