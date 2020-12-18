package com.zdy.mystarter.basic.handlers;

import com.zdy.mystarter.annotations.ResultBeanResponseBody;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义一个方法返回值处理器的静态代理类
 * Created by Jason on 2020/1/12.
 * 说明：
 *    替换SpringBoot中HandlerMethodReturnValueHandler的默认实现类，需要在Spring中找到合适的切面，
 *    InitializingBean在执行完所有属性设置方法(即setXxx)将被自动调用，正好满足我们的需求，
 *    在InitializingBean中编辑我们的代码即可，
 */
public class ProxyHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    /**
     * TODO 请求响应Boody方法处理器
     */
    private RequestResponseBodyMethodProcessor target;
    //TODO 构造器
    public ProxyHandlerMethodReturnValueHandler(RequestResponseBodyMethodProcessor target){
        this.target = target;
    }

    /**
     * TODO 判断是否满足要求
     */
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        System.out.println("ProxyHandlerMethodReturnValueHandler:supportsReturnType");
        //TODO 同时兼顾自定义的注解和ReponseBody注解
        return methodParameter.hasParameterAnnotation(ResultBeanResponseBody.class)||
               methodParameter.hasParameterAnnotation(ResponseBody.class);
    }

    /**
     * TODO 处理逻辑
     */
    @Override
    public void handleReturnValue(@Nullable Object retValue,
                                  MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest) throws Exception {
        System.out.println("ProxyHandlerMethodReturnValueHandler:handleReturnValue");
        //
        if(methodParameter.hasParameterAnnotation(ResultBeanResponseBody.class)){
            //如果Controller中使用了我的自定义注解，那么对返回值进行封装
            Map<String,Object> res = new HashMap<>();
            res.put("code","1");
            res.put("data",retValue);
            target.handleReturnValue(res,methodParameter,modelAndViewContainer,nativeWebRequest);
        } else {
            target.handleReturnValue(retValue,methodParameter,modelAndViewContainer,nativeWebRequest);
        }
    }

    /**
     * TODO
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        return target.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }
}
