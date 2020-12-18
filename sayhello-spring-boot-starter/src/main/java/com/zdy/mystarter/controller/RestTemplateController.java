package com.zdy.mystarter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO restTemplate测试
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 9:08
 * @desc
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateController{

    /**
     * 装配RestTemplate
     */
    @Autowired
    RestOperations restTemplate;

    @PostMapping("/demo1")
    public Map restTemplateDemo1(){
        Map<String ,Object> retMap=new HashMap<>();
        /**
         * todo 可以定制;
         *  1.HttpEntityRequestCallBack       处理请求
         *  2.HttpMessageConverterExtractor   处理应答
         */
        //restTemplate.execute();
        return retMap;
    }







}
