package com.zdy.mystarter.basic.msgconverters.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * TODO 定制一个ResponseBodyAdvice
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
public class SpecilizationResponseBodyAdvice implements ResponseBodyAdvice{

    private static Logger logger= LoggerFactory.getLogger(SpecilizationResponseBodyAdvice.class);

    /**
     * todo beforeBodyWrite执行时会调用supports()
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return false;
    }

    /**
     * todo javaPojo-->httpResponseMessage的前置处理
     */
    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }
}
