package com.zdy.mystarter.basic.ftps.confservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * TODO 默认的抽象服务配置类
 * <pre>
 *     说明：缺失getter、setter会导致属性绑定失败！！！
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/25 12:09
 * @desc
 */
@Component("baseFtpServer")
@ConfigurationProperties(prefix = "basic.ftp")
@PropertySource("classpath:/conf/basicftp.properties")
public class AbstractBaseFtpsConfig implements BasicFtpsConfig{

    /**
     * 服务地址
     */
    @Value("${basic.ftp.address}")
    private String ftpServerAddress=null;

    /**
     * ftp服务端口号，默认22
     */
    @Value("${basic.ftp.port}")
    private String port="22";

    /**
     * 服务用户名
     */
    @Value("${basic.ftp.user}")
    private String user=null;

    /**
     * 服务密码
     */
    @Value("${basic.ftp.password}")
    private String password=null;

    /**
     * 服务超时时间，默认700
     */
    @Value("${basic.ftp.timeOut}")
    private String timeOut="700";

    @Override
    public String getFtpServerAddress() {
        return ftpServerAddress;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getTimeOut() {
        return timeOut;
    }

    public void setFtpServerAddress(String ftpServerAddress) {
        this.ftpServerAddress = ftpServerAddress;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
}
