package top.jasonkayzk.ezshare.common.constant;

/**
 * 正则常量
 *
 * @author zk
 */
public class RegexpConstant {

    /**
     * 手机号正则
     */
    public static final String MOBILE_REG = "^1(3|4|5|7|8)\\d{9}$";

    /**
     * 邮箱正则
     */
    public static final String MAIL_REX = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
}
