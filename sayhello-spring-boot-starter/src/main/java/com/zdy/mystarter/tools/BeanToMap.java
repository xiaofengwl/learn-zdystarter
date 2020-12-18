package com.zdy.mystarter.tools;

import com.zdy.mystarter.annotations.EmpMapping;
import com.zdy.mystarter.model.common.Person;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 将Bean转换成Map对象
 * <pre>
 *     java bean中包含list<object>转map方法
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/2/26 18:28
 * @desc
 */
public class BeanToMap {


    /**
     * 测试-将含有List的实体Bean对象转换成Map对象
     * 说明：
     *      Map对象中保存集合数据的结构为：
     *      key-value
     *             保存单个变量：String-Object
     *                  key1-value1
     *             保存集合变量：String-List({map1},{map2})
     *                  key2-List[HashMap,HashMap,HashMap...]
     *
     * @param args
     */
    public static void main(String[] args){

        List<Person> list=new ArrayList<>();
        Person person=new Person();
        person.setAddress("深圳福田区");
        person.setName("xfwl");

        Person person2=new Person();
        person2.setAddress("深圳福田区");
        person2.setName("xfwl");

        Person person3=new Person();
        person3.setAddress("深圳福田区");
        person3.setName("xfwl");

        list.add(person2);
        list.add(person3);
        person.setPersonList(list);

        Map<String,Object> map=javabeanToMap(person);
        System.out.println(map);
    }

    /**
     * 方法-1
     * @param bean
     * @return
     */
    public static Map<String, Object> javabeanToMap(Object bean) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (bean == null) {
            return result;
        }

        Field[] fields = bean.getClass().getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return result;
        }

        for (Field field : fields) {
            // 重置属性可见(而且一般属性都是私有的)，否则操作无效
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }

            String key = field.getName();
            try {
                // 获取子类属性

                Object value = field.get(bean);
                if (value instanceof java.util.List) {
                    List list = (List) value;
                    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                    Object obj;
                    for (int i = 0; i < list.size(); i++) {
                        obj = list.get(i);
                        // list里是map或String，不会存在list里直接是list的，
                        Field[] fieldChilds = obj.getClass().getDeclaredFields();
                        Map<String, Object> resultChild = new HashMap<String, Object>();
                        for (Field field2 : fieldChilds) {
                            // 重置属性可见(而且一般属性都是私有的)，否则操作无效
                            boolean accessible2 = field2.isAccessible();
                            if (!accessible2) {
                                field2.setAccessible(true);
                            }
                            try {
                                // 获取属性名称及值存入Map
                                String key1 = field2.getName();
                                Object ooo = field2.get(obj);
                                System.out.println("==key " + key1 + ";;;;;" + ooo);
                                resultChild.put(key1, field2.get(obj));
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                            }

                            // 还原属性标识
                            field2.setAccessible(accessible2);
                        }
                        mapList.add(resultChild);
                    }

                    result.put(key, mapList);

                } else {

                    result.put(key, field.get(bean));
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 还原属性标识
            field.setAccessible(accessible);
        }

        // 获取父类属性
        fields = bean.getClass().getSuperclass().getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return result;
        }

        for (Field field : fields) {
            // 重置属性可见(而且一般属性都是私有的)，否则操作无效
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }

            // 获取属性名称及值存入Map
            String key = field.getName();
            try {
                result.put(key, field.get(bean));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 还原属性标识
            field.setAccessible(accessible);
        }

        return result;
    }

    /**
     * todo 将原来的java对象转换成map对象，方便转成json对象
     * @param source
     * @return
     * @throws IllegalAccessException
     * @throws ReflectiveOperationException
     */
    public static Object copyProperties(Object source)throws IllegalAccessException,ReflectiveOperationException{
        Map<String,Object> target=new HashMap<>();
        Class<? extends Object> clazz=source.getClass();
        Field[] fields=clazz.getDeclaredFields();
        Method[] methods=clazz.getDeclaredMethods();
        for (Field field:fields){
            String fieldName=field.getName();
            Class<?> fieldType=field.getType();
            for(Method method:methods){
                if(method.getName().equals(getFieldGetMethod(fieldName))){
                    EmpMapping empMapping=field.getAnnotation(EmpMapping.class);
                    if(empMapping!=null&&!StringUtils.isEmpty(empMapping.value())){
                        fieldName=empMapping.value();
                    }
                    int modifiers=field.getModifiers();
                    Object value=method.invoke(source,null);
                    String className=fieldType.getSimpleName().trim();
                    if(value!=null){
                        if("Integer".equals(className)){
                            target.put(fieldName,Integer.parseInt(value.toString()));
                            break;
                        }else if("String".equals(className)){
                            target.put(fieldName,value.toString());
                            break;
                        }else if(Number.class.isAssignableFrom(fieldType)){
                            target.put(fieldName,((Number)value).doubleValue());
                            break;
                        }else{
                            target.put(fieldName,value);
                            break;
                        }
                    }else{
                        target.put(fieldName,"");
                    }
                }
            }
        }
        return target;
    }

    /**
     * 获取指定属性的get方法
     * @param fieldName
     * @return
     */
    private static String getFieldGetMethod(String fieldName) {
        return new StringBuilder().append("get")
                .append(Character.toUpperCase(fieldName.charAt(0)))
                .append(fieldName.substring(1))
                .toString();
    }

    /**
     * 获取指定属性的set方法
     * @param fieldName
     * @return
     */
    private static String getFieldSetMethod(String fieldName) {
        return new StringBuilder().append("set")
                .append(Character.toUpperCase(fieldName.charAt(0)))
                .append(fieldName.substring(1))
                .toString();
    }
}
