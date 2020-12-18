package com.zdy.mystarter.basic.wrappers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 增强HttpServletRequest
 * Created by Jason on 2020/1/14.
 */
public class ParameterHttpRequestWrapper extends HttpServletRequestWrapper {

   /**
     * 将HttpServletRequest对象注入
     * @param request
     */
    public ParameterHttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }


}
