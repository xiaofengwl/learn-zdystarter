package com.zdy.mystarter.config.customization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * TODO 用户注册-扫描第三方对象
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 12:10
 * @desc
 */
public class SpecilizationRegistrar implements ImportBeanDefinitionRegistrar{

    private static Logger logger= LoggerFactory.getLogger(SpecilizationRegistrar.class);

    /**
     * todo
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.info("SpecilizationRegistrar-registerBeanDefinitions-2-parameters");
    }

    /**
     * todo
     * @param importingClassMetadata
     * @param registry
     * @param importBeanNameGenerator
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        logger.info("SpecilizationRegistrar-registerBeanDefinitions-3-parameters");
    }
}
