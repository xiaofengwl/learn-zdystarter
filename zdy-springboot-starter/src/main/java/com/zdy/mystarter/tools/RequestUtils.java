package com.zdy.mystarter.tools;

import com.zdy.mystarter.basic.awares.SpringContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.KeyGenerator;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO 请求工具类
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 10:05
 * @desc
 */
public class RequestUtils {

    private static final String TRIM="--";
    private static final String REAL_IP_KEY="X-Real-IP";

    /**
     * 获取真实IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request){
        String ip=request.getHeader(REAL_IP_KEY);
        if(!StringUtils.isEmpty(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取真实IP
     * @return
     */
    public static String getIpAddress(){
        HttpServletRequest request=null;
        String ip=request.getHeader(REAL_IP_KEY);
        if(!StringUtils.isEmpty(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取HttpServletRequest对象
     * @return
     */
    public static HttpServletRequest getHttpServletRequest(){
        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 获取HttpServletResponse
     * @return
     */
    public static HttpServletResponse getHttpServletResponse(){
        ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return attributes.getResponse();
    }

    /**
     * 获取请求参数
     * @param key
     * @return
     */
    public static String getParameter(String key){
        return getHttpServletRequest().getParameter(key);
    }

    /**
     * 获取头部信息
     * @param key
     * @return
     */
    public static String getHeader(String key){
        return getHttpServletRequest().getHeader(key);
    }

    /**
     * 生成唯一Id
     * @return
     */
    public static String getUniqueSequence(){
        KeyGenerator keyGenerator= SpringContextHolder.getBean(KeyGenerator.class);
        String sessionId=RequestUtils.getHeader("sessionId")==null?"":RequestUtils.getHeader("sessionId")+"-";
        String key=sessionId+keyGenerator.generateKey();
        return key;
    }

    /**
     * 获取Cookie
     * @param name
     * @return
     */
    public static Cookie getCookie(String name){
        Cookie[] cookies=RequestUtils.getHttpServletRequest().getCookies();
        if(StringUtils.isEmpty(cookies)){
            return null;
        }
        for (Cookie cookie:cookies){
            if(name.equals(cookie.getName())){
                return cookie;
            }
        }
        return null;
    }
}
