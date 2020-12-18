package com.zdy.mystarter.basic.wrappers;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;

/**
 * TODO 装饰者增强ServletRequest对象
 *  增强方式：
 *  todo 第一种方式：
 *      implements ServletRequest(所有的方法都要自己来重写，
 *      所以，建议使用第二种方法，也就是通过继承ServletRequestWrapper，
 *      再对自己的需要进行重写相应的方法)
 *
 *  todo 第二种方式：
 *      extends ServletRequestWrapper(已经用装饰者模式帮我们重写所有的方法，
 *      我们只需要重写自己需要重写的方法就可以了，而免去了对其它方法的进行重写的麻烦)
 *
 *
 *
 * Created by Jason on 2020/1/14.
 */
public class ParameterRequestWrapper extends ServletRequestWrapper{

    public ParameterRequestWrapper(ServletRequest request) {
        super(request);
    }

    /**
     * 重写参数处理方法
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }
}
