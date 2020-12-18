package com.zdy.mystarter.tools.encrypt;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * TODO AES加解密工具类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/20 9:59
 * @desc
 */
public class AesEncryptUtil {

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        //说明：key为加解密的因子
        //普通加解密
        String encry=encryptBase64("XFWL","123456");
        System.out.println(encry);
        String srcData=decrypt(encry,"123456");
        System.out.println(srcData);

        //偏移量加解密
        String encryIv=encryptBase64OnIv("XFWL","123456");
        System.out.println(encryIv);
        String srcDataIv=decryptOnIv(encryIv,"123456");
        System.out.println(srcDataIv);

    }


    private static final Logger logger= LoggerFactory.getLogger(AesEncryptUtil.class);

    //秘钥算法
    private static final String KEY_ALGORITHM="AES";

    //加解密算法/工作模式/填充方式
    private static final String CIPHER_ALGORITHM_ECB="AES/ECB/PKCS5Padding";
    private static final String CIPHER_ALGORITHM_CBC="AES/CBC/PKCS5Padding";  //CBC模式，需要设置偏移量IV
    private static final String SECURE_RANDOM="SHA1PRNG";
    private static final int KEYSIZE_128=128;
    private static final String DEFAULT_CHARSET="UTF-8";
    private static final String IV="1234567890132456";         //偏移量:16位

    /**
     * 加密操作
     * @param data
     * @param key
     * @return
     */
    public static String encryptBase64(String data,String key){
        String str=null;
        try{
            byte[] keyb=initAesKey(key);
            byte[] result=encrypt(data,keyb);
            //此处使用BASE64做转码功能，同时能起到2次加密的作用
            str= Base64.encodeBase64String(result);
            str=str.replaceAll("\r\n","");
        }catch (Exception e){
            logger.error("===============AES加密异常=====================");
        }
        return str;
    }

    /**
     * 加密操作
     * @param data
     * @param key
     * @return
     */
    public static String encryptBase64OnIv(String data,String key){
        String str=null;
        try{
            byte[] keyb=initAesKey(key);
            byte[] result=encryptOnIv(data,keyb);
            //此处使用BASE64做转码功能，同时能起到2次加密的作用
            str= Base64.encodeBase64String(result);
            str=str.replaceAll("\r\n","");
        }catch (Exception e){
            logger.error("===============AES加密异常=====================");
        }
        return str;
    }
    /**
     * 加密处理
     * @param data
     * @param keyb
     * @return
     */
    private static byte[] encrypt(String data, byte[] keyb) {
        byte[] result=null;
        try{
            SecretKeySpec secretKeySpec=new SecretKeySpec(keyb,KEY_ALGORITHM);
            //实例化Cipher对象，它用于完成实际的加密工作
            Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            //初始化Cipher对象，设置加密模式
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            //加密数据
            result=cipher.doFinal(data.getBytes(DEFAULT_CHARSET));
        }catch (Exception e){
            logger.error("===============AES加密异常=====================");
        }
        return result;
    }

    /**
     * 加密处理
     * @param data
     * @param keyb
     * @return
     */
    private static byte[] encryptOnIv(String data, byte[] keyb) {
        byte[] result=null;
        try{
            SecretKeySpec secretKeySpec=new SecretKeySpec(keyb,KEY_ALGORITHM);
            //偏移量对象
            IvParameterSpec ivParameterSpec=new IvParameterSpec(IV.getBytes());
            //实例化Cipher对象，它用于完成实际的加密工作
            Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            //初始化Cipher对象，设置加密模式,偏移量
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
            //加密数据
            result=cipher.doFinal(data.getBytes(DEFAULT_CHARSET));
        }catch (Exception e){
            logger.error("===============AES加密异常=====================");
        }
        return result;
    }

    /**
     * 根据第三方约定的privateKey，动态创建AES秘钥（key)
     * @param privateKey
     * @return
     */
    private static byte[] initAesKey(String privateKey) {
        byte[] keyb=null;
        try{
            //获取aes加密实例
            KeyGenerator kgen=KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random=SecureRandom.getInstance(SECURE_RANDOM);
            random.setSeed(privateKey.getBytes());
            kgen.init(KEYSIZE_128,random);
            SecretKey secretKey=kgen.generateKey();
            keyb=secretKey.getEncoded();
        }catch (Exception e){
            logger.error("===============动态创建AES秘钥异常=====================");
        }
        return keyb;
    }

    /**
     * 解密
     * @param srcContent
     * @return
     */
    public static String decrypt(String srcContent,String key){
        byte[] result=null;
        try{
            byte[] raw=initAesKey(key);
            SecretKeySpec secretKeySpec=new SecretKeySpec(raw,KEY_ALGORITHM);
            //实例化Cipher对象，它用于完成实际的加密工作
            Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            //初始化Cipher对象，设置加密模式
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
            //解密数据
            byte[] encrypted1 = new Base64().decode(srcContent);//先用base64解密
            result=cipher.doFinal(encrypted1);
        }catch (Exception e){
            logger.error("===============AES加密异常=====================");
        }
        return new String(result);
    }

    /**
     * 解密-IV偏移量
     * @param srcContent
     * @return
     */
    public static String decryptOnIv(String srcContent,String key){
        byte[] result=null;
        try{
            byte[] raw=initAesKey(key);
            //偏移量对象
            IvParameterSpec ivParameterSpec=new IvParameterSpec(IV.getBytes());
            SecretKeySpec secretKeySpec=new SecretKeySpec(raw,KEY_ALGORITHM);
            //实例化Cipher对象，它用于完成实际的加密工作
            Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            //初始化Cipher对象，设置加密模式
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
            //解密数据
            byte[] encrypted1 = new Base64().decode(srcContent);//先用base64解密
            result=cipher.doFinal(encrypted1);
        }catch (Exception e){
            logger.error("===============AES加密异常=====================");
        }
        return new String(result);
    }
}
