package top.jasonkayzk.ezshare.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.jasonkayzk.ezshare.common.annotation.LogAnnotation;
import top.jasonkayzk.ezshare.common.constant.ApplicationConstant;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.common.utils.AddressUtil;
import top.jasonkayzk.ezshare.common.utils.SortUtil;
import top.jasonkayzk.ezshare.system.entity.Log;
import top.jasonkayzk.ezshare.system.dao.mapper.LogMapper;
import top.jasonkayzk.ezshare.system.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Jasonkay
 */
@SuppressWarnings("rawtypes")
@Slf4j
@Service("logService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    final ObjectMapper objectMapper;

    public LogServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public IPage<Log> findLogs(QueryRequest request, Log log) {
        try {
            QueryWrapper<Log> queryWrapper = new QueryWrapper<>();

            if (StringUtils.isNotBlank(log.getUsername())) {
                queryWrapper.lambda().eq(Log::getUsername, log.getUsername().toLowerCase());
            }
            if (StringUtils.isNotBlank(log.getOperation())) {
                queryWrapper.lambda().like(Log::getOperation, log.getOperation());
            }
            if (StringUtils.isNotBlank(log.getLocation())) {
                queryWrapper.lambda().like(Log::getLocation, log.getLocation());
            }
            if (StringUtils.isNotBlank(log.getTimeFrom()) && StringUtils.isNotBlank(log.getTimeTo())) {
                queryWrapper.lambda()
                        .ge(Log::getCreateTime, log.getTimeFrom())
                        .le(Log::getCreateTime, log.getTimeTo());
            }

            Page<Log> page = new Page<>(request.getPageNum(), request.getPageSize());
            SortUtil.handlePageSort(request, page, "createTime", ApplicationConstant.ORDER_DESC, true);

            return this.page(page, queryWrapper);
        } catch (Exception e) {
            LogServiceImpl.log.error("获取系统日志失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteLogs(String[] logIds) {
        baseMapper.deleteBatchIds(Arrays.asList(logIds));
    }

    @Override
    @Transactional
    public void saveLog(ProceedingJoinPoint joinPoint, Log log) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        if (logAnnotation != null) {
            // 注解上的描述
            log.setOperation(logAnnotation.value());
        }

        // 请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        // 请求的方法名
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");

        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();

        // 请求的方法参数名称
        var discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = discoverer.getParameterNames(method);

        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Collections.singletonList(paramNames));
            log.setParams(params.toString());
        }

        log.setCreateTime(LocalDateTime.now());
        log.setLocation(AddressUtil.getCityInfo(log.getIp()));

        // 保存系统日志
        save(log);
    }

    /**
     * 根据方法参数名和参数递归生成字符串
     *
     * @param params     中间结果
     * @param args       方法参数值
     * @param paramNames 方法参数名
     * @return 最终结果
     * @throws JsonProcessingException json格式结果
     */
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                var set = ((Map) args[i]).keySet();
                List<Object> list = new ArrayList<>();
                List<Object> paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map) args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(params, list.toArray(), paramList);
            } else {
                if (args[i] instanceof Serializable) {
                    Class<?> aClass = args[i].getClass();
                    try {
                        aClass.getDeclaredMethod("toString", new Class[]{null});
                        // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                        params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                    } catch (NoSuchMethodException e) {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                    }
                } else if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                } else {
                    params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                }
            }
        }
        return params;
    }

}
