package com.zdy.mystarter.basic.swaggers.provider;

import com.zdy.mystarter.basic.swaggers.export.SwaggerZdyModel;
import com.zdy.mystarter.basic.swaggers.service.RestZdyServiceExportor;
import io.swagger.models.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelProvider;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.OperationModelContextsBuilder;

import java.util.*;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 18:43
 * @desc
 */
@Component
@ConditionalOnProperty(name = "zdy.swagger.flow.swtich",havingValue = "true",matchIfMissing = false)
public class SwaggerZdyApiProvider implements ApiListingScannerPlugin {

    Logger logger= LoggerFactory.getLogger(SwaggerZdyApiProvider.class);
    /**
     * 装配rest接口发布服务对象
     */
    @Autowired
    private RestZdyServiceExportor restZdyServiceExportor;

    @Autowired
    @Qualifier("default")
    private ModelProvider modelProvider;


    /**
     * 发布入口，调用为Swagger2
     * @param documentationContext
     * @return
     */
    @Override
    public List<ApiDescription> apply(DocumentationContext documentationContext) {
        List<ApiDescription> apiDescriptions=new ArrayList<>();
        logger.info("==[step-0]==========准备发布接口======================");
        logger.info("==[step-1]==========SwaggerZdyApiProvider.apply()准备发布接口======================");
        //待接口信息数据准备
        List<Map<String,Object>> dataList=new ArrayList<>();
        for(int i=0;i<1;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("name","swagger测试");
            map.put("id","testflow");
            map.put("serviceId","swagger/test");
            dataList.add(map);
        }

        //原始数据-装载要发布的接口集合
        restZdyServiceExportor.exportZdyService(dataList);

        //获取转换后的待发布集合
        List<SwaggerZdyModel> modelList=restZdyServiceExportor.getSwaggerModeList();
        int index=0;
        for (SwaggerZdyModel model:modelList){

            //发布准备
            String param_name="param_"+index+"_"+index;         //参数name
            String ret_name="ret_"+index+"_"+index;             //返回参数name
            List<Class> paramList=new ArrayList<>();            //参数数据列表
            List<Class> retList=new ArrayList<>();              //返回参数列表

            /**
             * todo 以下的操作不太理解
             */
            Class paramClass=ClassUtil.createRefMoodel(model.getParams(),param_name,model.getId(),paramList );
            Class retClass  =ClassUtil.createRefMoodel(model.getReturnType(),ret_name,model.getId(),retList );

            obtainModel(documentationContext,paramList,retList);

            //组装的单个Api描述
            ApiDescription apiDescription=resolveApiDescription(documentationContext,model.getPath(),paramClass,retClass,model.getName());

            index++;
        }
        logger.info("==[step-6]==========发布接口·暂未完成，缺失对Springfox包中的源码理解，很多功能不太会用！需要抽空继续研究！！！！======================");
        return apiDescriptions;
    }

    /**
     * 核心方法-处理Api的描述
     * @param documentationContext
     * @param path
     * @param paramsClazz
     * @param returnValType
     * @param summary
     * @return
     */
    private ApiDescription resolveApiDescription(DocumentationContext documentationContext,
                                                 String path,
                                                 Class paramsClazz,
                                                 Class returnValType,
                                                 String summary) {



        return null;
    }

    /**
     *
     * @param documentationContext
     * @param paramList
     * @param returnType
     */
    private void obtainModel(DocumentationContext documentationContext,
                             List<Class> paramList,
                             List<Class> returnType) {

        //创建builder
        OperationModelContextsBuilder builder=new OperationModelContextsBuilder(documentationContext.getGroupName(),
                documentationContext.getDocumentationType(),documentationContext.getAlternateTypeProvider(),
                documentationContext.getGenericsNamingStrategy(),documentationContext.getIgnorableParameterTypes());

        //创建输入参数
        for (Class c:paramList){
            builder.addInputParam(c);
        }

        //如果返回值不为void类型
        if(!void.class.equals(returnType)){
            for (Class c:returnType){
                builder.addReturn(c);
            }
        }

        //
        final Set<ModelContext> modelContexts=builder.build();
        if (modelContexts != null && !modelContexts.isEmpty()) {
            Map<String, Model> models=null;

        }
    }

    /**
     *
     * @param documentationType
     * @return
     */
    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }


}
