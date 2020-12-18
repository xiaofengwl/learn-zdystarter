package com.zdy.mystarter.basic.adapters;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求url拦击器
 * Created by Jason on 2020/1/8.
 * 不依赖于Servlet容器，依赖于Spring，通过反射和动态代理实现，仅对Action有效
 *
 * 实现拦截器的方式：
 * （1）extends HandlerInterceptorAdapter
 * （2）implements HandlerInterceptor
 *
 * 说明：
 *   无论那种实现方式，追着源码进去分析就会发现，最终还是要实现HandlerInterceptor接口
 * 经过测试得知的处理顺序如下：
 *   preHandle-->Controller-->postHandle-->afterCompletion
 */
public class UrlHandlerInterceptorAdapter extends HandlerInterceptorAdapter{

    /**
     * [执行顺序-1]预处理-在请求处理之前进行调用（Controller方法调用之前）
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("UrlHandlerInterceptorAdapter...preHandle...");
        return true;
    }

    /**
     * [执行顺序-2]中处理-在请求处理之后调用（Controller中方法执行之后，生成视图之前）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("UrlHandlerInterceptorAdapter...postHandle...");
    }

    /**
     * [执行顺序-3]后置处理-在DIspatcherServlet完全处理完成请求之后被调用（可用于清理资源）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("UrlHandlerInterceptorAdapter...afterCompletion...");
    }

    /**
     * [执行顺序-暂未可知？？？]此方法非原生接口HandlerInterceptor中定义的，是后来接口继承补充的
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("UrlHandlerInterceptorAdapter...afterConcurrentHandlingStarted...");
    }
}
