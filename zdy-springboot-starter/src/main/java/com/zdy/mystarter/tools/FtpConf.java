package com.zdy.mystarter.tools;

import lombok.Data;

/**
 * TODO FTPConfig
 * <pre>
 *     ftp配置父类
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 9:46
 * @desc
 */
@Data
public class FtpConf {

    private String ip;
    private int port;
    private String userName;
    private String password;
}
