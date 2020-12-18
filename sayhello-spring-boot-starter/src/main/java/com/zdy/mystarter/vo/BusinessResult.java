package com.zdy.mystarter.vo;

import com.alibaba.fastjson.JSON;

/**
 * 定义业务异常结果类，包括异常错误码和异常提示信息
 *
 * Created by Jason on 2020/1/13.
 */
public class BusinessResult {
    /**
     * 异常错误码
     */
    private Object code;
    /**
     * 异常提示信息
     */
    private String message;

    public BusinessResult(Object code,String message){
        this.code=code;
        this.message=message;
    }

    /**
     * 创建业务返回定义
     * @param code
     * @param message
     * @return
     */
    public static BusinessResult create(Object code,String message){
        return new BusinessResult(code,message);
    }


    public Object getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
