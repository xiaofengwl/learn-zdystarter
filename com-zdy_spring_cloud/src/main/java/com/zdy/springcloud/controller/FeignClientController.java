package com.zdy.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zdy.springcloud.api.FeignClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/5/2 11:10
 * @desc
 */
@RestController
@RequestMapping("/fiegn")
public class FeignClientController {
    /**
     * 应该是自动生成了代理类
     */
    @Autowired
    FeignClientApi feignClientApi;

    @RequestMapping("/1/{type}")
    public String feignClient_1(@PathVariable("type") int type){
        String result="";
        switch (type){
            case 1:result=feignClientApi.feignClient_1();break;
            case 2:result=feignClientApi.feignClient_2();break;
            case 3:result=feignClientApi.feignClient_3();break;
            case 4:result=feignClientApi.feignClient_4();break;
        }
        return result;
    }

    /**
     * 测试-是生效的
     * @return
     */
    @HystrixCommand(fallbackMethod = "feignClient_2FallBack")
    @RequestMapping("/2")
    public String feignClient_2(){
        //int i=1/0;
        return "feignClient_2";
    }
    public String feignClient_2FallBack(){
        return "feignClient_2FallBack";
    }
}
