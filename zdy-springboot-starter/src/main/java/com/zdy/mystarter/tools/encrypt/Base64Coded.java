package com.zdy.mystarter.tools.encrypt;

import org.apache.commons.codec.binary.Base64;

/**
 * TODO 使用apache.commons-codex进行Base64对字符串进行编码与解码
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/20 15:01
 * @desc
 */
public class Base64Coded {

    public static void main(String[] args) {
        String string = "xfwl1234";
        //编码
        String encode = encode(string.getBytes());
        System.out.println(string + "\t编码后的字符串为：" + encode);
        //解码
        String decode = decode(encode.getBytes());
        System.out.println(encode + "\t字符串解码后为：" + decode);
    }
    //base64 解码
    public static String decode(byte[] bytes) {
        return new String(Base64.decodeBase64(bytes));
    }

    //base64 编码
    public static String encode(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
}
