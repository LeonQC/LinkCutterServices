package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect//代表当前的class是切面类
@Component
public class GeneralAdvice {
    //统计目标方法的耗时时间
    private static final Logger logger = LoggerFactory.getLogger(GeneralAdvice.class);

    @Pointcut("execution(* com.controller..*(..)) && !execution(* org.springframework.security.core.userdetails.UserDetailsService.*(..))")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object logControllerExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecutionTime(joinPoint, "Controller");
    }

    // 切入点 - Service层
    @Pointcut("execution(* com.service..*(..)) && !execution(* org.springframework.security.core.userdetails.UserDetailsService.*(..))")
    public void servicePointcut() {}

    @Around("servicePointcut()")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecutionTime(joinPoint, "Service");
    }
    // 切入点 - Repository层
    @Pointcut("execution(* com.repository..*(..)) && !execution(* org.springframework.security.core.userdetails.UserDetailsService.*(..))")
    public void repositoryPointcut() {}

    @Around("repositoryPointcut()")
    public Object logRepositoryExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecutionTime(joinPoint, "Repository");
    }


//代表我们访问的是com.student下面的包，以及子包的所有select方法
    public Object logExecutionTime(ProceedingJoinPoint pjp, String layer) throws Throwable {
        //ProceedingJoinPoint()就代表了我们要统计的目标方法
        String methodName = pjp.getSignature().toShortString();
        Object[] args = pjp.getArgs();
        //1.目标方法运行前，记录开始时间start
        long start = System.currentTimeMillis();
        logger.info("[{}] 开始执行方法: {}，参数: {}", layer, methodName, args);
        for(Object obj: args) {
            logger.info("{} ", obj);
        }
        Object res;
        //2.执行目标方法
        try{
            res = pjp.proceed();//ProceedingJoinPoint执行目标方法
            //由于不知道目标方法的返回值，所以我们用object来接收
        } catch (Throwable throwable) {
            logger.info("[{}] 方法: {} 执行出错，错误信息: {}",layer, methodName, throwable.getMessage());
            throw throwable;
        }


        //3.目标方法运行后，记录当前记录时间end
        long end = System.currentTimeMillis();
        long duration = end - start;
        //4.end-start就是方法运行时间
        logger.info("[{}] 方法: {} 执行完毕，耗时: {} ms，返回值: {}", layer, methodName, duration, res);
        return res;

    }

}
