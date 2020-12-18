package com.zdy.mystarter.basic.proxy;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/4/24 10:29
 * @desc
 */
public class CglibProxyTest<T> implements InvocationHandler{

    public static void main(String[] args){

        CglibProxyTest<ProxyInterface> proxy=new CglibProxyTest(ProxyInterface.class);
        Object bean=proxy.getProxy();
        System.out.println(bean);


    }

    private Class<T> proxyInterface;

    public CglibProxyTest(Class<T> proxyInterface){
        this.proxyInterface=proxyInterface;
    }

    /**
     * 创建代理对象-1
     * @return
     */
    public T getProxy(){
        return (T)Proxy.newProxyInstance(proxyInterface.getClassLoader(),
                new Class[]{ProxyInterface.class},
                this);
    }

    /**
     * 创建代理对象-2
     * @param clazz  接口Class
     * @return
     */
    public Object createProxyInstance(Class clazz){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CglibProxy());
        return enhancer.create();
    }

    /**
     * 代理类-默认一个
     */
    class CglibProxy implements MethodInterceptor{

        @Override
        public Object intercept(Object bean,
                                Method method,
                                Object[] args,
                                MethodProxy methodProxy) throws Throwable {
            return method.invoke(bean,args);
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        /*if(method.getName().equals("toString")){
            System.out.println("execute toString method");
        }*/
        return null;
    }
}
