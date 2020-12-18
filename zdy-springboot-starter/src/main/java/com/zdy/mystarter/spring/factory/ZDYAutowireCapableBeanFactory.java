package com.zdy.mystarter.spring.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.config.NamedBeanHolder;

import java.util.Set;

/**
 * TODO IOC容器学习·AutowireCapableBeanFactory
 * <pre>
 *    todo 知识点：
 *    todo 1.spring Bean的作用域:
 *          scope=singleton(默认，单例，生成一个实例） 不是线程安全，性能高
 *          scope=prototype(多线程, 生成多个实例）
 *    todo 2.如何使用？
 *          （1）一般我们采用下面的方法就可实现属性的依赖注入。
 *           void autowireBean(Object existingBean) throws BeansException; // 使用autowireBeanProperties装配属性
 *
 *
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 17:30
 * @desc
 */
public interface ZDYAutowireCapableBeanFactory extends ZDYBeanFactory{

    /**
     * 自动装配类型
     */
    //todo 常量，不自动注入
    int AUTOWIRE_NO = 0;

    // todo 使用BeanName策略注入
    int AUTOWIRE_BY_NAME = 1;

    // todo 使用类型装配策略
    int AUTOWIRE_BY_TYPE = 2;

    //todo 使用构造器装配策略
    int AUTOWIRE_CONSTRUCTOR = 3;

    //todo 自动装配策略
    /** @deprecated */
    @Deprecated
    int AUTOWIRE_AUTODETECT = 4;

    String ORIGINAL_INSTANCE_SUFFIX = ".ORIGINAL";

    /**
     * 创建一个给定Class的实例。
     * 执行此Bean所有的关于Bean生命周期的接口方法如BeanPostProcessor
     * 此方法用于创建一个新实例，它会处理各种带有注解的域和方法，并且会调用所有Bean初始化时所需要调用的回调函数
     * 此方法并不意味着by-name或者by-type方式的自动装配，如果需要使用这写功能，可以使用其重载方法
     */
    <T> T createBean(Class<T> beanClass) throws BeansException;

    /**
     * Populate the given bean instance through applying after-instantiation callbacks
     * 通过调用给定Bean的after-instantiation及post-processing接口，对bean进行配置。
     * 此方法主要是用于处理Bean中带有注解的域和方法。
     * 此方法并不意味着by-name或者by-type方式的自动装配，如果需要使用这写功能，可以使用其重载方法autowireBeanProperties
     */
    void autowireBean(Object existingBean) throws BeansException;

    /**
     * Configure the given raw bean: autowiring bean properties, applying
     * 配置参数中指定的bean，包括自动装配其域，对其应用如setBeanName功能的回调函数。
     * 并且会调用其所有注册的post processor.
     * 此方法提供的功能是initializeBean方法的超集，会应用所有注册在bean definenition中的操作。
     * 不过需要BeanFactory 中有参数中指定名字的BeanDefinition。
     */
    Object configureBean(Object existingBean, String beanName) throws BeansException;

    /**
     * 创建一个指定class的实例，通过参数可以指定其自动装配模式（by-name or by-type）.
     * 会执行所有注册在此class上用以初始化bean的方法，如BeanPostProcessors等
     */
    Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;

    /**
     * 通过指定的自动装配策略来初始化一个Bean。
     * 此方法不会调用Bean上注册的诸如BeanPostProcessors的回调方法
     */
    Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;

    /**
     * 通过指定的自动装配方式来对给定的Bean进行自动装配。
     * 不过会调用指定Bean注册的BeanPostProcessors等回调函数来初始化Bean。
     * 如果指定装配方式为AUTOWIRE_NO的话，不会自动装配属性，但是依然会调用BeanPiostProcesser等回调方法。
     *
     * todo 说明：
     *      该方法可以帮助我们实现对Bean的属性的装配，
     *      比如：某个类中有@Value("")  如果直接去new的话，是无法获得对应的属性值的，但是可以通过这个
     *      方法：autowireBeanProperties来为类装填属性数据，就行IOC容器自动帮我们装填一样。
     */
    void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck)
            throws BeansException;

    /**
     * 将参数中指定了那么的Bean，注入给定实例当中
     * 此方法不会自动注入Bean的属性，它仅仅会应用在显式定义的属性之上。如果需要自动注入Bean属性，使用
     * autowireBeanProperties方法。
     * 此方法需要BeanFactory中存在指定名字的Bean。除了InstantiationAwareBeanPostProcessor的回调方法外，
     * 此方法不会在Bean上应用其它的例如BeanPostProcessors
     * 等回调方法。不过可以调用其他诸如initializeBean等方法来达到目的。
     */
    void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException;


    /**
     * 初始化参数中指定的Bean，调用任何其注册的回调函数如setBeanName、setBeanFactory等。
     * 另外还会调用此Bean上的所有postProcessors 方法
     */
    Object initializeBean(Object existingBean, String beanName) throws BeansException;

    /**
     * 调用参数中指定Bean的postProcessBeforeInitialization方法
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * 调用参数中指定Bean的postProcessAfterInitialization方法
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * 销毁参数中指定的Bean，同时调用此Bean上的DisposableBean和DestructionAwareBeanPostProcessors方法
     * 在销毁途中，任何的异常情况都只应该被直接捕获和记录，而不应该向外抛出。
     */
    void destroyBean(Object existingBean);

    /**
     * 查找唯一符合指定类的实例，如果有，则返回实例的名字和实例本身
     * 和BeanFactory中的getBean(Class)方法类似，只不过多加了一个bean的名字
     */
    <T> NamedBeanHolder<T> resolveNamedBean(Class<T> requiredType) throws BeansException;

    /**
     *
     * @param var1
     * @param var2
     * @return
     * @throws BeansException
     */
    Object resolveBeanByName(String var1, DependencyDescriptor var2) throws BeansException;

    /**
     * 解析出在Factory中与指定Bean有指定依赖关系的Bean
     * 参数建下一个方法
     */
    Object resolveDependency(DependencyDescriptor descriptor, String requestingBeanName) throws BeansException;

    /**
     * 解析指定Bean在Factory中的依赖关系
     * @param descriptor 依赖描述 (field/method/constructor)
     * @param requestingBeanName 依赖描述所属的Bean
     * @param autowiredBeanNames 与指定Bean有依赖关系的Bean
     * @param typeConverter 用以转换数组和连表的转换器
     * @return the 解析结果，可能为null
     */
    Object resolveDependency(DependencyDescriptor descriptor, String requestingBeanName,
                             Set<String> autowiredBeanNames, TypeConverter typeConverter) throws BeansException;





}
