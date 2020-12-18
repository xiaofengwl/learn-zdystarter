package com.zdy.mystarter.spring.factory;

import org.springframework.lang.Nullable;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/29 11:05
 * @desc
 */
public class NullBean {
    NullBean() {
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return (this == obj || obj == null);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public String toString() {
        return "null";
    }

}
