package com.bootcamp.quickdemo.aspect;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bootcamp.quickdemo.common.RateLimit;
import com.bootcamp.quickdemo.exception.RateLimitExceededException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class RateLimitAspect {

    // Cache: key = client identifier (IP + method), value = counter
    private final Cache<String, AtomicInteger> requestCounts = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Around("@within(rateLimit) || @annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        RateLimit effectiveRateLimit = rateLimit;
        if (effectiveRateLimit == null) {
            effectiveRateLimit = joinPoint.getTarget().getClass().getAnnotation(RateLimit.class);
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes servletAttributes)) {
            // Defensive fallback: allow if not in a web context
            return joinPoint.proceed();
        }

        HttpServletRequest request = servletAttributes.getRequest();
        String clientIp = getClientIp(request);
        String key = clientIp + ":" + joinPoint.getSignature().toShortString();

        AtomicInteger counter = requestCounts.get(key, k -> new AtomicInteger(0));

        if (counter.incrementAndGet() > effectiveRateLimit.limit()) {
            throw new RateLimitExceededException("Too many requests. Please try again later.");
        }

        final RateLimit finalRateLimit = effectiveRateLimit;
        requestCounts.policy().expireVariably()
                .ifPresent(policy -> policy.put(key, counter, finalRateLimit.durationSeconds(), TimeUnit.SECONDS));

        return joinPoint.proceed();
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        return xfHeader == null ? request.getRemoteAddr() : xfHeader.split(",")[0];
    }
}
