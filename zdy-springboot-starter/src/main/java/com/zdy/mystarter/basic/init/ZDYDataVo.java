package com.zdy.mystarter.basic.init;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 11:57
 * @desc
 */
@Data
//@ConfigurationProperties(prefix = "rewrite.hello")
public class ZDYDataVo {
    private String msg;

    @Value("${rewrite.hello.start}")
    private String start;

    @Value("${rewrite.hello.start}")
    private String end;
}
