package com.zdy.mystarter.sensitive.service;

import com.zdy.mystarter.sensitive.ICondition;
import com.zdy.mystarter.sensitive.ISensitive;
import com.zdy.mystarter.sensitive.IStrategy;
import com.zdy.mystarter.sensitive.annotation.Sensitive;
import com.zdy.mystarter.sensitive.annotation.SensitiveEntity;
import com.zdy.mystarter.sensitive.annotation.metadata.SensitiveCondition;
import com.zdy.mystarter.sensitive.annotation.metadata.SensitiveStrategy;
import com.zdy.mystarter.sensitive.context.SensitiveContext;
import com.zdy.mystarter.sensitive.strategory.SensitiveStrategyBuiltIn;
import com.zdy.mystarter.sensitive.util.BeanUtil;
import com.zdy.mystarter.sensitive.util.ClassUtil;
import com.zdy.mystarter.sensitive.util.SensitiveStrategyBuiltInUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO 脱敏服务实现类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 13:57
 * @desc
 */
public class SensitiveService<T> implements ISensitive<T> {

    /**
     *
     * @param object
     * @return
     */
    @Override
    public T desCopy(T object) throws Exception{
        //1.初始化对象
        final Class clazz=object.getClass();
        final SensitiveContext context=new SensitiveContext();

        //2.深度复制对象
        final T copyObject= BeanUtil.deepCopy(object);

        //3.处理
        handleClassField(context,copyObject,clazz);

        return copyObject;
    }

