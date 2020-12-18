package com.zdy.mystarter.basic.swaggers.provider;

import com.zdy.mystarter.basic.swaggers.export.SwaggerParameterModel;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 10:07
 * @desc
 */
public class ClassUtil {

    private final static Logger logger= LoggerFactory.getLogger(ClassUtil.class);

    private final static  String basePackage="com.zdy.mystarter.basic.swaggers.provider.vo.";

    private static int packageCpunter=1;

    /**
     * 动态生成类
     * @param list
     * @param className
     * @param modelId
     * @param classList
     * @return
     */
    public static Class createRefMoodel(List<SwaggerParameterModel> list,
                                        String className,
                                        String modelId,
                                        List<Class> classList) {

        //如果为空
        if(list.size()==0){
            classList.add(Object.class);
            return Object.class;
        }

        //准备动态生成一个类
        ClassPool pool=ClassPool.getDefault();
        CtClass ctClass=null;
        try{
            ctClass=pool.makeClass(basePackage+modelId+"."+className);
        }catch (Exception e){
            logger.error("Swagger处理-动态生成类失败！！！",e);
            packageCpunter++;
            String addPackage="a"+basePackage;
            ctClass.defrost();
            ctClass=pool.makeClass(basePackage+modelId+"."+addPackage+"."+className);
        }

        //准备迭代处理数据
        try{

            for (SwaggerParameterModel property:list){
                ctClass.addField(createField(modelId,property,ctClass,classList));
            }
            Class c=ctClass.toClass();
            return c;
        }catch (Exception e){
            logger.error("Swagger处理-向动态生成的类中添加属性Field失败！！！",e);
            return null;
        }
    }

    /**
     * 创建单个CtField
     * @param modelId
     * @param property
     * @param ctClass
     * @param classList
     * @return
     */
    private static CtField createField(String modelId,
                                       SwaggerParameterModel property,
                                       CtClass ctClass,
                                       List<Class> classList) throws Exception{

        CtField ctField=null;
        CtClass cts=getFieldType(property.getType(),property.getKey(),modelId,property.getReference(),classList);
        if(property.getType().equals("list")){
            ctField=new CtField(ClassPool.getDefault().get(List.class.getCanonicalName()),property.getKey(),ctClass);
        }else{
            try{
                ctField=new CtField(cts,property.getKey(),ctClass);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //设置修饰符
        ctField.setModifiers(Modifier.PUBLIC);

        //设置注解
        ConstPool constPool=ctClass.getClassFile().getConstPool();
        AnnotationsAttribute attr=new AnnotationsAttribute(constPool,AnnotationsAttribute.visibleTag);
        Annotation ann=new Annotation("io.swagger.annotations.ApiModelProperty",constPool);
        ann.addMemberValue("value",new StringMemberValue(property.getDescription(),constPool));

        if(ctField.getType().subclassOf(ClassPool.getDefault().get(String.class.getName()))){
            ann.addMemberValue("example",new StringMemberValue(property.getExample(),constPool));
        }

        attr.addAnnotation(ann);

        ctField.getFieldInfo().addAttribute(attr);
        return ctField;
    }

    /**
     * 获取Field类型
     * @param type
     * @param key
     * @param modelId
     * @param reference
     * @param classList
     * @return
     */
    private static CtClass getFieldType(String type,
                                        String key,
                                        String modelId,
                                        Object reference,
                                        List<Class> classList)throws NotFoundException {
        CtClass fieldType=null;
        switch(type){
            case "String":
            default:
                fieldType=ClassPool.getDefault().get(String.class.getName());
                break;
            case "Float":
                fieldType=ClassPool.getDefault().get(Float.class.getName());
                break;
            case "Long":
                fieldType=ClassPool.getDefault().get(Long.class.getName());
                break;
            case "Byte":
                fieldType=ClassPool.getDefault().get(Byte.class.getName());
                break;
            case "Short":
                fieldType=ClassPool.getDefault().get(Short.class.getName());
                break;
            case "Double":
                fieldType=ClassPool.getDefault().get(Double.class.getName());
                break;
            case "Int":
                fieldType=ClassPool.getDefault().get(Integer.class.getName());
                break;
            case "Date":
                fieldType=ClassPool.getDefault().get(Date.class.getName());
                break;
            case "List":
            case "object":
                Class c=createRefMoodel((List<SwaggerParameterModel>) reference,key,modelId,classList);
                fieldType=ClassPool.getDefault().get(c.getName());
        }
        return fieldType;
    }
}
