package com.zdy.mystarter.basic.proxy;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 *
 * TODO 添加一个全局代理类,在被代理的目前方法执行前后做一些处理
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 12:12
 * @desc
 */
public class ZDYGlobalInvkerCglib implements MethodInterceptor{

    private final static Logger logger= LoggerFactory.getLogger(ZDYGlobalInvkerCglib.class);
    private final static String S="#";
    private Object target;

    public ZDYGlobalInvkerCglib(Object target){
        this.target=target;
    }

    /**
     * 代理处理
     * @param o
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o,
                            Method method,
                            Object[] args,
                            MethodProxy methodProxy) throws Throwable {

        String methodName=method.getName();
        //不处理
        if("toString".equals(methodName)||"hashCode".equals(methodName)||"equals".equals(methodName)){
            return method.invoke(target,args);
        }
        //方法全称：包名+类名+方法名
        methodName=target.getClass()+S+methodName;
        //如果没有入参则不打印参数
        if(null==args || args.length==0){
            logger.info("{}:start,args:{}",methodName);
        }else{
            logger.info("{}:start,args:{}",methodName,getPrintArgs(args));
        }

        //开始执行方法
        //开始计时
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        Object returnObj=null;
        try{
            returnObj=method.invoke(target,args);
            stopWatch.stop();
            if(method.getReturnType().equals("Void")){
                logger.info("{}:end,success,time：{}ms",methodName,stopWatch.getLastTaskTimeMillis());
            }else{
                logger.info("{}:end,success,time：{}ms,return msg:{}",methodName,stopWatch.getLastTaskTimeMillis(),getPrintArgs(returnObj));
            }
        }catch (Exception e){
            logger.error("{}:end，times:{}ms,error:{}",methodName,"",e);
        }finally {
            if(stopWatch.isRunning()){
                //停止计时
                stopWatch.stop();
            }
        }
        return returnObj;
    }

    /**
     * 获取打印参数
     * @param objects
     * @return
     */
    private String getPrintArgs(Object[] objects){
        StringBuffer sb=new StringBuffer("}");
        for (Object object:objects){
            if(!(object instanceof HttpServletRequest)
                    && !(object instanceof HttpServletResponse)
                    && !(object instanceof MultipartFile)){
                sb.append(getPrintArgs(object)).append(",");
            }
        }
       sb.deleteCharAt(sb.length()-1);
       sb.append("}");
        return sb.toString();
    }

    /**
     * 获取打印参数
     * @param object
     * @return
     */
    private String getPrintArgs(Object object){
        if(!(object instanceof HttpServletRequest)
                && !(object instanceof HttpServletResponse)
                && !(object instanceof MultipartFile)){
            try{
                return JSONObject.toJSONString(object);
            }catch (Exception e){
                //转换异常则不打印
            }
        }
        return "{}";
    }



}
