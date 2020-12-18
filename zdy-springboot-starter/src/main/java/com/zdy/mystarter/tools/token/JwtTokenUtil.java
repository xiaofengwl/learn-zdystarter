package com.zdy.mystarter.tools.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 获取token工具类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/20 11:47
 * @desc
 */
public class JwtTokenUtil {

    /**
     * TODO 测试
     *    所有的信息都被存放在token中，但必须知道秘钥
     * @param args
     */
    public static void main(String[] args) {
        String token=getToken("xfwl","123456",100);//超时时间：86400=24*60*40=1天
        System.out.println(token);

        //打印结果：
        //eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJjcmVhdFRpbWUiOjE2MDMxNzU0OTUzODUsImV4cCI6MTYwMzI2MTg5NSwidXNlcklkIjoidXNlcklkIiwiaWF0IjoxNjAzMTc1NDk1fQ._hcyP1j7uHoUXi2vATcL1mtfPcOgKrsOdhD94nJsGhw

        //获取保存的信息
        String userId=getUserId(token,"123456","userId").toString();
        System.out.println(userId);

    }

    private static final Logger logger= LoggerFactory.getLogger(JwtTokenUtil.class);

    /**
     * TODO 说明
     *  JWT 由3部分组成: header(Map集合),playload(负载,也可以把它看做请求体body,也是一个map集合),signature(签名,有header和playload加密后再跟secrect加密生成)
     *  header:有2个值,一个是类型,一个是算法,类型就是JWT,不会变,算法有2种选择,HMAC256和RS256,基本选择HMAC256
     *  playload:类似于post请求的请求体,是一个map集合,可以存很多很多值,如存用户的信息
     *  signature:由header(Base64加密后)和playload(Base64加密后)再加上secrect(秘钥生成)
     *  Base64加密是可逆的,所以存在header和playload的数据不能是敏感数据
     *
     *  playload有一些值定义:
     *       iss: jwt签发者
     *      sub: jwt所面向的用户
     *      aud: 接收jwt的一方
     *      exp: jwt的过期时间，这个过期时间必须要大于签发时间
     *      nbf: 定义在什么时间之前，该jwt都是不可用的.
     *      iat: jwt的签发时间
     *      jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     *
     * @param userId 用户编号
     * @param secrectKey 秘钥(密码)
     * @param expireTime 过期时间单位s
     * @return
     */
    public static String getToken(String userId,String secrectKey,int expireTime){
        Date createDate=new Date();
        Date expireDate= DateUtils.addSeconds(createDate,expireTime);

        Map<String ,Object> header=new HashMap<>();
        header.put("type","JWT");
        header.put("alg","HS256");

        //token创建底层使用的是设计模式中的创建者模式,了解该模式对于下面的代码比较容易理解
        String token= JWT.create()
                         .withHeader(header)
                         .withClaim("userId",userId)//playload的一部分:withClaim底层是一个map,可以不断使用链式表达式存数据
                         .withClaim("creatTime",createDate.getTime())
                         .withIssuedAt(createDate)         //创建时间 //playload的一部分
                         .withExpiresAt(expireDate)        //过期时间 //playload的一部分
                         .sign(Algorithm.HMAC256(secrectKey));//生成 signature
        return token;
    }

    /**
     * TODO 如果token过期了,解析时就会报错,所以捕捉到异常时就知道是否过期了
     * @param token     token信息
     * @param secretKey 秘钥信息
     * @return
     */
    public static DecodedJWT decodeToken(String token,String secretKey){
        DecodedJWT jwt=null;
        try{
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(secretKey)).build();
            jwt=jwtVerifier.verify(token);
            return jwt;
        }catch (JWTVerificationException ex){
            logger.error("========token过期了=============");
            throw ex;
        }
    }

    /**
     * TODO 也可以通过token不需要密钥直接获取 DecodedJWT
     * @Param token
     * @return
     */
    public static DecodedJWT decodedToken(String token){
        DecodedJWT decode = JWT.decode(token);
        return decode;
    }

    //获取payLoad的值
    public static Object getUserId(String token,String secrectKey,String payLoadKey){
        DecodedJWT jwt=decodeToken(token,secrectKey);
        Map<String,Claim> claims=jwt.getClaims();
        Claim claim=claims.get(payLoadKey);//也可以通过claims获取其他值,具体根据存到playlaod里面的数据来取值
        return claim.asString();
    }



}
