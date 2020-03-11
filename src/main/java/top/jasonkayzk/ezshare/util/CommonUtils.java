package top.jasonkayzk.ezshare.util;

import com.zhazhapan.modules.constant.ValueConsts;
import top.jasonkayzk.ezshare.modules.constant.DefaultValues;

/**
 * @author pantao
 * @since 2018/1/29
 */
public class CommonUtils {

    private CommonUtils() {}

    /**
     * 将相对路径转换成绝对路径
     *
     * @param path 文件路径
     *
     * @return {@link String}
     */
    public static String checkPath(String path) {
        String prefix = DefaultValues.COLON + ValueConsts.SEPARATOR;
        return path.startsWith(ValueConsts.SEPARATOR) || path.startsWith(prefix, ValueConsts.ONE_INT) ? path :
                DefaultValues.STORAGE_PATH + path;
    }
}
