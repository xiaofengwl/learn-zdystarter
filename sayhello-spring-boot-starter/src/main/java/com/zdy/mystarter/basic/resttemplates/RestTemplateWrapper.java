package com.zdy.mystarter.basic.resttemplates;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 服务调用
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 9:29
 * @desc
 */
@Component
public class RestTemplateWrapper {

    //日志
    private Logger logger= LoggerFactory.getLogger(RestTemplateWrapper.class);

    /**
     * 装配RestTemplate
     */
    @Autowired
    RestOperations restTemplate;

    /**
     * 发送请求
     * @param serviceId  服务名称Id
     * @param uri        uri
     * @param httpMethod 请求方式：post/get....
     * @param protocol   协议类型：http/https
     * @param req        请求数据
     * @param header     请求头部
     * @return
     */
    @HystrixCommand(fallbackMethod = "defaultResponse")         //熔断机制
    public Map<String,Object> postRequest(String serviceId,
                                          String uri,
                                          String httpMethod,
                                          String protocol,
                                          Map req,
                                          HttpHeaders header)throws Exception{
        //
        Map<String ,Object> res=null;
        //请求设置
        try{
            if(null==header){
                header=new HttpHeaders();
            }
            if(null==header.getContentType()){
                header.setContentType(MediaType.APPLICATION_JSON);
            }
            if(null==header.getAccept()){
                List<MediaType> mediaTypes=new ArrayList<>();
                mediaTypes.add(MediaType.APPLICATION_JSON);
                header.setAccept(mediaTypes);
            }
            HttpEntity<?> requestEntity=new HttpEntity<Map<String,Object>>(req,header);
            res=restTemplate.execute(
                                    (null==protocol?"http":protocol)+"://"+serviceId+"/"+uri,
                                    HttpMethod.valueOf(null==httpMethod?"POST":httpMethod),
                                    new RestHttpEntityRequestCallBack(restTemplate,requestEntity),
                                    new RestHttpMessageConverterExtractor<Map>(restTemplate,Map.class)
                        );
        }catch (Exception e){
            logger.error("调用发生异常",e);
            throw new Exception(e);
        }
        return res;
    }

    /**
     * 降级处理
     * 默认-响应处理方法
     * @param serviceId
     * @param uri
     * @param httpMethod
     * @param protocol
     * @param req
     * @param header
     * @return
     * @throws Exception
     */
    public Map<String,Object> defaultResponse(String serviceId,
                                              String uri,
                                              String httpMethod,
                                              String protocol,
                                              Map req,
                                              HttpHeaders header)throws Exception{
        Map<String,Object> res=new HashMap<>();
        res.put("ec","99999");
        res.put("em","服务调用超时");
        return res;
    }



}
