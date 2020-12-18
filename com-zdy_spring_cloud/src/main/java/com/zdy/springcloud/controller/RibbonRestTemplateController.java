package com.zdy.springcloud.controller;

import com.zdy.springcloud.entity.RequestVo;
import com.zdy.springcloud.entity.RespVo;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/1")
    public String getData(){
        return "getData()-测试Ribbon和RestTemplate结合";
    }

    @GetMapping("/1/{path}")
    public String getData2(@PathVariable("path")String path){
        return "getData2()-测试Ribbon和RestTemplate结合:path="+path;
    }

    @PostMapping("/2")
    public RespVo getData3(@RequestBody RequestVo requestVo){

        System.out.println(requestVo.toString());

        RespVo vo=new RespVo();
        vo.setCode("200");
        vo.setMsg("getData3()-测试Ribbon和RestTemplate结合");
        return vo;
    }










}
