package com.zdy.mystarter.basic.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * TODO 初始化资源-properties文件
 * <pre>
 *     测试过程的细节说明：
 *         关于使用InitializingBean.afterPropertiesSet()做初始化和
 *         使用@Bean(initMethod = "propInit")做初始化执行先后的问题
 *         结论：
 *            InitializingBean.afterPropertiesSet() 执行优先于 @Bean(initMethod = "propInit")
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/19 9:50
 * @desc
 */
public class ZDYProperties implements InitializingBean,ApplicationContextAware {

    /**
     * 设置资源容器
     * @throws Exception
     */
     private Properties prop=new Properties();

    /**
     * 内置一个上下文对象
     */
    private  ApplicationContext applicationContext=null;

    /**
     * 资源初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ZDYProperties_开始初始化资源....");
        InputStream in=null;
        try{
            //资源-1
            in=new BufferedInputStream( this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("conf/rewrite.properties"));
            //资源-2
            Resource resource=new DefaultResourceLoader().getResource("application.properties");
            //开始装载数据
            prop.load(in);  //加入资源-1
            in.close();
            in=resource.getInputStream();
            prop.load(in);  //加入资源-2
            in.close();
            System.out.println("ZDYProperties_初始化资源完成....");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null!=in){
              in.close();
            }
        }
    }

    /**
     * 接收Spring应用上下文对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * 提供获取资源的方式
     * @return
     */
    public Properties getProp() {
        return prop;
    }

    /**
     * 定制一个当前资源类的初始化方法，在@Bean(initMethod = "propInit")
     */
    public void propInit(){
        System.out.println("ZDYProperties__定制初始化方法__@Bean(initMethod = \"propInit\")");
    }
}
