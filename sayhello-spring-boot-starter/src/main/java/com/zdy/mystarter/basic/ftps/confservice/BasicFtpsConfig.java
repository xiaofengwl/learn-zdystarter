package com.zdy.mystarter.basic.ftps.confservice;

/**
 * TODO 基础服务配置接口
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/25 12:06
 * @desc
 */
public interface BasicFtpsConfig {

    /**
     * 获取ftps服务地址
     * @return
     */
    String getFtpServerAddress();

    /**
     * 获取端口号
     * @return
     */
    String getPort();

    /**
     * 获取用户信息
     * @return
     */
    String getUser();

    /**
     * 获取密码
     * @return
     */
    String getPassword();

    /**
     * 获取超时时间
     * @return
     */
    String getTimeOut();
}
