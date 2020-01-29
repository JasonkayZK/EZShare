package top.jasonkayzk.ezshare.common.annotation;

import top.jasonkayzk.ezshare.common.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     * 资源名称，用于描述接口功能
     *
     * @return name 资源名称
     */
    String name() default "";

    /**
     * 资源 key
     *
     * @return 资源 key
     */
    String key() default "";

    /**
     * key prefix
     *
     * @return key 前缀
     */
    String prefix() default "";

    /**
     * 时间段，单位秒
     *
     * @return 时间段
     */
    int period();

    /**
     * 限制访问次数
     *
     * @return 限制访问次数
     */
    int count();

    /**
     * 限制类型
     *
     * @return 传统型, IP型
     */
    LimitType limitType() default LimitType.CUSTOMER;

}