package com.bootcamp.quickdemo.common; 
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    int limit() default 5;
    int durationSeconds() default 60;
}
