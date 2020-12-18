package com.zdy.mystarter.basic.swaggers;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * TODO 启动Swagger-API功能
 * <pre>
 *     定制Swagger-UI
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/23 15:17
 * @desc
 */
@Configuration
@EnableSwagger2    //启动swagger2
public class ZDYSwaggerConfig {

    //日志
    private static Logger logger = LoggerFactory.getLogger(ZDYSwaggerConfig.class);

    @Primary
    @Bean
    public Docket zyDocket(){
        logger.info("==[step-0]==========swagger启动：ZDYSwaggerConfig.zyDocket()==============");
        Predicate<RequestHandler> predicate=new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler requestHandler) {
                //只有添加了ApiOperation注解的method才在API中西那是
                if(requestHandler.isAnnotatedWith(ApiOperation.class)){
                    return true;
                }
                return false;
            }
        };
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                   .select()
                   .apis(predicate)
                   .paths(PathSelectors.any())
                   .build();
    }

    /**
     * 定制API文档信息
     * @return
     */
    private ApiInfo apiInfo(){
        logger.info("==[step-0]==========swagger启动：ZDYSwaggerConfig.apiInfo()==============");
        return new ApiInfoBuilder().title("测试ZDY-API文档").build();
    }

}
