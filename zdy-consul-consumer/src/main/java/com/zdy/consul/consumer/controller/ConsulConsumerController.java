package com.zdy.consul.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/26 9:10
 * @desc
 */
@RestController
@RequestMapping("/consul/consumer")
public class ConsulConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取所有服务
     * @return
     */
    @RequestMapping("/servies")
    public Object getAllConsulServices(){
        List<ServiceInstance> instances=discoveryClient.getInstances("zdy-springcloud");
        return instances;
    }

    /**
     * 轮训获取服务中的一个
     * @return
     */
    @RequestMapping("discover")
    public String discover(){
        Object discover=loadBalancerClient.choose("zdy-springcloud");
        return (StringUtils.isEmpty(discover))?"暂无发现":discover.toString();
    }

}
