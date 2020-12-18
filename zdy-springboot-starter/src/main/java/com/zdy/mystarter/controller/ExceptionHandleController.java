package com.zdy.mystarter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理异常
 * Created by Jason on 2020/1/9.
 */
@RestController
@RequestMapping("/exception")
public class ExceptionHandleController {

    @RequestMapping("/ex/1")
    public String returnException(){
        System.out.println("/exception/ex/1");
        return "系统繁忙，请稍后再试！";
    }

    @RequestMapping("/ex/2")
    public String returnExceptionHoldQueryParam(@RequestParam(value="p1") String p1){
        System.out.println("/exception/ex/2收到请求参数："+p1);
        return "系统繁忙，请稍后再试！";
    }
}
