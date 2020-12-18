package com.zdy.mystarter.basic.exceptions;

import com.zdy.mystarter.vo.BusinessResult;

import java.text.MessageFormat;

/**
 * 业务处理异常类，定义常见业务异常
 * 格式：
 *    throw new BusinessException(601,"系统处理异常，请稍后重试");
 *
 * Created by Jason on 2020/1/13.
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID=1L;

    protected Object code;

    public BusinessException(Object code,String message){
        super(message);
        this.code=code;
    }

    public BusinessException(BusinessResult result){
        super(result.getMessage());
        this.code=result.getCode();
    }

    /**
     * 格式化
     * @param result
     * @param params
     * @return
     */
    public BusinessException format(BusinessResult result,String... params){
       String mes=(params==null)?result.getMessage(): MessageFormat.format(result.getMessage(),params);
       this.code=result.getCode();
       return new  BusinessException(code,mes);
    }

    /**
     * TODO 不会收集线程的整个异常栈信息，提升性能10倍左右
     * @return
     */
    @Override
    public Throwable fillInStackTrace(){
        return this;
    }

    /**
     * 获取错误码
     * @return
     */
    public Object getCode() {
        return code;
    }
}
