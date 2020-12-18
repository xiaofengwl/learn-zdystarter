package com.zdy.mystarter.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @date 2020/9/24 16:47
 * @desc
 */
@RestController
@RequestMapping("/gateway")
@Api(tags = {"zuul测试"})
public class ZuulMainController {

    @PostMapping("/t/1")
    public void test(){
        System.out.println("class:com.zdy.mystarter.controller.ZuulController");
        System.out.println("url:/gateway/t/1");
        System.out.println("method:test()");

    }
}