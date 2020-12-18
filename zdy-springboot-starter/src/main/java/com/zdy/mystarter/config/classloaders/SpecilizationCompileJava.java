package com.zdy.mystarter.config.classloaders;

import com.zdy.mystarter.config.customization.EnableSpecilizationManageSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 将Java文件编译为一个class文件
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/8 16:06
 * @desc
 */
public class SpecilizationCompileJava {

    private static Logger logger= LoggerFactory.getLogger(SpecilizationCompileJava.class);

    /**
     * 将指定路径下的java文件编译到本路径下为class文件
     * @param fileFullPath
     * @return
     */
    public static boolean compileJava2Classes(String fileFullPath){
        logger.info("正在编译....");
        Process process=null;
        try{
            process=Runtime.getRuntime().exec(" javac "+fileFullPath);
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
        int result=process.exitValue();
        logger.info("编译完成....");
        return result==0;
    }

    /**
     * 将指定路径下的java文件编译到指定路径下为class文件
     * @param fileFullPath  java文件路径
     * @param targetClassPath class文件路径
     * @return
     */
    public static boolean compileJava2Classes(String fileFullPath,String targetClassPath){
        logger.info("正在编译....");
        Process process=null;
        try{
            process=Runtime.getRuntime().exec(" javac "+fileFullPath+" -d " +targetClassPath);
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
        int result=process.exitValue();
        logger.info("编译完成....");
        return result==0;
    }

}
