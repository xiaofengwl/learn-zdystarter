package com.zdy.mystarter.basic.init;

import com.zdy.mystarter.basic.awares.SpringContextHolder;
import com.zdy.mystarter.basic.listeners.ZDYStartupListener;
import com.zdy.mystarter.basic.swaggers.flow.DefaultZdyBeanExcutor;
import com.zdy.mystarter.basic.swaggers.flow.ZdyBeanExcutor;
import com.zdy.mystarter.basic.swaggers.service.RestZdyService;
import com.zdy.mystarter.basic.swaggers.service.RestZdyServiceExportor;
import com.zdy.mystarter.basic.swaggers.service.ZDYRestHandlerMapping;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * TODO 指定模块的装配
 * <pre>
 *     测试细节：
 *         凡是@Configuration修饰的装配类中的@Bean都是按照从上到写的顺序加载处理的，
 *         只是能够加载成功就需要自己控制了，
 *         比如：
 *              @ConditionalOnBean(ZDYProperties.class)
 *              @ConditionalOnMissingBean(ZDYProperties.class)
 *              @ConditionalOnProperty(name="zdy.prop.init.switch",havingValue = "true")
 *              @ConditionalOnClass(SpringContextHolder.class)
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/19 9:31
 * @desc
 */
@Configuration
public class ZDYBeanConfig {

    /**
     * 注入资源文件-初始化配置信息
     * 装配条件：
     *     必须含有"zdy.prop.init.switch"属性且值为”true"的时候才会启动
     *     @Primary 如果发现多个当前类的对象，则依此为准
     * @return
     */
    @Primary
    @Bean(initMethod = "propInit")
    @ConditionalOnMissingBean(ZDYProperties.class)
    @ConditionalOnProperty(name="zdy.prop.init.switch",havingValue = "true")
    public ZDYProperties zdyProperties(){
        return new ZDYProperties();
    }

    /**
     * 注入Bean
     * 注入条件:
     *      1.必须ZDYProperties属性配置文件被成功加载后才会执行本Bean的装配
     *      2.必须存在指定的类：SpringContextHolder.class
     * 重点关注:
     *      1.在当前注入的方法zdYinitalizer(SpringContextHolder sch,ZDYProperties zdyProperties)
     *        中，含有的参数，在被Srping自动装配调用之前，会自动从IOC容器中找到相关的bean然后注入到该方法中，
     *        所以，此处的入参对象是有具体数据的，
     *        比如：
     *          ZDYProperties zdyProperties  这个对象就含有前一个Bean初始化过程中初始化的数据信息。
     * @return
     */
    @Bean
    @ConditionalOnBean(ZDYProperties.class)
    @ConditionalOnMissingBean(ZDYInitalizer.class)
    @ConditionalOnClass(SpringContextHolder.class)
    public ZDYInitalizer zdyInitalizer(SpringContextHolder sch,ZDYProperties zdyProperties){
        //带着初始化的资源进入
        return new ZDYInitalizer(zdyProperties);
    }

    /**
     * 注入ApplicationContext的监听器，
     * 当使用applicationContext.publishEvent(event)发布事件的时候，此处会自动处理。
     * 描述：
     *     如果容器中存在ApplicationListener的Bean，
     *     当ApplicationContext调用publishEvent方法时，对应的Bean会被触发。
     *     这一过程是典型的观察者模式的实现。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ZDYStartupListener.class)
    public ZDYStartupListener zdyStartupListener(){
        return new ZDYStartupListener();
    }

    /**
     * 注入SpringContextHolder
     * 如果缺失，就重新注入，一般没问题，因为SpringContextHolder类已经加了@Component
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SpringContextHolder.class)
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }

    /**
     * 注入RestTemplate，如果Spring容器中检查到没有则就注入进入
     * @LoadBalanced注解：做负载均衡
     * todo 说明
     *  使用RestTemplateCustomizer对所有标注了@LoadBalanced的RestTemplate Bean添加了一个
     *  LoadBalancerInterceptor拦截器。利用RestTempllate的拦截器，spring可以对restTemplate
     *  bean进行定制，加入loadbalance拦截器进行ip:port的替换，也就是将请求的地址中的服务逻辑名转为
     *  具体的服务地址。
     *  todo 特别注意：
     *      使用了@LoadBalanced注解之后，在使用restTemplate的时候需要用注册中心中注册的目标服务ID作为主机名称
     * @return
     */
    @Primary
    @Bean("fiegnRestTemplate")
    //@ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate getRestTemplate(){
        RestTemplateBuilder builder=SpringContextHolder.getBean(RestTemplateBuilder.class);
        RestTemplate restTemplate=builder.build();//new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory= BeanUtils.instantiateClass(HttpComponentsClientHttpRequestFactory.class);
        factory.setReadTimeout(12000);
        restTemplate.setRequestFactory(factory);
        //restTemplate.setInterceptors(new ArrayList<>());   //如果步设置为空集合的话，再次从restTemplate中获取factory就会是拦截器的对象
        return restTemplate;
    }

    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean("restTemplate2")
    @LoadBalanced
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate2(){
        return new RestTemplate();
    }


    /**
     * 装配Swagger发布接口服务-依赖项
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ZDYRestHandlerMapping.class)
    public ZDYRestHandlerMapping zdyRestHandlerMapping() {
        ZDYRestHandlerMapping zdyRestHandlerMapping=new ZDYRestHandlerMapping();
        return zdyRestHandlerMapping;

    }

    /**
     * 装配Swagger发布接口服务，本服务用于发布编码
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="zdy.export.rest.switch",havingValue = "true",matchIfMissing=false)
    public RestZdyServiceExportor restZdyServiceExportor(){
        RestZdyServiceExportor restZdyServiceExportor=new RestZdyServiceExportor();
        restZdyServiceExportor.setZdyRestHandlerMapping(zdyRestHandlerMapping());
        return restZdyServiceExportor;
    }

    /**
     * 注入执行器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ZdyBeanExcutor.class)
    public ZdyBeanExcutor zdyBeanExcutor(){
        DefaultZdyBeanExcutor zdyBeanExcutor=new DefaultZdyBeanExcutor();
        return zdyBeanExcutor;
    }

    /**
     * 注入rest接口发布服务
     * @param zdyBeanExcutor
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RestZdyService.class)
    public RestZdyService restZdyService(ZdyBeanExcutor zdyBeanExcutor){
        RestZdyService restZdyService=new RestZdyService();
        restZdyService.setZdyBeanExcutor(zdyBeanExcutor);
        return restZdyService;
    }


}
