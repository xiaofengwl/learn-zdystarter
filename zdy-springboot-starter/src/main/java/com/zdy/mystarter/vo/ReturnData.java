package com.zdy.mystarter.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建一个用作响应客户端请求的应答数据对象(可以自定义)
 * Created by Jason on 2020/1/12.
 */
@Data
public class ReturnData implements Serializable{
    /**
     * 响应码值
     */
    private Integer status;
    /**
     * 响应码说明
     */
    private String message;
    /**
     * 响应数据
     */
    private Object datas;
}
