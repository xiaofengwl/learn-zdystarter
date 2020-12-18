package com.zdy.mystarter.config.classloaders;

import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TODO 自定义一个类加载器
 * <pre>
 *     类加载阶段：
 *         将类的class文件读入到内存中
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/9/8 15:44
 * @desc
 */
public class SpecilizationClassLoader extends ClassLoader{

    //目标文件
    private String targetPath;
    //构造器
    public SpecilizationClassLoader(String targetPath){
        this.targetPath=targetPath;
    }

    /**
     * 重写资源定位逻辑
     * loadClass(...)方法会调用findClass(...)方法
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //获取该class文件的字节码数组
        byte[] targetClassData=getData();
        Assert.notNull(targetClassData,"从文件获取字节码数组！！！");
        return defineClass(name,targetClassData,0,targetClassData.length);
    }

    /**
     * 将class文件转换为字节码组件
     * 扩展：
     *    比如：可以在此为文件内容做加密处理
     * @return
     */
    private byte[] getData() {
        File file=new File(this.targetPath);
        if(file.exists()){
            FileInputStream in=null;
            ByteArrayOutputStream out=null;
            try{
                in=new FileInputStream(file);
                out=new ByteArrayOutputStream();
                byte[] buffer=new byte[1024];
                int size=0;
                while((size=in.read(buffer))!=-1){
                    out.write(buffer,0,size);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    in.close();
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }
    }

    @Override
    public String toString() {
        return SpecilizationClassLoader.class.getName();
    }
}
