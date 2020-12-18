package com.zdy.mystarter.basic.getway.in.zuul.entity;

import lombok.Data;

/**
 * TODO 路由实体
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/24 16:58
 * @desc
 */
@Data
public class Router {

    /**
     * 业务代码
     */
    private String serviceCode;

    /**
     * 场景号
     */
    private String serviceScene;

    /**
     * 目标服务名称
     */
    private String targetService;

    /**
     * 目标服务URL
     */
    private String targetUrl;

    /**
     * 是否有效
     */
    private String isActive;

    /**
     * 描述
     */
    private String description;


}
