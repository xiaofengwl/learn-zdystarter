package com.zdy.mystarter.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;


/**
 * TODO 添加主题
 * <pre>
 *     文件上传处理工具类
 * </pre>
 *  使用格式：
 *      @ResponseBody
 *      @PostMapping("/**")
 *      @CommonCheck(desc="前置检查",type={CheckDemo.class})
 *      public void test(@RequestParam("file") MultipartFile[] file){
 *          //...
 *      }
 * @author lvjun
 * @version 1.0
 * @date 2020/3/17 10:46
 * @desc
 */
public class MultipartFileUtils {

    /**
     * 尚未完善的处理方法-案例-下载上传文件到指定位置
     * 处理MultipartFile对象
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public MultipartFile parserMultipartFile(MultipartFile multipartFile)throws  Exception{

        //获取数据
        //文件名称，带后缀
        String originalFileName=multipartFile.getOriginalFilename();
        //form-data中的name
        String name=multipartFile.getName();
        InputStream is=multipartFile.getInputStream();
        //保存-用户上传的文件到临时目录下
        multipartFile.transferTo(new File("/临时路径/temp.txt"));
        return null;
    }


}
