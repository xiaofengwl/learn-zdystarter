package com.zdy.mystarter.spring.config;

import com.zdy.mystarter.spring.beans.ZDYBeanMetadataElement;
import com.zdy.mystarter.spring.core.ZDYAttributeAccessor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;

/**
 * TODO IOC容器学习·自定义一个BeanDefinition接口
 * <pre>
 *     BeanDefinition抽象了我们对Bean的定义，是IOC容器其作用的主要数据类型。
 *   IOC容器是用来管理对象依赖关系的，对IOC容器来说，Beanfinition就是对依赖反转模式中管理的
 *   对象依赖关系的数据抽象，是容器中能都实现依赖反转的核心数据结构。
 *     依赖反转都是围绕对BeanDefinition的处理来完成的。
 * </pre>
 *     本接口继承了另外2个接口:
 *          1.AttributeAccessor 属性操作接口
 *          2.BeanMetadataElement 资源获取接口
 * todo 重点说明：
 *     如果当前Bean设置了@Bean(lazyInit=true),那么在IOC容器初始化的时候就已经加载进入IOC
 *     容器中了，直接通过@Autowire即可获取使用，而普通的@Bean,则需要getBean()方式才会注入到IOC中
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 14:07
 * @desc
 */
public interface ZDYBeanDefinition extends ZDYAttributeAccessor,ZDYBeanMetadataElement{

    /**
     * 定义作用域
     */
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    /**
     * 定义角色类型
     */
    int ROLE_APPLICATION = 0;
    int ROLE_SUPPORT = 1;
    int ROLE_INFRASTRUCTURE = 2;

    /**
     * 获取父类名称
     * @return
     */
    String getParentName();

    /**
     * 设置父类名称
     * @param var1
     */
    void setParentName(String var1);

    /**
     * 获取Bean的类名称
     * @return
     */
    String getBeanClassName();

    /**
     * 设置Bean的类名称
     * @param var1
     */
    void setBeanClassName(String var1);

    /**
     * 通过Bean来获取工厂Bean的名称
     * @return
     */
    String getFactoryBeanName();

    /**
     * 设置工厂Bean的名称
     * @param var1
     */
    void setFactoryBeanName(String var1);

    /**
     * 获取工厂方法
     * @return
     */
    String getFactoryMethodName();

    /**
     * 设置工厂方法
     * @param var1
     */
    void setFactoryMethodName(String var1);

    /**
     * 获取作用域
     * @return
     */
    String getScope();

    /**
     * 设置作用域
     * @param var1
     */
    void setScope(String var1);

    /**
     * 是否自动装配
     * @return
     */
    boolean isAutowireCandidate();

    /**
     * 设置是否自动装配标记
     * @param var1
     */
    void setAutowireCandidate(boolean var1);

    /**
     * 获取构造方法的参数值对象
     * @return
     */
    ConstructorArgumentValues getConstructorArgumentValues();

    /**
     * 获取属性值对象
     * @return
     */
    MutablePropertyValues getPropertyValues();

    /**
     * 判断是否是单例
     * @return
     */
    boolean isSingleton();

    /**
     * 判断是否是抽象类
     * @return
     */
    boolean isAbstract();

    /**
     * 判断是否是懒加载
     * @return
     */
    boolean isLazyInit();

    /**
     * 获取角色类型
     * @return
     */
    int getRole();

    /**
     * 获取描述
     * @return
     */
    String getDescription();

    /**
     * 获取资源描述
     * @return
     */
    String getResourceDescription();

    /**
     * 获取Bean
     * @return
     */
    BeanDefinition getOriginatingBeanDefinition();
}
