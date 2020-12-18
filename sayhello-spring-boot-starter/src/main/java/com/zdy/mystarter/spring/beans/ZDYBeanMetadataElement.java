package com.zdy.mystarter.spring.beans;

/**
 * TODO IOC容器学习·BeanMetadataElement
 * <pre>
 *     从字面意思理解是：Bean的元数据类型，提供一个获取资源的方法、
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 14:19
 * @desc
 */
public interface ZDYBeanMetadataElement {
    /**
     *
     * @return
     */
    Object getSource();
}
