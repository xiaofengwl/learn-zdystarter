package com.zdy.mystarter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/5/2 11:01
 * @desc
 */
@RestController
@RequestMapping("/feign/timeout")
public class FeignClentController {

    @RequestMapping("/1")
    public String fiegnClient_1(){
        System.out.println("开始做future任务");
        long start = System.currentTimeMillis();
        try {
            //时间必须小于hystrix的超时配置，否则线程会被中断掉
            int randomInt=new Random().nextInt(10000);
            System.out.println("准备休息："+randomInt);
            Thread.sleep(randomInt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);//HystrixRuntimeException
        }
        long end = System.currentTimeMillis();
        System.out.println("future任务完成，耗时：" + (end - start) + "毫秒");
        return "zdy-common-project...FeignClentController...fiegnClient";
    }
}
