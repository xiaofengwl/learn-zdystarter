package com.zdy.mystarter.vo.annotation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO 测试实体类-报文数据-1
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/17 14:28
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVo<T> {

    /**
     * 报文头-服务码
     */
    private String mServiceCode;

    /**
     * 报文头-服务信息
     */
    private String mServiceMsg;

    /**
     * 报文体-BODY
     */
    private T mServiceBody;


}
