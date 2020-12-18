package com.zdy.mystarter.controller;

import com.zdy.mystarter.vo.entitiy.RequestVo;
import com.zdy.mystarter.vo.entitiy.RespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 测试Ribbon和RestTemplate结合
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/27 14:41
 * @desc
 */
@RestController
@RequestMapping("/ribbon")
public class RibbonRestTemplateController {

    /**
     * 注入restTemplate
     */
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    /**
     * 调用方式-1-restTemplate-get方式-无参
     * @return
     */
    @GetMapping("/1")
    public String getForObject(){
        String result= restTemplate.getForObject(
                "http://zdy-springcloud:8081/ribbon/1",
                String.class
        );
        return result;
    }

    /**
     * 调用方式-2-restTemplate-get方式-有参
     * @param path
     * @return
     */
    @GetMapping("/1/{path}")
    public String getForObject(@PathVariable("path")String path){
        String result= restTemplate.getForObject(
                "http://zdy-springcloud:8081/ribbon/1/{path}",
                String.class,
                "深圳市"
        );
        return result;
    }

    /**
     * 调用方式-3-restTemplate-post方式-有参
     * @param
     * @return
     */
    @PostMapping("/2")
    public String postForObject(){
        RequestVo requestVo=new RequestVo();
        requestVo.setName("xfwl");
        requestVo.setAddress("深圳市");

        //请求准备
        HttpHeaders header=new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            List<MediaType> mediaTypes=new ArrayList<>();
            mediaTypes.add(MediaType.APPLICATION_JSON);
        header.setAccept(mediaTypes);

        //设置请求对象
        HttpEntity<?> requestEntity=new HttpEntity<>(requestVo,header);

        /**
         * 可以指定
         *  （1）请求入参的媒体类型，参数类型
         *  （2）响应参数类型的指定
         */
        RespVo result=restTemplate.postForObject(
                "http://zdy-springcloud:8081/ribbon/2",
                requestEntity,
                RespVo.class
        );
        System.out.println(result.toString());
        return "OK";
    }


    /**
     * 调用方式-4-restTemplate-post方式-有参--在restTemplate使用了@LoadBalanced做负载
     * @param
     * @return
     */
    @PostMapping("/3")
    public String postForObject2(){
        RequestVo requestVo=new RequestVo();
        requestVo.setName("xfwl");
        requestVo.setAddress("深圳市");

        //请求准备
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes=new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        header.setAccept(mediaTypes);

        //设置请求对象
        HttpEntity<?> requestEntity=new HttpEntity<>(requestVo,header);

        //可以通过loadBalancerClient从注册中心获取指定服务信息
        ServiceInstance serviceInstance = loadBalancerClient.choose("zdy-springcloud");
        String url = String.format("http://%s:%s",serviceInstance.getServiceId(),serviceInstance.getPort()) + "/ribbon/2";
        /**
         * 可以指定
         *  （1）请求入参的媒体类型，参数类型
         *  （2）响应参数类型的指定
         */
        RespVo result=null;
        try{
            result=restTemplate.postForObject(url,
                    requestEntity,
                    RespVo.class
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(result.toString());
        return "OK";
    }






}
