package top.jasonkayzk.ezshare.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.jasonkayzk.ezshare.common.authentication.JWTUtil;
import top.jasonkayzk.ezshare.common.utils.HttpContextUtil;
import top.jasonkayzk.ezshare.common.utils.IPUtil;
import top.jasonkayzk.ezshare.system.entity.Log;
import top.jasonkayzk.ezshare.system.service.ILogService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * AOP 记录用户操作日志
 *
 * @author zk
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private final ILogService logService;

    @Value("${ezshare.openAopLog}")
    private boolean isOpenAopLog;

    public LogAspect(ILogService logService) {
        this.logService = logService;
    }

    @Pointcut("@annotation(top.jasonkayzk.ezshare.common.annotation.LogAnnotation)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();

        // 执行方法
        result = point.proceed();

        // 获取 request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

        // 设置 IP 地址
        String ip = IPUtil.getIpAddr(request);

        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (isOpenAopLog) {
            // 保存日志
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            String username = "";
            if (StringUtils.isNotBlank(token)) {
                username = JWTUtil.getUsername(token);
            }

            Log log = new Log();
            log.setUsername(username);
            log.setIp(ip);
            log.setTime(BigDecimal.valueOf(time));
            logService.saveLog(point, log);
        }

        return result;
    }
}
