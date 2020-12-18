package com.zdy.mystarter.sensitive.exception;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:56
 * @desc
 */
public class SnsitiveRuntimeException extends RuntimeException{

    private static final long serialVersionUID = -7034897190745766939L;

    public SnsitiveRuntimeException(){}

    public SnsitiveRuntimeException(String message){
        super(message);
    }

    public SnsitiveRuntimeException(String message,Throwable e){
        super(message,e);
    }

    public SnsitiveRuntimeException(Throwable e){
        super(e);
    }
    public SnsitiveRuntimeException(String message,Throwable e,boolean enableSupperssion,boolean writableStackTrace){
        super(message,e,enableSupperssion,writableStackTrace);
    }


}
