package com.zdy.mystarter.tools;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 请求vo 响应vo 数据转移工具类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 10:30
 * @desc
 */
public class BeansUtils {

    private static final Logger logger= LoggerFactory.getLogger(BeansUtils.class);

    /**
     * todo Bean数据拷贝
     * @param sourceObject
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T convertObject(Object sourceObject,Class<T> targetClass){
        if(null==sourceObject){
            return null;
        }
        if (null==targetClass){
            return null;
        }
        Object newInstance=null;
        try{
            newInstance=targetClass.newInstance();
            BeanUtils.copyProperties(sourceObject,newInstance);
        }catch (Exception e){
            logger.error("转换失败！",e);
        }
        return (T)newInstance;
    }


}
