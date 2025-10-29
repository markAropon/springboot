package com.bootcamp.quickdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    @Before("execution(* com.bootcamp.quickdemo.services.impl.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        System.out.println(YELLOW + "[ASPECT::Logging] Service method is about to be called: " + methodName + RESET);
    }

    @Before("execution(* com.bootcamp.quickdemo.config.*.*(..))")
    public void logBeforeMethodInConfig(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        System.out.println(
                YELLOW + "[ASPECT::Logging] Configuration method is about to be called: " + methodName + RESET);
    }

    @Around("execution(* com.bootcamp.quickdemo.services.impl.*.*(..))")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        System.out
                .println(BLUE + "[ASPECT::Execution Time] " + joinPoint.getSignature() + " executed in " + duration
                        + " ms" + RESET);
        return result;
    }

    @Around("execution(* com.bootcamp.quickdemo.config.*.*(..))")
    public Object logRequestForConfig(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        System.out
                .println(BLUE + "[ASPECT::Execution Time] " + joinPoint.getSignature() + " executed in " + duration
                        + " ms" + RESET);
        return result;
    }
}
