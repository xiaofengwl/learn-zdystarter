package com.zdy.mystarter.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zdy.mystarter.basic.adapters.ApplicationContextListenerAdapter;
import com.zdy.mystarter.basic.adapters.UrlHandlerFilterAdapter;
import com.zdy.mystarter.basic.adapters.UrlHandlerInterceptorAdapter;
import com.zdy.mystarter.basic.adapters.UrlSecondHandlerFilterAdapter;
import com.zdy.mystarter.basic.handlers.ReturnValueHandler;
import com.zdy.mystarter.basic.resolvers.ParameterResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 基础配置调整
 * 主要用来重新配置监听器、过滤器、拦截器并注入到IOC容器中
 * Created by Jason on 2020/1/8.
 * 说明：
 *     需要实现WebMvcConfigurer接口，该接口中定义了一些添加视图解析器、异常处理器、资源处理器、监听器、过滤器等方法。
 * 接口内置方法如下：
     public interface WebMvcConfigurer {
         -->configurePathMatch(PathMatchConfigurer configurer) {    }
         -->configureContentNegotiation(ContentNegotiationConfigurer configurer) {    }
         -->configureAsyncSupport(AsyncSupportConfigurer configurer) {    }
         -->configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {    }
         -->addFormatters(FormatterRegistry registry) {   }
         -->addInterceptors(InterceptorRegistry registry) {    }
         -->addResourceHandlers(ResourceHandlerRegistry registry) {    }
         -->addCorsMappings(CorsRegistry registry) {    }
         -->addViewControllers(ViewControllerRegistry registry) {    }
         -->configureViewResolvers(ViewResolverRegistry registry) {    }
         -->addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {    }
         -->addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {    }
         -->configureMessageConverters(List<HttpMessageConverter<?>> converters) {    }
         -->extendMessageConverters(List<HttpMessageConverter<?>> converters) {    }
         -->configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {    }
         -->extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {    }
     }
 */
@Order(1)
@Configuration
public class SpringBasicConfig implements WebMvcConfigurer {

