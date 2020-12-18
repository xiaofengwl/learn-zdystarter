package com.zdy.mystarter.basic.resttemplates;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

/**
 * TODO 定制请求头请求体处理逻辑
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 9:44
 * @desc
 */
public class RestHttpEntityRequestCallBack implements RequestCallback{

    /**
     * todo 需要的资源
     */
    private RestTemplate restTemplate;
    private List<HttpMessageConverter<?>> messageConverters;
    private HttpEntity requestEntity;

    /**
     * todo 构造器-装配资源
     * @param restOperations  restTemplate
     * @param requestEntity   请求实体对象
     */
    public RestHttpEntityRequestCallBack(RestOperations restOperations,HttpEntity requestEntity){
        this.restTemplate=(RestTemplate)restOperations;
        this.messageConverters=this.restTemplate.getMessageConverters();
        this.requestEntity=requestEntity;
    }

    /**
     * todo 核心处理逻辑
     * 处理方式和restTemplate保持一致
     * @param clientHttpRequest
     * @throws IOException
     */
    @Override
    public void doWithRequest(ClientHttpRequest clientHttpRequest) throws IOException {

        if(!requestEntity.hasBody()){//请求实体中不含请求体
            //组装请求头
            HttpHeaders httpHeaders=clientHttpRequest.getHeaders();
            HttpHeaders requestHeader=requestEntity.getHeaders();
            if(!requestHeader.isEmpty()){
                httpHeaders.putAll(requestHeader);
            }
            if (httpHeaders.getContentLength()<0L){
                httpHeaders.setContentLength(0L);
            }
        }else{//请求实体中存在请求体
            Object requestBody=requestEntity.getBody();
            Iterator iterator=messageConverters.iterator();
            while(iterator.hasNext()){
                Class<?> requestBodyClass=requestBody.getClass();
                Type requestBodyType =requestEntity instanceof RequestEntity?((RequestEntity)requestEntity).getType():requestBodyClass;
                HttpHeaders requestHeaderx=requestEntity.getHeaders();
                MediaType requestContentType=requestHeaderx.getContentType();
                HttpMessageConverter<Object> messageConverter=(HttpMessageConverter)iterator.next();
                //判断消息转换器
                if(messageConverter instanceof GenericHttpMessageConverter){
                    GenericHttpMessageConverter<Object> genericHttpMessageConverter=(GenericHttpMessageConverter)messageConverter;
                    if(genericHttpMessageConverter.canWrite((Type)requestBodyType,requestBodyClass,requestContentType)){
                        if(!requestHeaderx.isEmpty()){
                            clientHttpRequest.getHeaders().putAll(requestHeaderx);
                        }
                        genericHttpMessageConverter.write(requestBody,(Type)requestBodyType,requestContentType,clientHttpRequest);
                        return;
                    }
                }else if(messageConverter.canWrite(requestBodyClass,requestContentType)){
                    if(!requestHeaderx.isEmpty()){
                        clientHttpRequest.getHeaders().putAll(requestHeaderx);
                    }
                    messageConverter.write(requestBody,requestContentType,clientHttpRequest);
                    return;
                }
            }
        }
    }
}
