package com.zdy.mystarter.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 处理结果类，包含code（相应码），message(响应消息)，data(返回处理数据)等信息
 * 返回格式如下：
 *  {
 *      code:200,
 *      timestamp:151660954222525,
 *      requestId:98798127121,
 *      serverIp:127.0.0.1,
 *      message:"operate successfully",
 *      data:{
 *          结果
 *      }
 *  }
 *  或者
 *  {
 *      code:200,
 *      timestamp:151660954222525,
 *      requestId:98798127121,
 *      serverIp:127.0.0.1,
 *      message:"operate successfully",
 *      data:[
 *          结果集
 *      ]
 *  }
 * Created by Jason on 2020/1/13.
 */
@Data
public class Result<T> {

    @JSONField(ordinal = 1)
    private Object code="00000";

    @JSONField(ordinal = 2)
    private String message="处理成功";

    @JSONField(ordinal = 3)
    private Long timestamp;

    @JSONField(ordinal = 4)
    private String requestId;

    @JSONField(ordinal = 5)
    private String serverIp;

    @JSONField(ordinal = 6)
    private Long handleTime;

    @JSONField(ordinal = 7)
    private T data;

    public Result(){

    }
    /**
     * 定义构造方法，默认返回状态码，提示信息，空的内容体
     * @param result
     */
    public Result(BusinessResult result){
        this.code=result.getCode();
        this.message=result.getMessage();
        this.requestId="***request&1";
        this.serverIp="127.0.0.1";
        this.timestamp=System.currentTimeMillis();
    }

    /**
     * 定义构造方法，默认返回状态码，提示信息，空的内容体
     * @param code
     * @param message
     */
    public Result(Object code,String message){
        this.code=code;
        this.message=message;
        this.requestId="***request&1";
        this.serverIp="127.0.0.1";
        this.timestamp=System.currentTimeMillis();
    }

    /**
     * 定义构造方法，默认返回状态码，提示信息，内容体
     * @param message
     */
    public Result(String message){
        this(BusinessResult.create(601L,"系统异常，请稍后重试。"));
    }

    /**
     * 定义构造方法，默认返回状态码，提示信息，内容体
     * @param code
     * @param message
     * @param data
     */
    public Result(Object code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
        this.requestId="***request&1";
        this.serverIp="127.0.0.1";
        this.timestamp=System.currentTimeMillis();
    }

    /**
     * 返回成功标识，未带任何信息
     * @param <T>
     * @return
     */
    public static <T> Result<T> OK(){
        return new Result<T>(BusinessResult.create(200L,"操作成功"));
    }

    /**
     * 返回成功标识，带信息
     * @param <T>
     * @return
     */
    public static <T> Result<T> OK(T t){
        Result result= new Result<T>(BusinessResult.create(200L,"操作成功"));
        result.setData(t);
        return result;
    }


    /**
     * 返回成失败标识，未带任何信息
     * @param <T>
     * @return
     */
    public static <T> Result<T> FAIL(){
        return new Result<T>(BusinessResult.create(601L,"系统异常，请稍后重试。"));
    }

    /**
     * 返回失败标识，带信息
     * @param <T>
     * @return
     */
    public static <T> Result<T> FAIL(T t){
        Result result= new Result<T>(BusinessResult.create(601L,"系统异常，请稍后重试。"));
        result.setData(t);
        return result;
    }

    /**
     * 参数缺失，带信息
     * @param <T>
     * @return
     */
    public static <T> Result<T> PARAM_ERROR(T t){
        Result result= new Result<T>(BusinessResult.create(601L,"参数错误"));
        result.setData(t);
        return result;
    }

    /**
     * 改变对象原来的code和message
     * @param code
     * @param message
     * @return
     */
    public Result<T> reset(Object code,String message){
        this.code=code;
        this.message=message;
        return this;
    }

    /**
     * 改变对象原来的message和data
     * @param message
     * @param data
     * @return
     */
    public Result<T> reset(String message,T data){
        this.message=message;
        this.data=data;
        return this;
    }
    /**
     * 改变对象原来的code和message,data
     * @param code
     * @param message
     * @return
     */
    public Result<T> reset(Object code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
        return this;
    }

    /**
     * 判断是否成功状态
     * @return
     */
    public boolean isOk(){
        return new BusinessResult("200L","操作成功").getCode().equals(this.getCode().toString());
    }

    /**
     * todo 重写toString()
     * @return
     */
    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }


}