    /**暂停分析-1·线程池配置**/
    /**
     * [暂停分析]-1
     * 线程池配置
     * 设置异步请求默认使用的线程池
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        //configurer.setTaskExecutor(new AsyncConfig());
    }

    /**暂停分析-2·默认的Servlet处理器配置**/
    /**
     * 配置默认的Servlet处理器
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    /**
     * TODO 配置异常处理器-1-添加处理
     * @param resolvers
     * 注意事项：
     *  注意上面方法里面的 configureHandlerExceptionResolvers(exceptionResolvers);
     *  这个方法的实现正好是我们所覆盖的，如果我们添加了异常处理器，那么就不会再添加默认的异常处理器了，而第二种异常处理的方式恰恰是通过默认的异常处理器来完成的，那么如何才能让二者都生效呢
     *  继续看这一行 extendHandlerExceptionResolvers(exceptionResolvers); 这个方法会把我们注册的异常处理器和默认的处理器合并，所以我们需要重写这个方法
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        //todo 加入自定义的异常解析器
        //resolvers.add(new HandlerExceptionResolverAdapter()); //不使用这种方式

    }

    /**
     * TODO 配置异常处理器-2-合并处理
     * @param resolvers
     * 注意事项：
     *     这样就能将默认的异常解析器和自定义的解析器都注册进去了，但是测试的时候你会发现，实现HandlerExceptionResolver 接口的异常处理器不会生效，
     *     这是为何？
     *     原因在于，异常注册器是按顺序执行的，当前一个返回的ModelAndView对象不为null时就会中断后续的异常处理器执行，
     *     要想生效，我们只需在注册时调整注册顺序即可
     *
     *  todo 建议使用此方法注册异常解析器，替代configureHandlerExceptionResolvers方法
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        //todo 异常解析器注册到首位即可
        //resolvers.add(0, new HandlerExceptionResolverAdapter());//不使用这种方式
    }

    /**
     * TODO 配置消息转换器-1
     * 使用阿里 FastJson 作为JSON MessageConverter
     * @param converters
     * Spring Boot底层通过HttpMessageConverters依靠Jackson库将Java实体类输出为JSON格式。
     * 当有多个转换器可用时，根据消息对象类型和需要的内容类型选择最适合的转换器使用。
     *
     * 消息转换器的目标是：
     *     HTTP输入请求格式向Java对象的转换；Java对象向HTTP输出请求的转换。
     *     有的消息转换器只支持多个数据类型，有的只支持多个输出格式，还有的两者兼备。
     *     例如：
     *       MappingJackson2HttpMessageConverter可以将Java对象转换为application/json，
     *       而ProtobufHttpMessageConverter仅支持com.google.protobuf.Message类型的输入，
     *       但是可以输出application/json、application/xml、text/plain和application/x-protobuf这么多格式。
     *
     * 消息转换的模式:
     *
     *         <--------⑤------Java对象 <---④----       <----③----HttpInputMessage<--②----请求报文<--①-----|
     *         |                                 |      |
     *      Spring（服务端）                  HttpMessageConverter                                          客户端
     *         |                                 |      |
     *         ---------⑥----->Java对象 ----⑦--->       -----⑧--->HttpOutputMessage--⑨-->响应报文----⑩----->|
     * 如何配置消息转换器：
     *    （1）在WebConfiguration类中加入@Bean定义。
     *    （2）重写（override）configureMessageConverters方法，扩展现有的消息转换器链表；
     *    （3）更多的控制，可以重写extendMessageConverters方法，首先清空转换器列表，再加入自定义的转换器。
     *
     *  本案例采用方式（2）的模式
     *
     *  须知：
     *       在pom.xml文件中配置了https://mvnrepository.com/artifact/com.alibaba/fastjson依赖，即使我们不做以下案例的配置
     *    也会有默认的消息转换器在执行。
     *
     *  如何选择合适的方式实现消息转换器：
     *    Spring提供了多种方法完成同样的任务，选择哪个取决于我们更侧重便捷性还是更侧重可定制性。
     *    述提到的三种方法各有什么不同呢？
     *  -->1.
     *       通过@Bean定义HttpMessageConverter是向项目中添加消息转换器最简便的办法，这类似于之前提到的添加Servlet Filters。
     *       如果Spring扫描到HttpMessageConverter类型的bean，就会将它自动添加到调用链中。
     *       推荐让项目中的WebConfiguration继承自WebMvcConfigurerAdapter。
     *  -->2.
     *       通过重写configureMessageConverters方法添加自定义的转换器很方便，
     *       但有一个弱点：如果项目中存在多个WebMvcConfigurers的实例（我们自己定义的，或者Spring Boot默认提供的），不能确保重写后的configureMessageConverters方法按照固定顺序执行。
     *  -->3.
     *       如果需要更精细的控制：清除其他消息转换器或者清楚重复的转换器，可以通过重写extendMessageConverters完成，
     *       仍然有这种可能：别的WebMvcConfigurer实例也可以重写这个方法，但是这种几率非常小。
     *
     *
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        System.out.println("SpringBasicConfig...configureMessageConverters...");
        //TODO 阿里的消息转换器
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //TODO 定制MIME类型
        List<MediaType> supportMediaTypeList = new ArrayList<>();
        supportMediaTypeList.add(MediaType.TEXT_HTML);
        supportMediaTypeList.add(MediaType.IMAGE_GIF);
        supportMediaTypeList.add(MediaType.IMAGE_JPEG);
        supportMediaTypeList.add(MediaType.IMAGE_PNG);
        supportMediaTypeList.add(MediaType.TEXT_PLAIN);
        //....
        //TODO 定制消息配置
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,        //保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,   //String null -> ""
                SerializerFeature.WriteNullNumberAsZero,    //Number null -> 0
                SerializerFeature.WriteNullListAsEmpty,     //List null-> []
                SerializerFeature.WriteNullBooleanAsFalse); //Boolean null -> false
        //TODO 装配转换器
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(supportMediaTypeList);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        //TODO 注册转换器到容器中
        converters.add(converter);
        /**
         * 添加自定义的消息转换器,至于转换器队列的首位。
         * 支持：application/xml格式，
         * 测试途径：postman发送post请求，选择application/xml格式的数据
         */
        //ESBHttpMessageConverter esbHttpMessageConverter=new ESBHttpMessageConverter();
        //converters.add(0,esbHttpMessageConverter);
    }
    /**
     * TODO 扩展消息转换器队列，比如为队列中的消息转换器排序
     * 如果想要对消息转换器的队列做操作，可以使用这种方式
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //先清空
        //converters.clear();
        //converters.add(0,new ZDYHttpMessageConverter());
        System.out.println("——————————消息转换器扩展-初始化配置———开始———————");
        for (HttpMessageConverter httpMessageConverter:converters){
            System.out.println("初始化配置："+httpMessageConverter);
        }
        System.out.println("——————————消息转换器扩展-初始化配置———结束———————");
    }
/********************--配置返回值处理器--************************************************************/
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    /**
     * TODO 自定义的返回值处理器
     */
    @Autowired
    private ReturnValueHandler returnValueHandler;

    /**
     * TODO 切面处理
     */
    @PostConstruct
    public void init() {
        final List<HandlerMethodReturnValueHandler> newHandlers = new LinkedList<>();
        final List<HandlerMethodReturnValueHandler> originalHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        if (null != originalHandlers) {
            newHandlers.addAll(originalHandlers);
            // 获取处理器应处于的位置，需要在RequestResponseBodyMethodProcessor之前
            /**
             * TODO 说明：
             *    1、Spring提供的返回值处理器条件是：如果supportsReturnType返回true，则返回值将交由此类进行处理。
             *    2、RequestResponseBodyMethodProcessor这个类中的返回值处理函数handleReturnValue在处理之后将直接返回，不再交由下一处理器处理，源码如下。故我们的自定义处理器需要排在此处理器之前。
             */
            final int index = obtainValueHandlerPosition(originalHandlers, RequestResponseBodyMethodProcessor.class);
            newHandlers.add(index, returnValueHandler);
        } else {
            newHandlers.add(returnValueHandler);
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);
    }
    /**
     * 获取让自定义处理器生效应处于的位置
     *
     * @param originalHandlers 已经添加的返回值处理器
     * @param handlerClass     返回值处理器的类型
     * @return 自定义处理器需要的位置
     */
    private int obtainValueHandlerPosition(final List<HandlerMethodReturnValueHandler> originalHandlers, Class<?> handlerClass) {
        for (int i = 0; i < originalHandlers.size(); i++) {
            final HandlerMethodReturnValueHandler valueHandler = originalHandlers.get(i);
            if (handlerClass.isAssignableFrom(valueHandler.getClass())) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 经过大量测试：下面的重写方法-无效且不再使用，仅供参考
     * 配置返回值处理器---【执行无效-原因待分析】
     * 业务场景：
     *    （1）由于某些时候需要对controller的返回对象作统一的封装，
     *        例如一个业务系统中统一的返回格式。这里可以使用到ReturnValueHandler，
     *        当然也可以使用ResponseBody、Convertor或者View等。
     *    （2）想在 SpringBoot 的项目中添加一个返回值处理器来处理有特定注解的请求方法。
     * @param handlers
     * 异常现象：
     *    （1）在配置和使用都正常的情况下，服务启动正常，代码运行正常，就是配置不生效！！！(笑哭)
     *
     * 处理方案：
     *  问题的根源在于自定义的处理器会排在最后面，所以只有默认的处理器全部失败才能轮到自己的,
     *  默认的ResponseBody处理类就是HandlerMethodReturnValueHandler，此处理器的优先级比我们新添加的实现类高，
     *  在WebMvcConfigurerAdapter 中配置HandlerMethodReturnValueHandler，代码其实是并不生效。
     *  如果你希望添加额外的返回值处理器，需要做的是改造默认的处理类（如果直接替换默认的处理类，但是原先的ResponseBody的处理逻辑将出错）。
     *
     *  解决思路： 1. 不要标记 @ResponseBody 和 @ RestController
     *           2. 利用反射把自己的处理器加到最前面（要依赖实现很不优雅）
     *           3. 使用AOP的方式---本案例采用的是AOP切面方式
     *
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        System.out.println("SpringBasicConfig...addReturnValueHandlers...");
        //不做任何处理......
    }
/********************************************************************************/


    /**
     * 配置参数解析器
     * 使用范围和处理逻辑：
     *    可以根据业务需求自定义一个参数注解和一个参数解析器（专门处理该参数注解），最后将该参数解析器添加到注册器中
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        System.out.println("SpringBasicConfig...addArgumentResolvers...");
        //注册-解析器
        resolvers.add(new ParameterResolver());
    }

    /**
     * 配置视图解析器
     * 如果对视图解析器的代码原理熟悉，可以自定义一个视图解析器
     * @param registry
     * 请求处理方法执行完成后，最终返回一个ModelAndView 对象。对于那些返回String，View 或ModeMap 等类型的处理方法，Spring MVC 也会在内部将它们装配成一个ModelAndView 对象，它包含了逻辑名和模型对象的视图；
     *
     * 说明：
     *     个人觉得，以后可能需要配置视图解析器的场景会越来越少了。目前市场上较流行的做法是前后端分离。
     *  以后客户端采用异步的方式向服务端发送求，服务不需要返回视图（*.html、*.jsp、*.js、*.css...等等静态资源给客户端）
     *  只需要根据客户端的MIME类型（Accept：***）做出对应的响应即可！
     *
     * 扩展说明：
     *     视图解析器、视图对象、都是可以自定义的。按需处理吧！！！
     *
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        /**
         * [1]、配置InternalResourceViewResolver视图解析器
         * Spring框架内置实现的一种视图解析器，注：视图解析器不止一种。
         */
        InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);  //将视图解析器加入到视图解析器的注册器中
        /**
         * [2] 添加自定义的视图解析器
         * 调整viewname和视图资源的绑定和处理规则
         */
        /*CommonResourceViewResolver commonViewResolver=new CommonResourceViewResolver();
        commonViewResolver.setPrefix("/templates/");
        commonViewResolver.setSuffix(".html");
        registry.viewResolver(commonViewResolver);*/
    }

    /**
     * 类型转换配置
     * 概念描述：
     *      考虑到PropertyEditor的无状态和非线程安全特性，Spring 3增加了一个Formatter接口来替代它。
     *      Formatters提供和PropertyEditor类似的功能，但是提供线程安全特性，
     *      并且专注于完成字符串和对象类型的互相转换。
     *
     *  使用范畴：
     *      如果需要通用类型的转换--例如String和boolean,最好使用PropertyEditor完成，
     *      因为这种需求可能不是全局需要的，只是某个Controller的定制功能需求。
     *
     *  参考网址：
     *      https://www.jianshu.com/p/f09052e185de
     * @param registry
     *
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    /**
     * 请求参数协商机制的开关（默认关闭）
     * 配置内容协商机制(此种方式默认关闭，需要手动启用才会生效)
     * @param configurer
     * （1）应用情景：
     *    不知你在使用Spring Boot时是否对这样一个现象"诧异"过：同一个接口（同一个URL）在接口报错情况下，
     *    若你用rest访问，它返回给你的是一个json串；但若你用浏览器访问，它返回给你的是一段html。
     * （2）原因：
     *    RESTful服务中很重要的一个特性是：
     *    同一资源可以有多种表述，这就是我们今天文章的主题：内容协商(ContentNegotiation)。
     * （3）什么是HTTP内容协商？
     *   1.定义：
     *     一个URL资源服务端可以以多种形式进行响应：即MIME（MediaType）媒体类型。但对于某一个客户端（浏览器、
     *     APP、Excel导出…）来说它只需要一种。so这样客户端和服务端就得有一种机制来保证这个事情，这种机制就是内容协商机制。
     *   2.方式：
     *      .....
     *      本方法支持-请求参数
     *      .....
     * （4）请求参数协商方式的优缺点？
     *      优点：不受浏览器约束
     *      缺点：需要额外的传递format参数，URL变得冗余繁琐，缺少了REST的简洁风范。还有个缺点便是：还需手动显示开启。
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // 支持请求参数协商
        /**
         * 例如：
         *     正常url:
         *          http://localhost:8080/hello/cros
         *     请求参数协商-1：
         *          http://localhost:8080/hello/cros?format=xml     要求返回xml格式
         *     请求参数协商-2：
         *          http://localhost:8080/hello/cros?format=json    要求返回json格式
         */
         /* 是否通过请求Url的扩展名来决定media type */
         /* configurer.favorPathExtension(true)
                    *//* 不检查Accept请求头 *//*
                    .ignoreAcceptHeader(true)
                    .parameterName("mediaType")
                    *//* 设置默认的media type *//*
                    .defaultContentType(MediaType.TEXT_HTML)
                    *//* 请求以.html结尾的会被当成MediaType.TEXT_HTML*//*
                    .mediaType("html", MediaType.TEXT_HTML)
                    *//* 请求以.json结尾的会被当成MediaType.APPLICATION_JSON*//*
                    .mediaType("json", MediaType.APPLICATION_JSON);*/
    }

    /**
     * 定义自己的path匹配规则--调整Spring的路由规则
     * @param configurer
     * 1、 extends WebMvcConfigurationSupport (已过时)
     *     implements WebMvcConfigurer（目前使用方式）
     * 2、重写下面方法;
     *    setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真-true 即匹配；
     *    setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真-true  即匹配；
     *
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        System.out.println("SpringBasicConfig...configurePathMatch...");
        /**
         * 说明：
         *    http://localhost:8080/hello/t4/1.a
         *    http://localhost:8080/hello/t4/1/
         *  等效于：
         *    正常请求格式： http://localhost:8080/hello/t4/1
         */
        configurer.setUseTrailingSlashMatch(true)
                  .setUseSuffixPatternMatch(true); //设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真-true 即匹配；
    }

    /**
     * 添加跨域访问配置
     * 介绍-CORS
     *    CORS（Cross-Origin Resource Sharing）“跨域资源共享”，是一个W3C标准，
     *    它允许浏览器向跨域服务器发送Ajax请求，打破了Ajax只能访问本站内的资源限制
     *    Access-Control-Allow-Origin字段是html5新增的一项标准功能，因此 IE10以下版本的浏览器是不支持的，
     *    因此，如果要求兼容IE9或更低版本的ie浏览器，会导致使用此种方式的跨域请求以及传递Cookie的计划夭折
     * 什么是”跨域“：
     *    跨域指的是浏览器不能执行其他网站的脚本。它是由浏览器的同源策略造成的，是浏览器施加的安全限制。
     * @param registry
     * 使用格式说明：
     *       .addMapping("/**")         配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
     *       .allowedMethods("*")       允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
     *       .allowedOrigins("*")       允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
     *       .exposedHeaders("*");      允许所有的请求header访问，可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN"
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("SpringBasicConfig...addCorsMappings...");
       /* registry.addMapping("**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .exposedHeaders("*");*/
    }

    /**
     * 配置视图资源解析器--指定映射的匹配规则
     * @return
     * 说明：
     *（1）SpringBoot的静态资源可以存放的默认路径：
     *      在src/main/resources目录下新建 public、resources、static 、META-INF等目录
     *（2）也可以修改配置文件，自定义指定静态资源路径
     *      spring.resources.static-locations=**********
     */
    @Bean
    @ConditionalOnMissingBean
    public InternalResourceViewResolver defaultViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        //视图解析器拿到的视图文件名称的前后缀补充
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        resolver.setOrder(0);
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    /**
     * 添加视图控制器--指定获取请求的预处理匹配规则
     * addViewControllers可以方便的实现一个请求直接映射成视图，而无需书写controller
     * registry.addViewController("请求路径").setViewName("请求页面文件路径")
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        System.out.println("SpringBasicConfig...addViewControllers...");
        //1.为指定请求设置对应的视图资源
        /**
         *
         */
        registry.addViewController("/exs/404").setViewName("exceptions/404");
        registry.addViewController("/exs/500").setViewName("exceptions/500");
        //2.为收到的指定请求设置重定向请求地址,**如果匹配不到则会进入重定向地址
        /**
         * 现状：
         *      已经配置了请求：/hello/ex/1
         * 使用格式：
         *  （1）/hello/ex/**  模糊匹配
         *      所有满足/hello/ex/**格式的请求，如果不存在，则会执行 addViewControllers，跳转到/exception/ex/1。
         *                                   如果已经存在，则只会执行Controller中的设置。
         *  （2）/hello/ex/1   精准匹配
         *      如果/hello/ex/1已经在Controller中设置了，则不会执行 addViewControllers，只会执行Controller中的设置。
         *      如果/hello/ex/1未设置，则会执行 addViewControllers，跳转到/exception/ex/1
         */
        registry.addRedirectViewController("/exceptions/hello_ex/*","/exception/ex/1")
                .setKeepQueryParams(true);
        registry.addRedirectViewController("/exceptions/404_ex/*","/exs/404")
                .setKeepQueryParams(true);
        registry.addRedirectViewController("/exceptions/500_ex/*","/exs/500")
                .setKeepQueryParams(true);
        registry.addRedirectViewController("/download/file/*","/file/1")
                .setKeepQueryParams(true);
    }

    /**
     * 添加静态访问资源的映射配置
     * @param registry
     * 说明：
     * 使用场景：
     *      图片上传预览
     * 如何使用：
     *     通过 .addResourceHandler("设置暴露在外的访问地址-文件夹路径")
     *         .addResourceLocations("设置真实的资源所在地址-文件夹路径")
     *     例如：
     *        registry.addResourceHandler("/file/**")  对外暴露出去的虚假路径
     *                 .addResourceLocations("file:E:/常用工具/")  和虚假路径映射的真实路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("SpringBasicConfig...addResourceHandlers...");
        /**
         * addResoureHandler指的是对外暴露的访问路径
         * addResourceLocations指的是文件放置的目录
         */
        //映射-1
        registry.addResourceHandler("/file/common/**")
                .addResourceLocations("file:E:/常用工具/","file:E:/yusys/"); //一次性映射多个真实地址
        //映射-2
        registry.addResourceHandler("/file/bgs/**")
                .addResourceLocations("file:E:/yusys/");
    }

    /**
     * 添加拦截器
     * @param registry 拦截器的注册器
     *   支持链式操作，可以为每一种的url请求设置专门的拦截器做处理。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("SpringBasicConfig...addInterceptors...");
        //支持链式添加
        registry.addInterceptor(new UrlHandlerInterceptorAdapter())
                .addPathPatterns("/hello/**")                       //要拦截的url
                .excludePathPatterns("toLogin","/login");           //放行的url
    }

    /**
     * 将过滤器注入到IOC容器，因为默认工程中是没有过滤器的，自定义的过滤器需要我们手动编码和注入。
     * （须知悉：SpringBoot依赖的启动器中可能会注入一些过滤器）
     * 通过setOrder(0),设置过滤器执行顺序，亲测有效
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean<UrlHandlerFilterAdapter> getUrlHandlerFIlterAdapter(){
        System.out.println("SpringBasicConfig...getUrlHandlerFIlterAdapter...");
        FilterRegistrationBean filterBean=new FilterRegistrationBean();
        filterBean.setFilter(new UrlHandlerFilterAdapter());
        filterBean.setName("UrlHandlerFilterAdapter");
        filterBean.addInitParameter("UrlHandlerFilterAdapter","UrlHandlerFilterAdapter");
        filterBean.addUrlPatterns("/hello/*");
        filterBean.setOrder(0);                 //设置为第一个过滤器、亲测有效
        //....
        return filterBean;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean<UrlSecondHandlerFilterAdapter> getUrlSecondHandlerFIlterAdapter(){
        System.out.println("SpringBasicConfig...getUrlSecondHandlerFIlterAdapter...");
        FilterRegistrationBean filterBean=new FilterRegistrationBean();
        filterBean.setFilter(new UrlSecondHandlerFilterAdapter());
        filterBean.setName("UrlSecondHandlerFilterAdapter");
        filterBean.addInitParameter("UrlSecondHandlerFilterAdapter","UrlSecondHandlerFilterAdapter");
        filterBean.addUrlPatterns("/hello/*");
        filterBean.setOrder(1);                 //设置为第二个过滤器、亲测有效
        //....
        return filterBean;
    }

    /**
     * 将监听器注入到IOC容器中
     */
    @Bean
    public ServletListenerRegistrationBean<ApplicationContextListenerAdapter> getServletListenerRegistrationBean(){
        System.out.println("SpringBasicConfig...getServletListenerRegistrationBean...");
        ServletListenerRegistrationBean srb=new ServletListenerRegistrationBean();
        srb.setListener(new ApplicationContextListenerAdapter());
        srb.setOrder(0);
        return srb;
    }

}
