package com.example.demo.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1)
public class LoggingAspects {
    @Before("execution(public * com.example.demo.*.CategoryServiceImpl.getAllCategories(..))")
    public void beforeCategoryServiceAdvice(){
        log.info("before find Categories ...");
    }

    @Before("execution(public * saveRecipe(..))")
    public void beforeSaveRecipeAdvice(){
        log.info("before save recipe ...");
    }

    @After("execution(* com.example.demo.controllers.*.*(..))")
    public void afterControllerAdvice(){
        log.info("after controller ...");
    }

    @Before("execution(* com.example.demo.controllers.*.*(..))")
    public void beforeControllerAdviceJP(JoinPoint joinPoint){
        log.info("before save recipe joinPoint...");
        log.info("... " + joinPoint.getSignature());
        log.info("... " + joinPoint.getArgs());
    }
}


