package com.zdy.mystarter.spring.support;

import com.zdy.mystarter.spring.core.ZDYAttributeAccessor;
import com.zdy.mystarter.spring.util.ZDYAssert;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO IOC容器学习·AttributeAccessorSupport
 * <pre>
 *     作为接口AttributeAccessor的具体实现类，作为接口的支持类。
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 16:50
 * @desc
 */
public class ZDYAttributeAccessorSupport implements ZDYAttributeAccessor, Serializable {
    /**
     * 构造器
     */
    public ZDYAttributeAccessorSupport() {}

    /**
     * 定义一个容器
     */
    private final Map attributes = new LinkedHashMap();

    /**
     * 设置属性
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value) {
        ZDYAssert.notNull(name, "Name must not be null");
        if(value != null) {
            this.attributes.put(name, value);
        } else {
            this.removeAttribute(name);
        }

    }

    /**
     * 获取属性
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        ZDYAssert.notNull(name, "Name must not be null");
        return this.attributes.get(name);
    }

    /**
     * 移除属性
     * @param name
     * @return
     */
    public Object removeAttribute(String name) {
        ZDYAssert.notNull(name, "Name must not be null");
        return this.attributes.remove(name);
    }

    /**
     * 判断是否含有某属性
     * @param name
     * @return
     */
    public boolean hasAttribute(String name) {
        ZDYAssert.notNull(name, "Name must not be null");
        return this.attributes.containsKey(name);
    }

    /**
     * 获取所有属性的名称
     * @return
     */
    public String[] attributeNames() {
        Set attributeNames = this.attributes.keySet();
        return (String[])((String[])attributeNames.toArray(new String[attributeNames.size()]));
    }

    /**
     * 克隆属性信息
     * @param source
     */
    protected void copyAttributesFrom(ZDYAttributeAccessor source) {
        ZDYAssert.notNull(source, "Source must not be null");
        String[] attributeNames = source.attributeNames();

        for(int i = 0; i < attributeNames.length; ++i) {
            String attributeName = attributeNames[i];
            this.setAttribute(attributeName, source.getAttribute(attributeName));
        }

    }

    /**
     * equals比较
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(!(other instanceof ZDYAttributeAccessorSupport)) {
            return false;
        } else {
            ZDYAttributeAccessorSupport that = (ZDYAttributeAccessorSupport)other;
            return this.attributes.equals(that.attributes);
        }
    }

    /**
     * hashCode
     * @return
     */
    public int hashCode() {
        return this.attributes.hashCode();
    }
}
