package com.zdy.mystarter.basic.handlers;

import com.alibaba.fastjson.JSON;
import com.zdy.mystarter.annotations.ResultBeanResponseBody;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义返回值处理器
 * Created by Jason on 2020/1/10.
 */
public class ResultBeanReturnValueHandler implements HandlerMethodReturnValueHandler {

    /**
     * 类似ResponseBody，
     * 例如：
     *   下面代码中-仅有添加ResultBeanResponseBody注解的method才会触发
     * @param returnType
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        System.out.println("ResultBeanReturnValueHandler...supportsReturnType.....");
        //TODO 核实当前处理的注解是否是指定注解且要求存在
        return returnType.hasMethodAnnotation(ResultBeanResponseBody.class);
    }

    /**
     * 用统一的结果封装类ResultInfo，并序列化成json
     * @param returnValue   返回值
     * @param returnType    返回类型
     * @param mavContainer  ModelAndView容器
     * @param webRequest    web请求
     * @throws Exception
     */
    @Override
    public void handleReturnValue(@Nullable Object returnValue,
                                  MethodParameter returnType,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        System.out.println("ResultBeanReturnValueHandler...handleReturnValue.....returnValue="+returnValue);
        /**
         * TODO 获取请求中的MIME类型
         */
        HttpServletRequest request=webRequest.getNativeRequest(HttpServletRequest.class);
        String contType=request.getHeader("Accept");
        if(StringUtils.isEmpty(contType)){
            contType=request.getHeader("Content-Type");
            if(StringUtils.isEmpty(contType)){
                contType=MediaType.TEXT_HTML_VALUE;
            }
        }
        /**
         * TODO  获取http响应对象,做出符合请求的MIME类型的数据
         */
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        //需要选择指定的
        response.addHeader("Content-Type", contType);
        /**
         * TODO .setRequestHandled(true)表示此函数可以处理请求，不必交给别的代码处理
         */
        mavContainer.setRequestHandled(true);
        /**
         * TODO 将接收到的响应参数转换成JSON返回
         * --以下为测试数据
         */
        response.getWriter().append(JSON.toJSONString(returnValue));
    }
}
