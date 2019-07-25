package com.cn.api.service.fdfs.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class LogAspect {
    private static final Log logger = LogFactory.getLog(LogAspect.class);

    @Pointcut("execution(* com.cn.api.service.fdfs.*.*.*(..)))")
     public void fdfsPointcut(){
    }

    @Around("fdfsPointcut()")
    public void around(JoinPoint joinPoint) throws Throwable{
        Signature signature = joinPoint.getSignature();
        ProceedingJoinPoint proJoinPoint = (ProceedingJoinPoint)joinPoint;
        Class clazz = joinPoint.getTarget().getClass();
        Object obj = proJoinPoint.proceed();//执行方法后方法返回值
        logger.info(obj.toString());
    }

    @AfterThrowing(pointcut = "fdfsPointcut()",throwing = "e")
    public void dealException(JoinPoint joinPoint,Exception e){
        //出错行
        int lineNumber = e.getStackTrace()[0].getLineNumber();
        //方法签名
        Signature signature = joinPoint.getSignature();
        //参数
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder();
        if (args.length > 0) {
            for (Object o : args) {
                builder.append(o);
            }
        }
        logger.error("方法:" + signature + " | " + "参数:" + builder.toString() +
                " | " + "错误行：" + lineNumber + " | " + "时间:" + new Date() +
                " | " + "异常内容:" + e.toString()
        );
    }
}
