package com.zdy.mystarter.basic.resttemplates;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * TODO 定制一个响应信息提取类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 10:20
 * @desc
 */
public class RestHttpMessageConverterExtractor<T> implements ResponseExtractor<T> {

    /**
     * todo 需要的资源
     */
    private RestTemplate restTemplate;
    private List<HttpMessageConverter<?>> messageConverters;
    private HttpMessageConverterExtractor<T> delegate;
    private Type responseType;

    @Nullable
    private final Class<T> responseClass;


    /**
     * todo 构造器-装配资源
     * @param restOperations  restTemplate
     * @param responseType    响应类型
     */
    public RestHttpMessageConverterExtractor(RestOperations restOperations, Type responseType){
        this.restTemplate=(RestTemplate)restOperations;
        this.messageConverters=this.restTemplate.getMessageConverters();
        this.responseType=responseType;
        this.responseClass=(responseType instanceof Class ? (Class<T>)responseType:null);
        if(null!=responseType && Void.class!=responseType){
            //可以直接转接其他提取器去执行核心逻辑
            this.delegate=new HttpMessageConverterExtractor<T>(responseType,this.messageConverters);
        }else{
            this.delegate=null;
        }
    }

    /**
     * 提取逻辑
     * @param clientHttpResponse
     * @return
     * @throws IOException
     */
    @Nullable
    @Override
    public T extractData(ClientHttpResponse clientHttpResponse) throws IOException {
       T body=null;
       RestMessageBodyClientHttpResponseWrapper responseWrapper=new RestMessageBodyClientHttpResponseWrapper(clientHttpResponse);
       if(!responseWrapper.hasMessageBody()||responseWrapper.hasEmptyMessageBody()){
           return null;
       }
       //拿到应答字符串
       String bodyStr= StreamUtils.copyToString(responseWrapper.getBody(), Charset.forName("UTF-8"));
       //处理应答字符串
       body= (T)JSON.parse(bodyStr);

        return body;
    }
}
