package top.jasonkayzk.ezshare.common.handler;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.jasonkayzk.ezshare.common.exception.EzShareException;
import top.jasonkayzk.ezshare.common.exception.LimitAccessException;
import top.jasonkayzk.ezshare.common.response.ApplicationResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理器
 *
 * @author zk
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationResponse<Void> handleException(Exception e) {
        log.error("系统内部异常，异常信息：", e);
        return ApplicationResponse.failWithCodeAndMsg(500, "系统内部异常");
    }

    @ExceptionHandler(value = EzShareException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationResponse<Void> handleParamsInvalidException(EzShareException e) {
        log.error("系统错误：{}", e.getMessage());
        return ApplicationResponse.failWithCodeAndMsg(500, e.getMessage());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     *
     * @return ApplicationResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationResponse<Void> validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(StringPool.COMMA);
        }
        return ApplicationResponse.failWithCodeAndMsg(400, message.substring(0, message.length() - 1));
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     *
     * @return ApplicationResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), StringPool.DOT);
            message.append(pathArr[1]).append(violation.getMessage()).append(StringPool.COMMA);
        }
        return ApplicationResponse.failWithCodeAndMsg(400, message.substring(0, message.length() - 1));
    }

    @ExceptionHandler(value = LimitAccessException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ApplicationResponse<Void> handleLimitAccessException(LimitAccessException e) {
        log.warn(e.getMessage());
        return ApplicationResponse.failWithCodeAndMsg(429, e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApplicationResponse<Void> handleUnauthorizedException(Exception e) {
        log.error("权限不足，{}", e.getMessage());
        return ApplicationResponse.failWithCodeAndMsg(403, e.getMessage());
    }

}
