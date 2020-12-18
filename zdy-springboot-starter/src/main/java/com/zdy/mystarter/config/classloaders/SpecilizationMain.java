package com.zdy.mystarter.config.classloaders;

/**
 * TODO 测试定制的启动类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/8 16:30
 * @desc
 */
public class SpecilizationMain {

    public static void main(String[] args) throws ClassNotFoundException {

        //首先找到java文件路径
        String targetPath="E:\\IED_ws\\maven_workspaces\\maven_self_starter\\zdy-springboot-starter\\src\\main\\java\\com\\zdy\\mystarter\\config\\classloaders\\SpecilizationJavaDTO.java";
        //设置编译后的classs目标路径
        String javacTargetPath="E:\\IED_ws\\maven_workspaces\\maven_self_starter\\zdy-springboot-starter\\target\\classes\\";
        //开始编译java-->class
        boolean compile=SpecilizationCompileJava.compileJava2Classes(targetPath,javacTargetPath);

        String targetClassPath="E:\\IED_ws\\maven_workspaces\\maven_self_starter\\zdy-springboot-starter\\target\\classes\\com\\zdy\\mystarter\\config\\classloaders\\SpecilizationJavaDTO.class";
        SpecilizationClassLoader classLoader=new SpecilizationClassLoader(targetClassPath);

        //目标资源的全类名
        String targetClassName="com.zdy.mystarter.config.classloaders.SpecilizationJavaDTO";

        //使用指定类加载器，加载class资源到内存中
        /**
         * todo-1 走ClassLoader中的loadClass(),双亲委派模式
         */
        Class<?> target=classLoader.loadClass(targetClassName);
        System.out.println("loadClass："+target.getClassLoader());

        /**
         * todo-2 直接调用指定类加载器去执行class资源的加载内存
         */
        Class<?> target2=classLoader.findClass(targetClassName);
        System.out.println("findCLass："+target2.getClassLoader());

        /**
         * todo-3 走ClassLoader中的loadClass(),双亲委派模式
         */
        Class<?> target3=classLoader.loadClass(targetClassName);
        System.out.println("loadClass："+target3.getClassLoader());

    }
}
