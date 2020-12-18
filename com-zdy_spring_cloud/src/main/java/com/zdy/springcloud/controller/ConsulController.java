package com.zdy.springcloud.controller;

import com.zdy.springcloud.api.FeignClientApi;
import com.zdy.springcloud.entity.RespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/26 8:59
 * @desc
 */
@RestController
@RequestMapping("/consul/producer")
public class ConsulController {

    /**
     * 装配RestTemplate
     */
    @Autowired
    RestOperations restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;



    @RequestMapping("/hello")
    public String helloConsul(){
        //请求准备
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes=new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        header.setAccept(mediaTypes);

        //设置请求对象
        HttpEntity<?> requestEntity=new HttpEntity<>(void.class,header);

        /**
         * 可以指定
         *  （1）请求入参的媒体类型，参数类型
         *  （2）响应参数类型的指定
         */
        String result=restTemplate.postForObject(
                "http://zdy-common-project:8083/consul/producer/hello",
                requestEntity,
                String.class
        );
        return result;
    }
}
