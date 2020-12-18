package com.zdy.mystarter.basic.resolvers;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.zdy.mystarter.annotations.ParameterAnntation;
import com.zdy.mystarter.basic.handlers.MessageAnalysisTransformHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * 自定义一个指定参数注解的参数解析器
 * Created by Jason on 2020/1/10.
 */
public class ParameterResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断此参数是否能够使用该解析器。
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ParameterAnntation.class);
    }

    /**
     * 框架会将每一个MethodParameter传入supportsParameter测试是否能够被处理，如果能够，就使用resolveArgument处理
     * @param methodParameter           方法上的参数
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  @Nullable ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {

        //获取参数上的注解
        Annotation[] annotations=methodParameter.getParameterAnnotations();
        //逐个处理
        for (Annotation annotation:annotations) {
            if(annotation instanceof ParameterAnntation){
                return dealParameterAnnotaion((ParameterAnntation) annotation,methodParameter,modelAndViewContainer,nativeWebRequest,webDataBinderFactory,webDataBinderFactory);
            }
        }
        return null;
    }

    /**
     * [1]处理ParameterAnnotation注解的方法
     * 说明：
     *     如果需要封装单个参数和实体Bean的映射关系，可以在此做匹配处理。
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @param webDataBinderFactory1
     * @return
     */
    private Object dealParameterAnnotaion(ParameterAnntation annotation,
                                          MethodParameter methodParameter,
                                          ModelAndViewContainer modelAndViewContainer,
                                          NativeWebRequest nativeWebRequest,
                                          WebDataBinderFactory webDataBinderFactory,
                                          WebDataBinderFactory webDataBinderFactory1) throws IOException {
        //获取request
        HttpServletRequest request=nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        //获取ApplicationContext
        ServletContext servletContext=request.getServletContext();
        ApplicationContext applicationContext= WebApplicationContextUtils.getWebApplicationContext(servletContext);
        //说明：可以通过applicationContext.getBean("beanName")的方式获取Spring


        //uri中的请求参数
        String str = request.getQueryString();
        //请求中的body信息
        BufferedReader bufferedReader = request.getReader();
        String bodyStr = IOUtils.read(bufferedReader);  //调用alibaba.dubbo下的包
        System.out.println("uri-parameter = "+str);
        System.out.println("post-body-parameter = " + bodyStr );
        /**
         * todo 解析请求参数到实参模版中，采用注解反射处理
         */
        //如果没有内容，直接返回null
        if (StringUtils.isEmpty(bodyStr)){
            return null;
        }
        //有内容，继续解析
        try {
            //开始解析
            // 注意：此处返回的数据类型必须和实参数据类型保持一致，否则无法被接收
            return MessageAnalysisTransformHandler.analysisParameter(annotation,
                                                                methodParameter,
                                                                request,
                                                                annotation.type().newInstance(),
                                                                bodyStr);
        } catch (InstantiationException|IllegalAccessException e) {
            e.printStackTrace();
            //注意：如果解析出出现意外，则日志打印报警
            return null;
        }
    }


}
