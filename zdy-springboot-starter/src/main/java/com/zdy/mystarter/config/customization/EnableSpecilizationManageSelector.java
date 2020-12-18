package com.zdy.mystarter.config.customization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * TODO 用户扫描外部第三方配置对象，注入到IOC中
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 12:04
 * @desc
 */
public class EnableSpecilizationManageSelector implements ImportSelector,BeanFactoryAware{

    private static Logger logger= LoggerFactory.getLogger(EnableSpecilizationManageSelector.class);

    private BeanFactory beanFactory;
    /**
     * 获取BeanFactory
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    /**
     *
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        logger.info("EnableSpecilizationManageSelector-selectImports");

        return new String[0];
    }


}
