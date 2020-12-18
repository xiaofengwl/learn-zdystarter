package com.zdy.mystarter.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * TODO 文件处理工具
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/20 12:38
 * @desc
 */
public class FileUtils {

    Logger logger= LoggerFactory.getLogger(FileUtils.class);

    /**
     * 将二进制数据写入到制定路径下
     * @param targetPath  格式要求：/home/temp/
     * @param bytes
     */
    public static void writeToFile(String targetPath,byte[] bytes,String sufix){
        FileOutputStream fos=null;
        try{
            File f=new File(targetPath);
            if(!f.exists()){
               f.mkdirs();
            }
            String fileName= UUID.randomUUID()+""+sufix;
            targetPath+=fileName;
            fos = new FileOutputStream(new File(targetPath));
            fos.write(bytes);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null!=fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取项目根路径，即，在磁盘中的绝对路径
     * @return
     */
    public static String getRootPath(Class target,String filter) throws Exception{
        return  target.getClassLoader().getResource(filter).toURI().getPath();
    }

}
