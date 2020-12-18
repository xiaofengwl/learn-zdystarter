package com.zdy.mystarter.basic.swaggers.service;

import com.zdy.mystarter.basic.swaggers.flow.ZdyBeanExcutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * TODO rest服务抽象层
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 17:36
 * @desc
 */
abstract class AbstractZdyService implements ZdyService{

    private static Logger logger = LoggerFactory.getLogger(AbstractZdyService.class);

    /**
     * todo 核心对象，任务调度对象
     */
    protected ZdyBeanExcutor zdyBeanExcutor;
    public void setZdyBeanExcutor(ZdyBeanExcutor zdyBeanExcutor) {
        this.zdyBeanExcutor = zdyBeanExcutor;
    }

    /**
     * todo 调度流程控制
     * @param request
     * @return
     */
    public ServiceResponse callFlow(HttpServletRequest request) {
        //创建响应对象
        ServiceResponse response = new ServiceResponse();

        try {
            //todo 核心调度流程
            Map o = (Map) zdyBeanExcutor.callFlow(request);
            response.setData(o);
            response.setSuccess();
        } catch (Exception e1) {
            logger.error("", e1);
            response.setError("ERROR-500", e1.getMessage());
        }
        return response;
    }


}
