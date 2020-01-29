package top.jasonkayzk.ezshare.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import top.jasonkayzk.ezshare.common.utils.DateUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 在线活跃用户实体类
 *
 * @author zk
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActiveUser implements Serializable {

    private static final long serialVersionUID = 3391341238432888545L;

    /**
     * 唯一编号
     */
    private String id = RandomStringUtils.randomAlphanumeric(20);

    /**
     * 用户名
     */
    private String username;

    /**
     * ip地址
     */
    private String ip;

    /**
     * token(加密后)
     */
    private String token;

    /**
     * 登录时间
     */
    private String loginTime = DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN);

    /**
     * 登录地点
     */
    private String loginAddress;

}
