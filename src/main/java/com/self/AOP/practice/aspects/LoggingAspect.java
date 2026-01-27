package com.self.AOP.practice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging method executions across the application.
 * Provides execution time tracking and detailed method information.
 */
@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method entry with parameters before execution.
     */
    @Before("@annotation(com.self.AOP.practice.aspects.BeforeLog)")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering method: {}.{} with arguments: {}",
                className, methodName, Arrays.toString(args));
    }

    /**
     * Logs method exit with return value after successful execution.
     */
    @AfterReturning(pointcut = "@annotation(com.self.AOP.practice.aspects.BeforeLog)",
                    returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} completed successfully. Return value: {}",
                methodName, result);
    }

    /**
     * Logs exceptions thrown by methods.
     */
    @AfterThrowing(pointcut = "@annotation(com.self.AOP.practice.aspects.BeforeLog)",
                   throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Method {} threw exception: {}",
                methodName, exception.getMessage(), exception);
    }

    /**
     * Tracks execution time of methods using Around advice.
     * This provides comprehensive timing information.
     */
    @Around("@annotation(com.self.AOP.practice.aspects.BeforeLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            logger.debug("Method {} executed in {} ms", methodName, executionTime);
            return result;
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("Method {} failed after {} ms", methodName, executionTime);
            throw throwable;
        }
    }
}
