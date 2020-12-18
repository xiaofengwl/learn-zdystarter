package com.zdy.mystarter.api;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/5/6 10:28
 * @desc
 */

import com.zdy.mystarter.api.fallback.AnnotationAopFeignApiFallBack;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "zdy-springcloudx",
        path = "/consul/producer",
        fallback = AnnotationAopFeignApiFallBack.class
)
public interface AnnotationAopFeignApi {

    //@WebLog(description = "日志组件测试")
    default void request(){

        System.out.println("AnnotationAopFeignApi....123.....");
    }

}
