package com.zdy.mystarter.tools.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO RSA加密方式（公钥、私钥）
 * <pre>
 *    公开密钥加密(public-key cryptography),也成为非对称加密，是密码学的一种算法，
 *    他需要两个密钥，一个是公开密钥，另一个是私有密钥，一个用作加密的时候，另一个则用作解密。
 *
 *    TODO 对称加密&&非对称加密
 *          对称加密：加密和解密都是用同一个密钥的算法，称作对称加密。
 *          非对称加密：加密和解密需要不同的密钥。
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/20 14:54
 * @desc
 */
public class RsaEncryptUtil {

    /**
     * TODO 测试
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //生成公钥和私钥
        getKeyPair();
        //加密字符串
        String message = "xfwl123";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(message,keyMap.get(0));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn,keyMap.get(1));
        System.out.println("还原后的字符串为:" + messageDe);
    }

    //TODO 使用RSA一般需要产生公钥和私钥，当采用公钥加密时，使用私钥解密；采用私钥加密时，使用公钥解密
    private static final Logger logger= LoggerFactory.getLogger(RsaEncryptUtil.class);

    //TODO 用于封装随机产生的公钥与私钥
    //0-公钥、1-私钥
    private static Map<Integer,String> keyMap=new HashMap<>();

    /**
     * todo 随机生成秘钥对（公+私）
     */
    public static void getKeyPair()throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
    }

    /**
     * todo RSA公钥加密
     * @param src           加密字符串
     * @param publicKey     公钥
     * @return              密文
     * @throws Exception    加密过程中发生的异常
     */
    public static String encrypt(String src,String publicKey)throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(src.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * todo RSA私钥解密
     * @param src           加密字符串
     * @param privateKey    私钥
     * @return              明文
     * @throws Exception    解密过程中发生的异常
     */
    public static String decrypt(String src,String privateKey)throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(src.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
