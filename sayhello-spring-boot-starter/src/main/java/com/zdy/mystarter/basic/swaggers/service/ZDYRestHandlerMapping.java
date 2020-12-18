package com.zdy.mystarter.basic.swaggers.service;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * TODO 定制Rest映射处理器
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 17:02
 * @desc
 */
public class ZDYRestHandlerMapping extends RequestMappingHandlerMapping{


    /**
     * todo 改变注解的值
     * @param annotation   注解对象
     * @param key          注解的属性key
     * @param newValue     注解的属性key要被设置的value值
     * @return
     */
    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {
        //获取注解的代理对象
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            //获取代理对象指定的field
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        //打开权限
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            //拿到该属性中保存的数据
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        //获取旧值
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        //重置新值
        memberValues.put(key, newValue);
        //返回被修改的旧值
        return oldValue;
    }

    /**
     * todo 注册操作
     * @param appName       应用名称
     * @param beanName      bean名称
     * @param serviceId     服务Id
     * @param method        方法
     * @param handlerType   处理器类型
     * @return
     */
    public String regist(String appName, String beanName, String serviceId , Method method, Class<?> handlerType) {
        logger.info("==[step-4]==========ZDYRestHandlerMapping.regist()======================");

        //获取请求映射信息
        RequestMappingInfo mapping = getMappingForMethod(method, handlerType);

        //获取服务格式参数
        String serviceFormat = getApplicationContext().getEnvironment().getProperty("zdy-export-rest-format");
        //判断格式是否已经配置
        if(StringUtils.isEmpty(serviceFormat)) {
            serviceFormat = "/*.do";
        }
        //替换服务uri
        String action=serviceFormat.replace("*", serviceId);
        //根据uri创建请求Condition
        PatternsRequestCondition patternsRequestCondition=new PatternsRequestCondition(action);
        PatternsRequestCondition apiPattern = new PatternsRequestCondition("").combine(patternsRequestCondition);
        //开始处理映射信息
        mapping = new RequestMappingInfo(mapping.getName(),
                                         apiPattern,
                                         mapping.getMethodsCondition(),
                                         mapping.getParamsCondition(),
                                         mapping.getHeadersCondition(),
                                         mapping.getConsumesCondition(),
                                         mapping.getProducesCondition(),
                                         mapping.getCustomCondition());
        //todo 注册操作
        registerHandlerMethod(beanName, method, mapping);
        //返回注册的uri
        return action;
    }
}
