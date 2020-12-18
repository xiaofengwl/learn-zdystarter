package com.zdy.mystarter.controller;

import com.zdy.mystarter.annotations.WebLog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/1")
    @WebLog(description = "日志组件测试")
    public Map webLogDemo1(){
        Map<String ,Object> retMap=new HashMap<>();
        retMap.put("ec","200");
        retMap.put("em","OK");
        return retMap;
    }

}
