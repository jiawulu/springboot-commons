package me.jiawu.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.extern.slf4j.Slf4j;
import me.jiawu.commons.result.AppError;
import me.jiawu.commons.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wuzhong on 2017/12/25.
 * @version 1.0
 */
@Aspect
@Order(0)
@Component
@Slf4j
public class ExCatcher {

    @Around("execution(public * *.*(..)) && @annotation(catchable)")
    public Object catchProceed(ProceedingJoinPoint joinPoint, Catchable catchable) {

        try {
            //真正的方法执行
            return joinPoint.proceed();
        } catch (Throwable t) {

            if (catchable.logExc()) {
                log.error("on " + joinPoint.getSignature().getName(), t);
            }

            if (null != catchable.convertor()) {
                return SpringContexts.getBean(catchable.convertor()).form(t);
            }

            return null;
        }

    }

    public static interface ExConvertor {
        Object form(Throwable e);
    }

    @Component
    public static class ResultConvertor implements ExConvertor{
        @Override
        public Object form(Throwable e) {
            return  Result.err(new AppError(1, e.getMessage()));
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface Catchable {

        boolean logExc() default true;

        Class<? extends ExConvertor> convertor();

    }



}