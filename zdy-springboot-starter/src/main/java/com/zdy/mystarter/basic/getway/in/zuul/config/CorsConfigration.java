/*
package com.zdy.mystarter.basic.getway.in.zuul.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

*/
/**
 * TODO 跨域配置
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/24 16:48
 * @desc
 *//*

@Configuration
public class CorsConfigration {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration config=new CorsConfiguration();
        //#允许向该服务器提交请求的URI，*表示全部允许
        config.addAllowedOrigin(CorsConfiguration.ALL);
        //允许访问的头部信息，*标示全部
        config.addAllowedHeader(CorsConfiguration.ALL);
        //允许提交请求的方法，*标示全部
        config.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("*/
/**",config);
        return new CorsFilter(source);
    }

    */
/**
     * 配置过滤器
     * @return
     *//*

    @Bean
    public FilterRegistrationBean someFilterRegistration(){
        FilterRegistrationBean<CorsFilter> registrationBean=new FilterRegistrationBean<>();
        registrationBean.setFilter(corsFilter());
        registrationBean.addUrlPatterns("*/
/*");
        registrationBean.setName("corsFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
*/
