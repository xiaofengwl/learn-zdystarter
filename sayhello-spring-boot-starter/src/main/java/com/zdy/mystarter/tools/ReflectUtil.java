package com.zdy.mystarter.tools;

import com.zdy.mystarter.annotations.HelloAnnotation;
import com.zdy.mystarter.model.DynamicBean;
import com.zdy.mystarter.model.LoggerEntity;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO CGLIB-动态代理方式为类添加新属性
 * <pre>
 *     使用cglib动态生成类与使用 commons-beanutils获取源对象属性-类型集合，封装成新对象并设置值代码：
 *     为指定类，添加属性和getter/setter方法
 * </pre>
 * 需要依赖commons-beanutils，已在pom.xml导入
 * @author lvjun
 * @version 1.0
 * @date 2020/2/20 9:00
 * @desc
 */
public class ReflectUtil {

    /**
     *
     * @param dest             目标对象
     * @param addProperties    要添加的属性
     * @return
     */
    public static Object getTarget(Object dest, Map<String, Object> addProperties) {
        // get property map
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class> propertyMap = new HashMap<>();

        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }

        // add extra properties
        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));

        // new dynamic bean
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);

        // add old value
        propertyMap.forEach((k, v) -> {
            try {
                // filter extra properties
                if (!addProperties.containsKey(k)) {
                    dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(dest, k));
                }
            } catch (Exception e) {
                System.out.println("e1:"+e.getMessage());
            }
        });
        // add extra value
        addProperties.forEach((k, v) -> {
            try {
                dynamicBean.setValue(k, v);
            } catch (Exception e) {
                System.out.println("e2:"+e.getMessage());
            }
        });
        Object target = dynamicBean.getTarget();


        return target;
    }

    /**
     * todo 指定类中的指定方法上注解存在的时候修改注解属性值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void annotationDeal() throws NoSuchFieldException,
            IllegalAccessException {
        Method[] methods = ReflectUtil.class.getDeclaredMethods();
        for(Method method : methods) {
            HelloAnnotation test = method.getAnnotation(HelloAnnotation.class);
            if(test != null) {
                InvocationHandler h = Proxy.getInvocationHandler(test);
                Field hField = h.getClass().getDeclaredField("memberValues");
                hField.setAccessible(true);
                Map memberMethods = (Map) hField.get(h);
                memberMethods.put("value", "TestDataProvider");
                String value = test.value();
            }
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        LoggerEntity entity = new LoggerEntity();
        entity.setAppName("appname");
        entity.setOperator("add");
        entity.setResult("result");
        entity.setUri("uri");
        entity.setMethod("method");
        Map<String, Object> addProperties = new HashMap() {{
            put("hello", "world");
            put("abc", "123");
        }};
        LoggerEntity a=(LoggerEntity)getTarget(entity, addProperties);
        System.out.println(a);

        Class clazz=a.getClass();
        Field[] fields=clazz.getDeclaredFields();
        for (Field field:fields){
            System.out.println(field);
        }
        System.out.println(clazz);
        // 通过反射查看所有方法名
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }
        //处理注解新增属性值问题
        annotationDeal();

    }

}
