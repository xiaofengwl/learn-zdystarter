package com.zdy.mystarter.tools;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO FTP文件上传下载工具
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 9:38
 * @desc
 */
public class FTPUtil {

    private static final Logger logger= LoggerFactory.getLogger(FTPUtil.class);
    private static final String DISPLIT="/";

    //ftp客户端,依赖包：apache-comons-net.jar
    private FTPClient ftpClient=new FTPClient();

    /**
     * 从FTP服务读取指定文件的文件流
     * @param ftpConf
     * @param fileName
     * @return
     * @throws IOException
     */
    public InputStream downLoadFile(FtpConf ftpConf,String fileName)throws IOException{
        InputStream in=null;
        try{
            //todo 1.建立连接
            logger.info("========建立连接=======");
            connectToServer(ftpConf);
            ftpClient.enterLocalPassiveMode();
            //todo 2.设置传输二进制文件
            logger.info("========设置传输二进制文件=======");
            setFileType(FTP.BINARY_FILE_TYPE);
            int reply=ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                ftpClient.disconnect();
                throw new IOException("连接到ftp服务器失败，IP:"+ftpConf.getIp());
            }
            //改变ftp服务路径
            ftpClient.changeWorkingDirectory("/home/temp/");
            //todo 3.ftp文件读取
            logger.info("========ftp文件读取=======");
            in=ftpClient.retrieveFileStream(fileName);
        }catch (FTPConnectionClosedException ex){
            logger.error("========ftp连接被关闭：{}=======", ex.toString());
            throw ex;
        }catch (Exception e){
            logger.error("========从ftp服务器读取文件失败=======", e);
            throw e;
        }
        return in;
    }

    /**
     * 设置文件传输类型
     * @param fileType
     */
    private void setFileType(int fileType) {
        try{
            ftpClient.setFileType(fileType);
        }catch (Exception e){
            logger.error("========ftp设置传输文件类型失败{}=======",e.toString());
        }
    }

    /**
     * 连接FTP服务
     * @param ftpConf
     */
    private void connectToServer(FtpConf ftpConf) {
        if(!ftpClient.isConnected()){
            int reply;
            try{
                ftpClient=new FTPClient();
                ftpClient.connect(ftpConf.getIp(),ftpConf.getPort());
                ftpClient.login(ftpConf.getUserName(),ftpConf.getPassword());
                reply=ftpClient.getReplyCode();
                if(!FTPReply.isPositiveCompletion(reply)){
                    ftpClient.disconnect();
                    logger.info("========拒绝连接FTP服务=======");
                }
            }catch (FTPConnectionClosedException ex){
                logger.error("========服务器：IP"+ftpConf.getIp()+"没有连接数{}======",ex.toString());
            }catch (Exception e){
                logger.error("========登录ftp服务器【"+ftpConf.getIp()+"】失败{}=======",e.toString());
            }
        }
    }


}
