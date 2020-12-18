package com.zdy.mystarter.aspect;

import com.alibaba.fastjson.JSONObject;
import com.zdy.mystarter.annotations.CommonCheck;
import com.zdy.mystarter.aspect.check.CommonCheckExcutor;
import com.zdy.mystarter.basic.awares.SpringContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *  使用格式：
 *      @CommonCheck(desc="前置检查",type={CheckDemo.class})
 *      public Map test(){
 *          //...
 *      }
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 10:18
 * @desc
 */
@Aspect
@Component
public class CommonCheckAspectJ {

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.zdy.mystarter.annotations.CommonCheck)")
    public void pointCut(){}

    /**
     * 环绕增强
     * @param point
     * @return
     */
    @Around("pointCut()")
    public Object process(ProceedingJoinPoint point)throws Throwable{
        //todo 开始处理
        MethodSignature signature=(MethodSignature)point.getSignature();
        Method method=signature.getMethod();
        CommonCheck commonCheck=method.getAnnotation(CommonCheck.class);
        Object retValue=null;
        if(null!=commonCheck){
            //获取数据
            Object[] args=point.getArgs();
            //获取检查信息
            String desc=commonCheck.desc();
            System.out.println("开始执行@CommonCheck检查："+desc);
            Class[] classes=commonCheck.type();
            boolean checkFlag=false;
            for (Class clazz:classes){
                //说明：使用SpringContextHolder.getBean();要求检查类必须@Componment
                CommonCheckExcutor excutor= (CommonCheckExcutor)SpringContextHolder.getBean(clazz);
                //选取方法
                checkFlag=excutor.execute(args);
                if(!checkFlag){
                    break;
                }
            }
            if(checkFlag){
                retValue=point.proceed(point.getArgs());
            }else{
                //todo 直接获取请求对象
                //HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                //todo 直接获取响应对象
                HttpServletResponse response= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
                Map paraMap=new HashMap();
                paraMap.put("ERROR-CODE","EBLN-0000");
                paraMap.put("ERROR-MSG","检查不通过");
                retValue=paraMap;
                System.out.println("执行@CommonCheck检查失败：检查不通过");
                OutputStream outputStream=response.getOutputStream();
                outputStream.write(JSONObject.toJSONString(paraMap).getBytes());
                outputStream.flush();
                //关闭流资源
                if(null!=outputStream){
                    outputStream.close();
                }
            }
        }
        return retValue;
    }


}
