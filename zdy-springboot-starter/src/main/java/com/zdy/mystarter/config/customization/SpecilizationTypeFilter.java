package com.zdy.mystarter.config.customization;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * TODO 定制ComponScan的类型过滤器
 * <pre>
 *  使用举例:
 *    @ComponentScan(
 *          excludeFilters = {
 *              @Filter(ype = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
 *              @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)
 *           }
 *    )
 *
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/1 12:14
 * @desc
 */
public class SpecilizationTypeFilter implements TypeFilter,ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     *
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * todo 定制匹配规则
     * @param metadataReader           读取当前正在被扫描类的信息
     * @param metadataReaderFactory     可以获取其他任何类的信息
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        //获取当前类的注解信息
        AnnotationMetadata annotationMetadata=metadataReader.getAnnotationMetadata();
        //获取当前被扫描类的信息
        ClassMetadata classMetadata=metadataReader.getClassMetadata();
        //获取当前类的资源
        Resource resource=metadataReader.getResource();

        String currentClassName=classMetadata.getClassName();
        //定制过滤逻辑，符合过滤条件的return true 即可

        //不满足过滤规则，return false
        return false;
    }
}
