package com.zdy.mystarter.basic.exceptions;


import com.zdy.mystarter.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 定制Exception适配器
 * Created by Jason on 2020/1/12.
 * 功能说明：
 *        实现该接口，注册到Spring容器中，当Controller中产生异常的时候会调用该接口来处理，注意：
 *     当返回值指定视图时会自动跳转到指定视图中去，如果返回null,会继续调用下一个异常处理器去执行。
 *     SpringBoot可以通过以下方式去注册进容器
 * 方式一：  @Bean
 *          public ApplicationHandlerExceptionResolver handlerExceptionResolver(){
 *              return new ApplicationHandlerExceptionResolver();
 *          }
 *      或者
 * 方式二： 实现WebMvcConfigurer接口中的configureHandlerExceptionResolvers方法，此种方式不常用。
 *      或者
 * 方式三： 使用注解@ControllerAdvice以及@ExceptionHandler(ApplicationException.class)注解对指定的异常做全局处理
 *         此种方式比较常用！
 *
 * TODO 本案例采用方式3，使用注解的处理方式
 * 补充说明:
 *      当捕捉到对应的异常信息后，会执行下面对应的异常处理，
 *      就不会再进入：RequestResponseBodyMethodProcessor中的handleReturnValue方法了。
 *  @RestControllerAdvice 这个注解比较有意思
 *  分析：
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 日志组件
     */
    Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * TODO [1] 处理业务异常-BusinessException
     * @param e
     * @param <T>
     * @return
     *
     */
    //todo 当捕获到该种类型的异常时，执行以下方法
    @ExceptionHandler(BusinessException.class)
    public <T> Result<T> handleBusinessException(BusinessException e){
        //记录日志，打印信息
        logger.error("business exception code={},message={}",e.getCode(),e.getMessage());
        return new Result<T>(e.getCode(),e.getMessage());
    }

    //....可以自定义n多个异常类，然后和BusinessException一样的方式添加进来



}
