package com.zdy.mystarter.xmlparase;

import com.sun.deploy.xml.XMLParser;
import com.zdy.mystarter.tools.XmlPraser;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/15 16:53
 * @desc
 */
public class XmlPraseMain {
    public static void main(String[] args) {

        //
        String xmlStr="xml格式的字符串数据报文";
        //转换为实体类
        XmlPJEntity entity= XmlPraser.toObject(XmlPJEntity.class,xmlStr);

    }
}
