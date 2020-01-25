package top.jasonkayzk.ezshare.common.converter;

import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;
import top.jasonkayzk.ezshare.common.utils.DateUtil;

import java.text.ParseException;

/**
 * Excel导出时间类型字段格式化
 *
 * @author zk
 */
@Slf4j
public class TimeConverter implements WriteConverter {

    @Override
    public String convert(Object value) throws ExcelKitWriteConverterException {
        try {
            return value == null ? "" : DateUtil.formatCSTTime(value.toString(), DateUtil.FULL_TIME_SPLIT_PATTERN);
        } catch (ParseException e) {
            log.error("时间转换异常", e);
            return "";
        }
    }

}
