package com.example.Aop;

import com.example.Utils.AutoFillUtils;
import com.example.annotation.AutoFill;
import com.example.enumrtation.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 自定义切面，统一为公共字段赋值
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点：拦截 mapper 包下所有方法，且标注了 @AutoFill 注解
     */
//    @Pointcut("execution(* com.example.Mapper.*.*(..)) && @annotation(com.example.annotation.AutoFill)")
    // .. 代表当前包及所有子包任意层级
    @Pointcut("execution(* com.example.Mapper..*.*(..)) && @annotation(com.example.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /**
     * 前置通知：自动填充公共字段
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
//        JoinPoint
        log.info("开始公共字段自动填充...");

        // 1. 获取方法上的 @AutoFill 注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();// 获取方法签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);// 获取注解对象
        OperationType operationType = autoFill.value();// 获取注解对象中的操作类型
//        最终目的解释获取：获取注解对象中的操作类型INSERT, UPDATE

        // 2. 获取目标方法的第一个参数（实体对象）,做约定，第一个参数为实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            log.warn("自动填充：方法无参数，跳过填充");
            return;
        }
        Object entity = args[0];/* 实体对象*/

        // 3. 调用工具类完成填充
        AutoFillUtils.fill(entity, operationType);
    }
}

