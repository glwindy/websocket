package com.szwujie.websocket.aspect;

import com.szwujie.websocket.utils.validate.WebDesc;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class WebAspectAnno {

    private Logger logger = LoggerFactory.getLogger(WebAspectAnno.class);

    /**
     * 切入点
     * 用注解
     */
    @Pointcut("@annotation(com.szwujie.websocket.utils.validate.WebDesc)")
    public void executeAnnotation(){
    }

    @Before("executeAnnotation()")
    public void beforeAdviceAnnotation(){
        logger.info("- - - - - 前置通知 annotation - - - - -");
    }

    @Around("@annotation(webDesc)")
    public Object aroundAnnotation(ProceedingJoinPoint proceedingJoinPoint, WebDesc webDesc) {
        logger.info("- - - - - 环绕通知 annotation - - - -");
        //获取注解里的值
        logger.info("注解的值: {}", webDesc.describe());
        try {//obj之前可以写目标方法执行前的逻辑
            Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
            logger.info("- - - - - 环绕通知 annotation end - - - -");
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
