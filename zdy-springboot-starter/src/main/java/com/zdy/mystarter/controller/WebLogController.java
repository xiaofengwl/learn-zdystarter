package com.zdy.mystarter.controller;

import com.zdy.mystarter.annotations.WebLog;
import com.zdy.mystarter.api.AnnotationAopFeignApi;
import com.zdy.mystarter.basic.awares.SpringContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 测试日志拦截
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 10:48
 * @desc
 */
@RestController
@RequestMapping("/log")
public class WebLogController {

    @Autowired
    private AnnotationAopFeignApi annotationAopFeignApi;

    @PostMapping("/1")
    public Map webLogDemo1(){
        //annotationAopFeignApi.request();
        Map<String ,Object> retMap=new HashMap<>();
        retMap.put("ec","200");
        retMap.put("em","OK");

        /**
         * 成功修改restTemplate对象
         */
        RestTemplate restTemplate =getRestTemplate();
        restTemplate.setInterceptors(new ArrayList<>());
        HttpComponentsClientHttpRequestFactory factory= (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
        factory.setReadTimeout(100000);
        restTemplate.setRequestFactory(factory);

        RestTemplate restTemplate2 =getRestTemplate();
        System.out.println(restTemplate2);




        return retMap;
    }

    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate= SpringContextHolder.getBean("fiegnRestTemplate");
        return restTemplate;
    }

}
