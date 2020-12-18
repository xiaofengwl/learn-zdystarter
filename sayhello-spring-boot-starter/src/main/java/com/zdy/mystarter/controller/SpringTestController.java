package com.zdy.mystarter.controller;

import com.zdy.mystarter.basic.awares.SpringContextHolder;
import com.zdy.mystarter.basic.init.ZDYDataVo;
import com.zdy.mystarter.vo.ResponseHolderData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * TODO 专门为测试Spring源码功能创建
 * <pre>
 *     顺带测试Swagger-UI和如何自动发布一个接口
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 11:20
 * @desc
 */
@RestController
@RequestMapping("/spring")
@Api(tags = {"Spring测试"})
public class SpringTestController {

    /**
     * 注入环境对象
     */
    @Autowired
    private Environment env;


    /**
     * 测试Spring外部手动向IOC容器中注入Bean后，在服务启动之后能够获取到
     * @return
     */
    @PostMapping("/autowire/1")
    @ApiOperation(value = "测试-1-手动向IOC容器中注入Bean",notes = "描述：")
    public Map test_autowireCapableBeanFactory_1(String p1){
        System.out.println("===============准备获取bean2===============");
        try{
            Object objBean= SpringContextHolder.getBean(ZDYDataVo.class);
            System.out.println("===============获取刚注入的Bean:"+objBean+"===============");
         }catch (Exception e){
            System.out.println("===============获取Bean失败===============");
        }
        return ResponseHolderData.getDefaultResponseData(Map.class);
    }
}
