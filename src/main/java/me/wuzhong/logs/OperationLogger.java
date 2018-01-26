package me.wuzhong.logs;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Slf4jReporter.LoggingLevel;
import com.codahale.metrics.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
public class OperationLogger {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogger.class);

    private static final MetricRegistry serviceMetricRegistry = new MetricRegistry();

    private static String buildTimerMetricName(ProceedingJoinPoint joinPoint) {
        return String.format("me.%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                             joinPoint.getSignature().getName());
    }

    private static String buildErrorMetricName(ProceedingJoinPoint joinPoint) {
        return String.format("me.%s.%s.error", joinPoint.getSignature().getDeclaringTypeName(),
                             joinPoint.getSignature().getName());
    }

    @Value("${me.logger.interval-secs:-1}")
    private int logReporterIntervalSecs;

    //@Before("execution(* *.*(..))")
    @Around("execution(public * *.*(..)) && @annotation(loggable)")
    public Object logOnOperation(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        Object result = null;
        Throwable throwable = null;

        Timer.Context timer = serviceMetricRegistry.timer(buildTimerMetricName(joinPoint)).time();
        try {
            //真正的方法执行
            result = joinPoint.proceed();
        } catch (Throwable t) {
            throwable = t;
            serviceMetricRegistry.meter(buildErrorMetricName(joinPoint)).mark();
        } finally {
            timer.stop();
        }

        boolean onArgs = loggable.onArgs();
        boolean onResult = loggable.onResult();
        boolean onThrowable = loggable.onThrowable();

        if (loggable.onThrowableOnly()) {
            onArgs = false;
            onResult = false;
        }

        if (onArgs || onResult || (onThrowable && throwable != null)) {
            StringBuilder sb = new StringBuilder();
            String method = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();

            sb.append("Method ").append(method).append(" ");
            if (onArgs && args.length > 0) {
                sb.append("called with ").append(Arrays.toString(args)).append(" ");
            }
            if (onThrowable && throwable != null) {

                sb.append("throws exception ");
                logger.error(sb.toString(), throwable);
                return sb.toString();
            } else {
                if (onResult && result != null) {
                    sb.append("returns ").append(result);
                }
            }
            logger.warn(sb.toString());
        }

        // wrap null in Result
        if (result == null) {
            result = true;
        }

        return result;
    }

    @PostConstruct
    public void init() {
        Slf4jReporter reporter = Slf4jReporter.forRegistry(serviceMetricRegistry)
            .outputTo(logger)
            .withLoggingLevel(LoggingLevel.INFO)
            .build();

        if (logReporterIntervalSecs > 0) {
            reporter.start(logReporterIntervalSecs, TimeUnit.SECONDS);
        }
    }
}

