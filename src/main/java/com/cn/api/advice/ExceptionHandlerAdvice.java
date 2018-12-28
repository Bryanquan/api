package com.cn.api.advice;

import com.cn.api.Exception.ApiException;
import com.cn.api.enums.ResultCode;
import com.cn.api.util.ResultUtils;
import dto.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 异常统一处理，业务逻辑统一包装成BizServiceException，这里会将具体的错误信息反馈给前端
 *
 * @author gongxufan
 * @date 17-12-15
 **/
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    final private Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    /**
     * 异常统一处理
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultDto handleException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        StringBuilder sb = new StringBuilder();
        if (e instanceof ApiException) {//约束异常
            logger.error("uri={} | message={}", request.getRequestURI(), ((ApiException) e).getResultCode().getMsg());
            return ResultUtils.error(((ApiException) e).getResultCode());
        } else if (e instanceof ConstraintViolationException) {//参数校验失败
            logger.error("uri={} | message={}", request.getRequestURI(), e.getMessage());
            for (ConstraintViolation<?> violation : ((ConstraintViolationException) e).getConstraintViolations()) {
                sb.append(violation.getMessage()).append(",");
            }
            return ResultUtils.error(ResultCode.COMM_ARGVLIDATEFIALED, sb.deleteCharAt(sb.length() - 1).toString());
        } else if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage()).append(",");
            }
            return ResultUtils.error(ResultCode.COMM_ARGVLIDATEFIALED, sb.deleteCharAt(sb.length() - 1).toString());
        } else {
            logger.error("uri={} | message={}", request.getRequestURI(), e.getMessage());
            return ResultUtils.error(ResultCode.COMM_WEAK_NET_WORK);
        }
    }

}
