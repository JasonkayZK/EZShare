package top.jasonkayzk.ezshare.job.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import top.jasonkayzk.ezshare.common.utils.SpringContextUtil;

import java.lang.reflect.Method;

/**
 * 执行定时任务
 *
 * @author zk
 */
@Slf4j
public class ScheduleTask implements Runnable {

    /**
     * 任务Bean
     */
    private Object target;

    /**
     * 方法
     */
    private Method method;

    /**
     * 方法参数
     */
    private String params;

    ScheduleTask(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        this.target = SpringContextUtil.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
            log.error("执行定时任务失败", e);
        }
    }

}
