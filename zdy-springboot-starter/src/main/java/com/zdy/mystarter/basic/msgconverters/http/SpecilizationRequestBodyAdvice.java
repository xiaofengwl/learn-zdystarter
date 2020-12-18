package com.zdy.mystarter.basic.msgconverters.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * TODO 定制一个RequestBodyAdvice
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 11:48
 * @desc
 */
@ControllerAdvice
public class SpecilizationRequestBodyAdvice implements RequestBodyAdvice{

    private static Logger logger= LoggerFactory.getLogger(SpecilizationRequestBodyAdvice.class);
    /**
     * todo 后面的方法都会先执行supports，
     * 被RequestResponseBodyAdviceChain对象调用beforeBodyRead()和afterBodyRead()中有supports()
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        logger.info("SpecilizationRequestBodyAdvice-supports");
        return false;
    }

    /**
     * todo 前置通知处理  请求对象HttpInputMessage中有请求体BODY
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        logger.info("SpecilizationRequestBodyAdvice-beforeBodyRead");
        return inputMessage;
    }

    /**
     * todo 后置通知处理 请求对象HttpInputMessage中有请求体BODY
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        logger.info("SpecilizationRequestBodyAdvice-afterBodyRead");
        return body;
    }

    /**
     * todo 请求对象HttpInputMessage中没有请求体BODY时执行
     */
    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        logger.info("SpecilizationRequestBodyAdvice-handleEmptyBody");
        return body;
    }
}
