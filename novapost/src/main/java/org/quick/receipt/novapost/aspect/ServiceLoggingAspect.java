package org.quick.receipt.novapost.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect {

    private static final int MAX_LOG_LENGTH = 500;

    @Pointcut("execution(* org.quick.receipt.novapost.service..*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logBeforeMethod(org.aspectj.lang.JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterMethod(org.aspectj.lang.JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {} with result: {}", joinPoint.getSignature().toShortString(), StringUtils.truncate(result.toString(), MAX_LOG_LENGTH));
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(org.aspectj.lang.JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in method: {} with message: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
    }
}