package com.library.librarymanagementsystem.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.library.librarymanagementsystem.services.*.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Method called: " + joinPoint.getSignature());

        Object result = null;
        try {
            result = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info("Method execution completed: " + joinPoint.getSignature() + " with result: " + result);
            logger.info("Time taken by " + joinPoint.getSignature() + " is " + timeTaken + " ms");
        } catch (Exception e) {
            logger.error("Exception in " + joinPoint.getSignature() + " with message: " + e.getMessage());
            throw e;
        }

        return result;
    }

    @Before("execution(* com.library.librarymanagementsystem.services.BookService.*(..))")
    public void logMethodCallBook() {
        logger.info("BookService  method called");
    }
    @Before(
            "execution(* com.library.librarymanagementsystem.services.PatronService.*(..))")
    public void logMethodCallPatron() {
        logger.info("Patron  method called");
    }
}
