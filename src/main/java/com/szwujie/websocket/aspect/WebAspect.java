package com.szwujie.websocket.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
// @Component把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>。当组件不好归类的时候，我们可以使用这个注解进行标注
public class WebAspect {

    Logger logger = LoggerFactory.getLogger(WebAspect.class);

    /**
     * 切入点
     * 匹配com.szwujie.websocket.controller包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(* com.szwujie.websocket.controller..*.*(..))")
    public void executePackage() {

    }

    /**
     * 前置通知，目标方法调用前被调用
     * @param joinPoint
     */
    @Before("executePackage()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("----- 前置通知 -----");
        Signature signature = joinPoint.getSignature();
        logger.info("返回目标方法的签名：{}", signature);
        logger.info("代理的是哪个方法：{}", signature.getName());
        Object[] objects = joinPoint.getArgs();
        logger.info("获取目标方法的参数信息：{}", Arrays.asList(objects));
    }

    /**
     * 后置最终通知，目标方法执行完执行
     */
    @After("executePackage()")
    public void afterAdvice() {
        logger.info("----- 后置最终通知 -----");
    }

    /**
     * 后置返回通知
     * 如果参数中第一个参数为joinPoint，则第二个参数为返回值信息
     * 如果参数中第一个参数不为joinPoint，则第一个参数为returning中对应的参数
     * returning 只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(value = "execution(* com.szwujie.websocket.controller..*.*(..))", returning = "keys")
    public void afterReturningAdvice(JoinPoint joinPoint, String keys) {
        logger.info("----- 后置返回通知 -----");
        logger.info("后置返回通知，返回值：{}", keys);
    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将目标方法抛出的异常传给通知方法
     * throwing 只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置通知异常，否则不执行
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "executePackage()", throwing = "exception")
    public void afterThrowingAdvice(JoinPoint joinPoint, NullPointerException exception) {
        logger.info("----- 后置异常通知 -----");
    }
    /**
     * 环绕通知
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值
     * 环绕通知第一个参数必须是 org.aspectj.lang.ProceedingJoinPoint 类型
     * @param proceedingJoinPoint
     * @return
     */
    @Around("execution(* com.szwujie.websocket.controller.AopController.testAround(..))")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        logger.info("----- 环绕通知 -----");
        logger.info("环绕通知的目标方法名：{}", proceedingJoinPoint.getSignature().getName());
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            logger.info("----- 环绕通知 end -----");
        }
        return null;
    }

}
