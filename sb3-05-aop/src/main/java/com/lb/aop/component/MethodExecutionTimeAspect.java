package com.lb.aop.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
@Aspect
@Component
public class MethodExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodExecutionTimeAspect.class);

    private long startTime;

    @Before("execution(* com.example.myapp.service.*.*(..))")
    public void beforeServiceMethodExecution() {
        startTime = System.currentTimeMillis();
    }

    @After("execution(* com.example.myapp.service.*.*(..))")
    public void afterServiceMethodExecution() {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        logger.info("Method execution time: {} ms", executionTime);
    }
}*/
