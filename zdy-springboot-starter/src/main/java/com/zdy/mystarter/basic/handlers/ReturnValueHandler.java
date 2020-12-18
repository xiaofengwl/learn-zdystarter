package com.zdy.mystarter.basic.handlers;

import com.alibaba.fastjson.JSON;
import com.zdy.mystarter.annotations.ResultBeanResponseBody;
import com.zdy.mystarter.vo.Result;
import com.zdy.mystarter.vo.ReturnData;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * TODO 包装返回值,针对有mapping注解的方法返回的json数据包装
 * 功能：
 *      封装Controller的方法应答数据，再做一次封装
 * Created by Jason on 2020/1/12.
 *
 * 1、返回ResponseBody类型的数据会由 RequestResponseBodyMethodProcessor 处理。
 * 2、此处继承了 RequestResponseBodyMethodProcessor ，直接调用 super.handleReturnValue(returnValue, returnType, mavContainer, webRequest) 去处理。
 */
@Component
public class ReturnValueHandler extends RequestResponseBodyMethodProcessor implements HandlerMethodReturnValueHandler {

    /**
     * TODO 要求构造函数需要一个和父类保持一致的，没有无参构造函数
     * @param converters
     */
    public ReturnValueHandler(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    /**
     * 只有该方法supportsReturnType()返回true，handleReturnValue()方法才会执行，否则保持原有逻辑不变。
     * @param returnType
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        System.out.println("ReturnValueHandler:supportsReturnType");
        /**
         * TODO 判断是否是当前方法是被@ResultBeanResponseBody注解修饰
         */
        Method method = returnType.getMethod();
        boolean flag=false;
        for(Annotation annotation:returnType.getMethodAnnotations()){
            if(annotation instanceof ResultBeanResponseBody ||
               annotation instanceof ResponseBody ){
                flag= true;
                break;
            }
        }
        return flag;
        /**
         * 保持原有逻辑不变：super.supportsParameter(returnType)
         * 查看该方法的源码：可以看出RequestResponseBodyMethodProcessor 这个类处理的就是有个@ResponseBody注解的类或者方法。
         */
        //return super.supportsParameter(returnType);
    }

    /**
     * 调整返回数据的处理逻辑，前提：supportsReturnType()方法返回true
     * @param returnValue
     * @param returnType
     * @param mavContainer
     * @param webRequest
     * @throws IOException
     * @throws HttpMediaTypeNotAcceptableException
     * @throws HttpMessageNotWritableException
     */
    @Override
    public void handleReturnValue(@Nullable Object returnValue,
                                  MethodParameter returnType,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
        // 此处进行值的包装
        System.out.println("ReturnValueHandler:handleReturnValue");

        //方式1：自定义应答格式  ---推荐，需要处理好异常信息，给客户端友好的提示（详情请参考自定义的处理方式：GlobalExceptionHandler.java）
        ReturnData data=new ReturnData();
        //todo 此处需要优化
        data.setStatus(HttpStatus.OK.value());
        data.setMessage("success");
        data.setDatas(StringUtils.isEmpty(returnValue)?"":returnValue);

        Result result=new Result(HttpStatus.OK.value(),
                                 "message",
                                 StringUtils.isEmpty(returnValue)?"":returnValue);

        //方式2：写死应答格式       ---不推荐
        //Map<String,Object> retMap=new HashMap<>();
        //retMap.put("code","200");
        //retMap.put("message","success");
        //retMap.put("datas",returnValue);

        //方式3：让Cotroller中的返回者去定义返回格式，这个时候可以将ec/em的复制操作前置。--不推荐

        // 【简易版】使用父类去处理，然后返回,要转换成JSON格式
        super.handleReturnValue(JSON.toJSONString(data), returnType, mavContainer, webRequest);

        //【扩展版】直接调用父类中的方法去执行，还可以处理以下请求和响应对象，效果和【简易版】一样
        //mavContainer.setRequestHandled(true);
        //ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
        //ServletServerHttpResponse outputMessage = this.createOutputMessage(webRequest);
        //super.writeWithMessageConverters(JSON.toJSONString(data), returnType, inputMessage, outputMessage);
    }
}