    /**
     * 处理脱敏相关信息
     * @param context
     * @param copyObject
     * @param clazz
     */
    private void handleClassField(final SensitiveContext context,
                                  final Object copyObject,
                                  final Class clazz) throws Exception{

        //每一个实体对应的字段仅对当前clazz生效
        List<Field> fieldList= ClassUtil.getAllFieldList(clazz);
        context.setAllFieldList(fieldList);
        context.setCurrentObject(copyObject);
        try{
            for (Field field:fieldList){
                //设置当前处理的字段
                final Class fieldTypeClass=field.getType();
                context.setCurrentField(field);

                //处理@SensitiveEntity注解
                SensitiveEntity sensitiveEntity=field.getAnnotation(SensitiveEntity.class);
                if(null!=sensitiveEntity){
                    if(ClassUtil.isJavaBeanClass(fieldTypeClass)){
                        //为普通JavaBean
                        final Object fieldNewObject=field.get(copyObject);
                        handleClassField(context,fieldNewObject,fieldTypeClass);
                    }else if(ClassUtil.isArrayClass(fieldTypeClass)){
                        //为数组类型
                        Object[] arrays=(Object[]) field.get(copyObject);
                        if(null!=arrays&&arrays.length>0){
                            Object firstArrayEntity=arrays[0];
                            final Class entityFiledClass=firstArrayEntity.getClass();

                            //1.如果需要特殊处理，则循环特殊处理
                            if(needHandleEntryType(entityFiledClass)){
                                for (Object arrayEntity:arrays){
                                    handleClassField(context,arrayEntity,entityFiledClass);
                                }
                            }else{
                                //2.基础值，直接循环设置即可
                                final int arrayLength=arrays.length;
                                Object newArray=Array.newInstance(entityFiledClass,arrayLength);
                                for(int i=0;i<arrayLength;i++){
                                    Object entry=arrays[i];
                                    Object result=handleSesitiveEntry(context,entry,field);
                                    Array.set(newArray,i,result);
                                }
                                field.set(copyObject,newArray);
                            }
                        }
                    }else if(ClassUtil.isCollectionClass(fieldTypeClass)){
                        //Collection接口的子类
                        final Collection<Object> entryCollection=(Collection<Object>)field.get(copyObject);
                        if(null!=entryCollection&&entryCollection.size()>0){
                            Object firstCollectionEntry=entryCollection.iterator().next();
                            Class collectionEntryClass=firstCollectionEntry.getClass();

                            //1.如果需要特殊处理，则循环特殊处理
                            if(needHandleEntryType(collectionEntryClass)){
                                for (Object collectionEntry:entryCollection){
                                    handleClassField(context,collectionEntry,collectionEntryClass);
                                }
                            }else {
                                //2.基础值，直接循环设置即可
                                List<Object> newResultList = new ArrayList<>(entryCollection.size());
                                for (Object entry : entryCollection) {
                                    Object result = handleSesitiveEntry(context, entry, field);
                                    newResultList.add(result);
                                }
                                field.set(copyObject, newResultList);
                            }
                        }
                    }else{
                        //1.常见的基本类型，不做处理
                        //2.如果为Map,暂时不支持处理。
                        //3.其他
                        //处理单个字段脱敏信息
                        handleSensitive(context,copyObject,field);
                    }
                }else{
                    handleSensitive(context,copyObject,field);
                }
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 处理单个字段脱敏信息
     * @param context
     * @param entry
     * @param field
     */
    @SuppressWarnings("unchecked")
    private void handleSensitive(final SensitiveContext context,
                                 final Object entry,
                                 final Field field)throws Exception {
        try{
            //处理@Sensitive
            Sensitive sensitive=field.getAnnotation(Sensitive.class);
            if(null!=sensitive){
                Class<? extends ICondition> conditionClass=sensitive.condition();
                ICondition condition=conditionClass.newInstance();
                if(condition.valid(context)){
                    Class<? extends IStrategy> strategyClass=sensitive.strategy();
                    IStrategy strategy=strategyClass.newInstance();
                    final Object originalFieldValue=field.get(entry);
                    final Object result=strategy.des(originalFieldValue,context);
                    field.set(entry,result);
                }
            }
            //获取所有注解
            Annotation[] annotations=field.getAnnotations();
            if(null!=annotations&&annotations.length>0){
                ICondition condition=getCondition(annotations);
                if(null!=condition&&condition.valid(context)){
                    IStrategy strategy=getStragy(annotations);
                    if(null!=strategy){
                        final Object originalFieldValue=field.get(entry);
                        final Object result=strategy.des(originalFieldValue,context);
                        field.set(entry,result);
                    }
                }
            }
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 处理需要脱敏的单个对象
     * 1.为了简化操作，所有的自定义注解使用多个，不生效
     * 2.生效顺序如下
     *   （1）Sensitive
     *   （2）系统内置自定义注解
     *   （3）用户自定义注解
     *
     * @param context 上下文
     * @param entry   明细
     * @param field  字段信息
     * @return
     */
    @SuppressWarnings("unchecked")
    private Object handleSesitiveEntry(final SensitiveContext context,
                                       final Object entry,
                                       final Field field) throws Exception{
        try{
            //处理@Sensitive
            Sensitive sensitive=field.getAnnotation(Sensitive.class);
            if(null!=sensitive){
                Class<? extends ICondition> conditionClass=sensitive.condition();
                ICondition condition=conditionClass.newInstance();
                if(condition.valid(context)){
                    Class<? extends IStrategy> strategyClass=sensitive.strategy();
                    IStrategy strategy=strategyClass.newInstance();
                    return strategy.des(entry,context);
                }
            }
            //获取所有注解
            Annotation[] annotations=field.getAnnotations();
            if(null!=annotations&&annotations.length>0){
                ICondition condition=getCondition(annotations);
                if(null!=condition&&condition.valid(context)){
                    IStrategy strategy=getStragy(annotations);
                    if(null!=strategy){
                        return strategy.des(entry,context);
                    }
                }
            }
            return entry;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 获取策略
     * @param annotations
     * @return
     */
    private IStrategy getStragy(Annotation[] annotations) throws Exception{

        try{
            for(Annotation annotation:annotations){
                SensitiveStrategy sensitiveStrategy=annotation.annotationType().getAnnotation(SensitiveStrategy.class);
                if(null!=sensitiveStrategy){
                    Class<?extends IStrategy> clazz=sensitiveStrategy.value();
                    if(SensitiveStrategyBuiltIn.class.equals(clazz)){
                        return SensitiveStrategyBuiltInUtil.require(annotation.annotationType());
                    }else{
                        return ClassUtil.newInstance(clazz);
                    }
                }
            }
        }catch (Exception e){
            throw e;
        }
        return null;
    }

    /**
     *
     * @param annotations
     * @return
     */
    private ICondition getCondition(Annotation[] annotations)throws Exception {
        try{
            for(Annotation annotation:annotations){
                SensitiveCondition sensitiveCondition=annotation.annotationType().getAnnotation(SensitiveCondition.class);
                if(null!=sensitiveCondition){
                    Class<?extends ICondition> customClass=sensitiveCondition.value();
                    return ClassUtil.newInstance(customClass);
                }
            }
        }catch (Exception e){
            throw e;
        }
        return null;
    }

    /**
     * 需要特殊处理的列表、对象类型
     * @param entityFiledClass
     * @return
     */
    private boolean needHandleEntryType(Class entityFiledClass) {

        if(ClassUtil.isBaseClass(entityFiledClass)
                ||ClassUtil.isMapClass(entityFiledClass)){
            return false;
        }
        if(ClassUtil.isJavaBeanClass(entityFiledClass)
                || ClassUtil.isArrayClass(entityFiledClass)
                || ClassUtil.isCollectionClass(entityFiledClass)){
            return true;
        }
        return false;
    }
}
