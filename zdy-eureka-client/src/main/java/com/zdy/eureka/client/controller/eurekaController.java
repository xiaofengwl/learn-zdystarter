package com.zdy.eureka.client.controller;

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
 * @date 2020/3/25 18:15
 * @desc
 */
@RestController
@RequestMapping("/eureka")
public class eurekaController {


    @PostMapping("/1")
    public String eureka_1(){
        return "OK";
    }


}
