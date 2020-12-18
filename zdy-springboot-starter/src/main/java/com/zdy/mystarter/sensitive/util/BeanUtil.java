package com.zdy.mystarter.sensitive.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 15:00
 * @desc
 */
public final class BeanUtil {

    public BeanUtil(){}

    public static <T> T deepCopy(T object){
        Class clazz=object.getClass();
        String jsonString= JSON.toJSONString(object);
        return (T)JSON.parseObject(jsonString,clazz);
    }

    public static void copyProperties(Object source,Object target)throws Exception{
        try{
            List<Field> sourceFieldList =ClassUtil.getAllFieldList(source);
            Map<String,Field> targetFieldMap=ClassUtil.getAllFieldMap(target);
            Iterator var4=sourceFieldList.iterator();
            while (var4.hasNext()){
                Field sourceField=(Field)var4.next();
                String sourceFileName=sourceField.getName();
                Field targetField=(Field)targetFieldMap.get(sourceFileName);
                if(targetField!=null&&targetField.getType().equals(sourceField.getType())){
                    Object sourceFiledValue=sourceField.get(source);
                    targetField.set(target,sourceFiledValue);
                }
            }
        }catch (Exception e){
            throw e;
        }
    }

}
