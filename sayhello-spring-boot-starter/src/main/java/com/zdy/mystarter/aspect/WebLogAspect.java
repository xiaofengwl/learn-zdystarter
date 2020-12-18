package com.zdy.mystarter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TODO 日志打印处理切面
 * <pre>
 *     测试后的执行顺序为，
 *     @Around
 *     @Before
 *     @After
 *     如果在Controller的请求方法中执行异常，则会进入@AfterThrowing
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 9:52
 * @desc
 */
@Aspect
@Component
public class WebLogAspect {
    /**
     * 准备
     */
    private final static Logger logger= LoggerFactory.getLogger(WebLogAspect.class);
    private final static  String CONTROLLER="";
    private final static  String CONTROLLER_PARAMS="RequestParams";
    private final static  String CONTROLLER_RESP="ResponseData";

    /**
     * 定义一个切入点
     */
    @Pointcut("@annotation(com.zdy.mystarter.annotations.WebLog)")
    public void webLog(){}

    /**
     * 处理之前
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("=============================start============================");
    }

    /**
     * 处理中
     * @param pjp
     * @return
     */
    @Around("webLog()")
    public Object invokeWebLog(ProceedingJoinPoint pjp)throws Throwable{
        logger.info("-----------invokeWeblog-----------");
        return pjp.proceed();
    }

    /**
     * 处理之后
     */
    @After("webLog()")
    public void doAfter(){
        logger.info("=============================after============================");
    }

    /**
     * 处理异常情况
     * @param e
     */
    @AfterThrowing(pointcut = "webLog()",throwing = "e")
    public void afterExecuteThrowing(Exception e){
        logger.info("---啊哦，出错了！----");
    }
}
