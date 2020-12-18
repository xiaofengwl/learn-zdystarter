package com.zdy.mystarter.basic.swaggers.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/24 9:25
 * @desc
 */
public class DefaultZdyBeanExcutor implements ZdyBeanExcutor{
    //日志
    private static Logger logger = LoggerFactory.getLogger(DefaultZdyBeanExcutor.class);

    /**
     * 服务调度方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object callFlow(HttpServletRequest request) throws Exception {
        logger.info("==[step-6]==========DefaultZdyBeanExcutor.callFlow()======================");
        return null;
    }

    /**
     * 获取服务接口相关数据
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getServiceData() {
        return new ArrayList<Map<String, Object>>();
    }
}
