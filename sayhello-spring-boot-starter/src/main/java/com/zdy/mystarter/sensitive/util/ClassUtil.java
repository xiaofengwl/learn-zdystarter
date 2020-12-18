package com.zdy.mystarter.sensitive.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 15:05
 * @desc
 */
public class ClassUtil {
    private static final Class[] BASE_TYPE_CLAS=new Class[]{String.class,Boolean.class,Character.class,Byte.class,Short.class,Integer.class,Long.class,Float.class,Double.class,Void.class,Object.class,Class.class};

    public ClassUtil(){}

    public static List<Field> getAllFieldList(Object object){
        if(null==object){
            return Collections.emptyList();
        }else{
            Class clazz=object.getClass();
            return getAllFieldList(clazz);
        }
    }

    public static List<Field> getAllFieldList(Class clazz){
        Set<Field> fieldSet=new HashSet<>();
        for (Class tempClass=clazz;tempClass!=null;tempClass=tempClass.getSuperclass()){
            fieldSet.addAll(Arrays.asList(tempClass.getDeclaredFields()));
        }
        Iterator var3=fieldSet.iterator();
        while (true){
            Field field;
            do{
                if(!var3.hasNext()){
                    return new ArrayList<>(fieldSet);
                }
                field= (Field) var3.next();
            }while(Modifier.isFinal(field.getModifiers())&& Modifier.isStatic(field.getModifiers()));
            field.setAccessible(true);
        }
    }

    public static Map<String,Field> getAllFieldMap(Object object){
        List<Field> fieldList=getAllFieldList(object);
        Map<String,Field> map=new HashMap<>(fieldList.size());
        Iterator var3=fieldList.iterator();
        while (var3.hasNext()){
            Field field=(Field) var3.next();
            String fieldName=field.getName();
            map.put(fieldName,field);
        }
        return map;
    }

    public static boolean isMapClass(Class<?> clazz){
        return Map.class.equals(clazz);
    }

    public static boolean isArrayClass(Class<?> clazz){
        return clazz.isArray();
    }

    public static boolean isCollectionClass(Class<?> clazz){
        return Collection.class.isAssignableFrom(clazz);
    }

    public static boolean isIterableClass(Class<?> clazz){
        return Iterable.class.isAssignableFrom(clazz);
    }

    public static boolean isBaseClass(Class<?> clazz){
        if(clazz.isPrimitive()){
            return true;
        }else{
            Class[] var1=BASE_TYPE_CLAS;
            int var2=var1.length;
            for (int var3=0;var3<var2;++var3){
                Class baseClazz=var1[var3];
                if(baseClazz.equals(clazz)){
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isAbstractlass(Class<?> clazz){
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public static boolean isJavaBeanClass(Class<?> clazz){
        return  null!=clazz
                && !clazz.isInterface()
                && !isAbstractlass(clazz)
                && !clazz.isEnum()
                && !clazz.isArray()
                && !clazz.isAnnotation()
                && !clazz.isSynthetic()
                && !clazz.isPrimitive();
    }

    public static <T> T newInstance(Class<T> clazz)throws Exception{
        try{
            return clazz.newInstance();
        }catch (Exception e){
            throw e;
        }
    }


}
