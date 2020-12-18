package com.zdy.mystarter.controller;

import com.zdy.mystarter.api.ZdySpringCloudApi;
import com.zdy.mystarter.basic.hystrix.ZDYHystrixTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TODO 测试Hystrix熔断降级
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/31 18:07
 * @desc
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {
    Logger logger= LoggerFactory.getLogger(HystrixController.class);

    /**
     * 注入restTemplate
     */
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ZDYHystrixTest zdyHystrixTest;

    @Autowired
    ZdySpringCloudApi zdySpringCloudApi;
    /**
     * 同步测试
     * @return
     */
    @PostMapping("/1")
    public String hystrixDemo1() {
        String retStr="default";
        try {
            retStr=zdyHystrixTest.hystrixResponse("sweet");
        } catch (Exception e) {
            retStr="fail";
        }
        return retStr;
    }

    /**
     * todo 异步测试
     * @return
     */
    @PostMapping("/2")
    public String hystrixDemo2() {
        String retStr="default";
        try {
            System.out.println("当前时间戳-Start："+System.currentTimeMillis());
            Future<String> result=zdyHystrixTest.asynchystrixResponse("sweet");
            retStr=result.get(1000, TimeUnit.MILLISECONDS);
            System.out.println("当前时间戳-End："+System.currentTimeMillis());
        }catch(TimeoutException e){
            retStr="fail：TimeoutException";
        }catch (Exception e) {
            retStr="fail：Exception";
        }
        return retStr;
    }

    @PostMapping("/3")
    public String hystrixDemo3() {
        String result=zdySpringCloudApi.helloConsul();
        return result;
    }
}
