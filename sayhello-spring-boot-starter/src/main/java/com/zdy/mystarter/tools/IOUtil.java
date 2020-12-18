package com.zdy.mystarter.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 文件流处理工具类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 9:14
 * @desc
 */
public class IOUtil {

    private static final Logger logger= LoggerFactory.getLogger(IOUtil.class);
    private IOUtil(){}

    /**
     * 获取文件流
     * @param path  文件路径
     * @return
     */
    public static InputStream loadInputStream(String path){
        InputStream is=null;
        try{
            is=new FileInputStream(path);
        }catch (FileNotFoundException e){
            logger.error("文件["+path+"]未找到",e);
        }
        if(null!=is){
            return is;
        }
        String xml_source;
        URL aURL;
        if(path.indexOf(":")!=-1){
            if(path.indexOf(":")<=2){
                xml_source="file:///"+path;
                try {
                    aURL=new URL(xml_source);
                    xml_source=aURL.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }else{
                xml_source=path;
            }
        }else{
            xml_source=path;
        }
        InputStream in;
        try {
            aURL=new URL(xml_source);
            in=aURL.openStream();
        } catch (Exception e) {
            in=IOUtil.class.getClassLoader().getResourceAsStream(path);
        }
        if(null!=in){
            return  in;
        }
        return Class.class.getResourceAsStream(path);
    }

    /**
     * 返回文件字符串
     * @param path
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String loadInputStreamToStr(String path) throws UnsupportedEncodingException {
        InputStream is=loadInputStream(path);
        return inputStreamToString(is);
    }

    /**
     * 通过流返回字符串
     * @param is
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String inputStreamToString(InputStream is) throws UnsupportedEncodingException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        byte[] bytes=new byte[1024];
        int len=0;
        try {
            while((len=is.read(bytes))!=-1){
                bos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=bos){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String data=new String(bos.toByteArray(),"UTF-8");
        return data;
    }

    /**
     * 按行读取文件，保存每一行
     * @param filePath 文件路径
     * @param encoding 文件编码
     * @return
     * @throws IOException
     */
    public static List<String> readLineFile(String filePath,String encoding)throws IOException{
        InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        List<String> fineList=new ArrayList<>();
        try{
            is=loadInputStream(filePath);
            isr=new InputStreamReader(is,encoding);
            br=new BufferedReader(isr);
            String line="";
            while((line=br.readLine())!=null){
                fineList.add(line);
            }
        }catch (IOException e){
            throw e;
        }finally {
            if(null!=is){
               is.close();
            }
            if(null!=isr){
                isr.close();
            }
            if(null!=br){
                br.close();
            }
        }
        return fineList;
    }










}
