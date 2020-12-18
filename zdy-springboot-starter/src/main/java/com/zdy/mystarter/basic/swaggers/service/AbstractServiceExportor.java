package com.zdy.mystarter.basic.swaggers.service;

import com.zdy.mystarter.basic.swaggers.export.SwaggerParameterModel;
import com.zdy.mystarter.basic.swaggers.export.SwaggerZdyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO 抽象层，处理数据转换
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 16:36
 * @desc
 */
public abstract class AbstractServiceExportor implements ZDYServiceExportor{
    //日志
    private static Logger logger = LoggerFactory.getLogger(AbstractServiceExportor.class);

    /**
     * 抽象层中设置，统计数和发布接口容器
     */
    int count = 0;
    private List<SwaggerZdyModel> swaggerModeList = new ArrayList<>();

    /**
     *
     * @return
     */
    abstract String exportType();

    abstract String createAndRegistZdyServiceBean(String serviceId,int i);

    /**
     * todo 向服务中注入数据源，该数据源含义：含有要发布的接口信息的数据集合
     *
     * @param dataList
     */
    @Override
    public void exportZdyService(List<Map<String,Object>> dataList) {
        logger.info("==[step-2]==========AbstractServiceExportor.exportZdyService()======================");
        //处理发布接口数据，装载到接口容器中
        int i=0;
        for (Map<String,Object> mapData:dataList){
            //获取原始数据
            String name=(String)mapData.get("name");
            String id=(String)mapData.get("id");
            String serviceId=(String)mapData.get("serviceId");
            //todo 创建和注册服务Bean,???????
            String action=createAndRegistZdyServiceBean(serviceId,i);
            //开始数据转换
            SwaggerZdyModel swaggerModel = new SwaggerZdyModel();
            swaggerModel.setName(name);
            swaggerModel.setId(id);
            swaggerModel.setPath(action);
            swaggerModel.setParams(covert());
            swaggerModel.setReturnType(getReturnType());

            //装入接口发布容器中
            swaggerModeList.add(swaggerModel);
            i++;
        }
    }

    /**
     * 测试-获取输出参数
     * @return
     */
    private List<SwaggerParameterModel> getReturnType() {
        List<SwaggerParameterModel> list=new ArrayList<>();
        list.add(new SwaggerParameterModel("ec", "结果代码"));
        list.add(new SwaggerParameterModel("em", "错误信息"));
        return list;
    }

    /**
     * 测试-转换输入参数
     * @return
     */
    private List<SwaggerParameterModel> covert() {
        List<SwaggerParameterModel> list=new ArrayList<>();
        //暂定输入为空

        return list;
    }

    /**
     * 对外输出接口容器
     * @return
     */
    public List<SwaggerZdyModel> getSwaggerModeList() {
        logger.info("==[step-5]===========AbstractServiceExportor.getSwaggerModeList()=====================");

        return swaggerModeList;
    }

}
