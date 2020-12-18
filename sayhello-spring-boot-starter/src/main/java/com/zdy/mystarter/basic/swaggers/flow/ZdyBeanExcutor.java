package com.zdy.mystarter.basic.swaggers.flow;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * TODO 流程执行基础接口
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 17:50
 * @desc
 */
public interface ZdyBeanExcutor {

    /**
     * 服务调度方法
     * @param request
     * @return
     * @throws Exception
     */
    Object callFlow(HttpServletRequest request) throws Exception;

    /**
     * 获取服务接口相关数据
     * @return
     */
    List<Map<String,Object>> getServiceData();


}
