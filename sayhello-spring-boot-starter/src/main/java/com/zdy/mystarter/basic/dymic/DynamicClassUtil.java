package com.zdy.mystarter.basic.dymic;

import com.zdy.mystarter.tools.FileUtils;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * TODO 动态生成类的工具
 * <pre>
 *     在内存中动态生成一个类的实例对象，并不会创建文件夹
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 11:32
 * @desc
 */
public class DynamicClassUtil {

    static Logger logger= LoggerFactory.getLogger(DynamicClassUtil.class);
    /**
     * todo 动态生成的类所在包路径
     */
    private  final static String basePackage="com.zdy.mystarter.basic.dymic.";

    /**
     * 生成逻辑
     * 格式：
     *      class com.zdy.mystarter.basic.dymic.test
     *      class com.zdy.mystarter.basic.dymic.DynamicClassUtil
     * @return
     */
    public static Class createClass(String className){

        ClassPool pool=ClassPool.getDefault();
        CtClass ctClass=null;
        Class retClass=null;
        try{
            //todo 生成对象
            ctClass=pool.makeClass(basePackage+className);
            //todo 给ctClass解锁
            ctClass.defrost();
            //todo 为类添加-属性
            CtField field1=new CtField(pool.get("java.lang.String"),"name",ctClass);
            ctClass.addField(field1);
            //todo 为类添加-方法
            CtMethod method1=new CtMethod( CtClass.intType,
                                          "add",
                                           new CtClass[]{CtClass.intType,CtClass.intType},
                                           ctClass);
            method1.setBody("return $1+$2;");
            method1.insertAfter("System.out.println(\"计算结果：\"+($1+$2));");
            ctClass.addMethod(method1);


            //添加注解
            //添加extends、implement
            //....

            //todo 导出文件
            String targetPath=FileUtils.getRootPath(DynamicClassUtil.class,"");
            targetPath+="dynamic/";
            FileUtils.writeToFile(targetPath,ctClass.toBytecode(),".class");
            System.out.println(targetPath);

            //todo 返回结果
            retClass=ctClass.toClass();

            //测试一下
            Class c = retClass;
            Object o = c.newInstance();
            Method m = c.getDeclaredMethod("add", int.class, int.class);
            System.out.println("Result:" + m.invoke(o, 1, 2));



        }catch (Exception e){
            logger.error("动态生成类失败！",e);
        }
        return retClass;
    }


    /**
     * todo 测试
     * @param args
     */
    public static void main(String[] args){
        createClass("DynamicClass");
        //Object obj= SpringContextHolder.getBean("DynamicClass");
        //System.out.println(obj);


    }




}
