package com.zdy.mystarter.spring.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * TODO IOC容器学习·-断言
 * <pre>
 *     一堆的判断检查
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/22 16:32
 * @desc
 */
public class ZDYAssert {
    /**
     * 构造器
     */
    public ZDYAssert() {}

    /**
     * 判断是否true,如果不是，指定抛出信息
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if(!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否true,如果不是，默认抛出信息
     * @param expression
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * 判断是否为null,如果不是，则抛出指定信息
     * @param object
     * @param message
     */
    public static void isNull(Object object, String message) {
        if(object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否为null,如果不是，则抛出默认信息
     * @param object
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * 判断为空，则抛出指定信息的报异常
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 调用上面的方法
     * @param object
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * text传入的参数不为空
     * str != null && !str.isEmpty();
     * @param text
     * @param message
     */
    public static void hasLength(String text, String message) {
        if(!StringUtils.hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 调用上面的方法
     * @param text
     */
    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * 判断入参text是否为空
     * str != null && !str.isEmpty() && containsText(str);
     *
     * containsText方法：
     *  for(int i = 0; i < strLen; ++i) {
     *      if(!Character.isWhitespace(str.charAt(i))) {
     *       return true;
     *   }
     * @param text
     * @param message
     */
    public static void hasText(String text, String message) {
        if(!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 调用上面的方法
     * @param text
     */
    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * 不包含
     * @param textToSearch
     * @param substring
     * @param message
     */
    public static void doesNotContain(String textToSearch, String substring, String message) {
        if(StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.indexOf(substring) != -1) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    /**
     * 数组不为空
     *  array == null || array.length == 0;
     * @param array
     * @param message
     */
    public static void notEmpty(Object[] array, String message) {
        if(ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * 数组中的元素不为空
     * @param array
     * @param message
     */
    public static void noNullElements(Object[] array, String message) {
        if(array != null) {
            for(int i = 0; i < array.length; ++i) {
                if(array[i] == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }

    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     * 集合不为空
     * @param collection
     * @param message
     */
    public static void notEmpty(Collection collection, String message) {
        if(CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection collection) {
        notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * Map不为空
     * @param map
     * @param message
     */
    public static void notEmpty(Map map, String message) {
        if(CollectionUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Map map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    /**
     * 非空且可实例化isInstance
     * @param clazz
     * @param obj
     */
    public static void isInstanceOf(Class clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    public static void isInstanceOf(Class type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if(!type.isInstance(obj)) {
            throw new IllegalArgumentException(message + "Object of class [" + (obj != null?obj.getClass().getName():"null") + "] must be an instance of " + type);
        }
    }

    public static void isAssignable(Class superType, Class subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * 非空且是否是指定的类型
     * TODO isAssignableFrom()方法与instanceof关键字的区别总结为以下两个点：
     *   1.第一点
     *        isAssignableFrom()方法是从类继承的角度去判断，
     *        instanceof关键字是从实例继承的角度去判断。
     *   2.第二点
     *        isAssignableFrom()方法是判断是否为某个类的父类，
     *        instanceof关键字是判断是否某个类的子类。
     * @param superType
     * @param subType
     * @param message
     */
    public static void isAssignable(Class superType, Class subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if(subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    /**
     * fasle则抛出IllegalStateException
     * @param expression
     * @param message
     */
    public static void state(boolean expression, String message) {
        if(!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }
}
