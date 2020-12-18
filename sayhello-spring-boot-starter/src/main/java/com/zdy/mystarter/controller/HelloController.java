package com.zdy.mystarter.controller;

import com.zdy.mystarter.annotations.ExtendRequestMapping;
import com.zdy.mystarter.annotations.HelloAnnotation;
import com.zdy.mystarter.annotations.ResultBeanResponseBody;
import com.zdy.mystarter.basic.exceptions.BusinessException;
import com.zdy.mystarter.config.HelloService;
import com.zdy.mystarter.vo.BusinessResult;
import com.zdy.mystarter.vo.ParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2020/1/7.
 * @Controller
 *     下面支持 return "redirect:/**" 的用法
 * @RestController
 *     因为内置了@ResponseBody注解，会导致return "redirect:/**" 的用法失效。
 * @responseBody
 *      _@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    //自定义的启动器被依赖成功
    //直接注入使用启动器已经加载到容器里面的HelloService 对象
    @Autowired
    HelloService helloService;

    @HelloAnnotation
    @RequestMapping("/login")
    @ResponseBody
    public String login(){
        System.out.println(helloService);
        return helloService.sayHellZdy("科比");
    }

    /**
     * 测试注解@PathVariable的使用方法,不可以隐射实体类
     * 用参数接收（请求参数在url上拼接）
     * @param param
     * @return
     */
    @RequestMapping("/t1/{p1}")
    @ResponseBody
    public String paramPathVariableAnnotataion(@PathVariable("p1") String param){
        return "获取参数："+param;
    }

    /**
     * 用参数接收（请求参数在BODY）
     * @param param
     * @return
     */
    @RequestMapping("/t2/1")
    @ResponseBody
    public String paramRequestParamAnnotataion2(@RequestParam("p1") String param){
        return "获取参数："+param;
    }

    /**
     * 用实体类接收参数--此种方式不支持转换
     * 异常记录：
     *    Failed to convert value of type 'java.lang.String' to required type 'com.zdy.mystarter.vo.ParamVo
     * 处理方式：
     *    更换注解类型,更换注解类型或者参数类型
     * @param po
     * @return
     */
    @RequestMapping("/t3/1")
    @ResponseBody
    public String paramRequestParamAnnotataion3(@RequestParam("p1") ParamVo po){
        return "获取参数："+po.getP1();
    }

    /**
     * RequestMapping方式：自动识别请求方式
     * 异常记录：
     * Content type 'multipart/form-data;boundary=----------525412376929272489613602;charset=UTF-8' not supported
     * 解决方式：
     *    使用JSON格式提交，即OK
     * @param po
     * @return
     */
    @RequestMapping("/t3/2")
    @ResponseBody
    public String paramRequestParamAnnotataion4(@RequestBody ParamVo po){
        return "获取参数："+po.getP1();
    }

    /**
     * PostMapping请求方式-POST
     * 用实体类接收参数--必须要做封装转换，
     * 异常记录：
     *    前端参数：{"p1":"hahah"}  后端参数:ParamVo po 无法接收封装
     * 处理方式：
     *    后端使用注解处理：@RequestBody ParamVo po
     * @param po
     * @return
     */
    @PostMapping("/t3/3")
    @ResponseBody
    public String paramRequestParamAnnotataion5(@RequestBody ParamVo po){
        return "获取参数："+po.getP1();
    }

    /**
     * PostMapping请求方式-GET
     * 用实体类接收参数--必须要做封装转换
     * 异常记录：
     *    前端参数：{"p1":"hahah"}  后端参数:ParamVo po 无法接收封装
     *    异常信息如下：
     *    message": "Required request body is missing: public java.lang.String com.zdy.mystarter.controller.HelloController.paramRequestParamAnnotataion6(com.zdy.mystarter.vo.ParamVo)
     * 处理方式：
     * （1）增加@RequestBody注解修饰 ，测试得知：GET方式提交的
     *     得出结论：
     *     @GetMapping 不支持@RequestBody
     * （2）修改@GetMapping为@PostMapping
     * @param po
     * @return
     */
    @PostMapping("/t3/4")
    @ResponseBody
    public String paramRequestParamAnnotataion6(@RequestBody ParamVo po){
        return "获取参数："+po.getP1();
    }

    /**
     * 无论是否使用@RequestParam，
     *     String p1和 @RequestParam String p1  这2中方式在接收前端参数：｛"p1":"hahah"｝或则 ..?p1=hahah
     *  的效果是一样的，均可接收到参数值
     * @param p1
     * @return
     */
    //@GetMapping("/t3/5")
    @ExtendRequestMapping("/t3/5")         //测试自定义注解，继承元注解规则
    @ResponseBody
    public String paramRequestParamAnnotataion7(String p1){
        System.out.println("execute controller method paramRequestParamAnnotataion7 on url:/hello/t3/5");
        return "获取参数："+p1;
    }

    /**
     * 测试：redirect-重定向
     * @param p1
     * @return
     * 说明：
     *   return "redirect:/**"的用法受到@ResponseBody注解影响会不生效。
     *   重定向，浏览器的url地址栏会看到变化
     */
    @ExtendRequestMapping("/t4/1")      //测试自定义注解，继承元注解规则
    public String paramRequestParamAnnotataion8(String p1){
        System.out.println("execute controller method paramRequestParamAnnotataion8 on url:/hello/t4/1");
        System.out.println("获取参数："+p1);
        return "redirect:/exceptions/hello_ex/1";
    }

    /**
     * 测试：forward-转发
     * @param p1
     * @return
     * 说明：
     *   return "forward:/**"的用法受到@ResponseBody注解影响会不生效。
     *   转发，浏览器的url地址栏不会看到变化
     * 注意：
     *   但是如果转发url_1会后在出现重定向，那么浏览器上也是可以看到地址变化的
     */
    @ExtendRequestMapping("/t4/2")      //测试自定义注解，继承元注解规则
    public String paramRequestParamAnnotxataion9(String p1){
        System.out.println("execute controller method paramRequestParamAnnotxataion9 on url:/hello/t4/2");
        System.out.println("获取参数："+p1);
        return "forward:/exceptions/hello_ex/2";
    }

    /**
     * CORS跨域请求-测试
     * @param p1
     * @return
     * 如何测试：
     *     前提准备：
     *          已知本地机器地址：169.254.74.218
     *          已知本工程tomcat服务启动占用端口号：8080
     *          addCorsMappings()方法中已经配置完成。
     *          存在一个前端操作请求：169.254.74.218:8080/hello/cros
     *     测试步骤：
     *          本地启动tomcat服务,169.254.74.218:8080
     *          在同一个局域网内另外一台电脑访问：169.254.74.218:8080/hello/cros
     *     预测结果：
     *          如果addCorsMappings()方法中已经配置完成,则另外一台电脑会出现："Hello this is a cros test！"提示
     *          否则，浏览器控制台console会报错类似于："Failed to load 169.254.74.218:8080/hello/cros, No Access-Control-Allow-Origein header is present on the requested resource....."
     *
     */
    @ExtendRequestMapping(value="/cros")      //测试自定义注解，继承元注解规则
    @ResponseBody
    public String paramRequestParamAnnotxataion10(String p1){
        System.out.println("execute controller method paramRequestParamAnnotxataion10 on url:/hello/cros");
        System.out.println("获取参数："+p1);
        return "Hello this is a cros test！";
    }

    /**
     * SpringMvc的内容协商机制·固定类型（produces）
     * 即在@RequestMapping的属性：produces= {MediaType.APPLICATION_JSON_VALUE}指定类型，支持多个指定
     * @param p1
     * @return
     */
    @RequestMapping(value="/ctr/1",produces= {MediaType.TEXT_HTML_VALUE})      //测试自定义注解，继承元注解规则
    @ResponseBody
    public String contTypeRequestDemo1(String p1){
        System.out.println("execute controller method contTypeRequestDemo1 on url:/ctr/1");
        System.out.println("获取参数："+p1);
        return "Hello this is a ctr test！";
    }

    /**
     * 测试-配置参数解析器
     * @param p1
     * @return
     */
    @ExtendRequestMapping(value="/par/1")
    @ResponseBody
    public String parameterAnnotationResolverDemo1( String p1){
        System.out.println("execute controller method parameterAnnotationResolverDemo1 on url:/par/1");
        return "获得参数："+p1;
    }

    /**
     * 测试-配置返回值处理器-1
     * @param p1
     * @return
     */
    @RequestMapping(value="/ret/1")
    //@ResponseBody           //响应数据：获得参数：123
    @ResultBeanResponseBody   //响应数据：{"code":"200","datas":"获得参数：123","message":"success"}
    public String retAnnotationTestDemo1(String p1){
        System.out.println("execute controller method retAnnotationTestDemo1 on url:/ret/1");
        return "获得参数："+p1;
    }

    /**
     * 测试-配置返回值处理器-2
     * @param p1
     * @return
     */
    @RequestMapping(value="/ret/2")
    //@ResponseBody           //响应数据：获得参数：123
    @ResultBeanResponseBody   //响应数据：{"code":"200","datas":{"p1":"123","p2":"p2-value"},"message":"success"}
    public ParamVo retAnnotationTestDemo2(String p1){
        System.out.println("execute controller method retAnnotationTestDemo2 on url:/ret/2");
        ParamVo paramVo=new ParamVo();
        paramVo.setP1(p1);
        paramVo.setP2("p2-value");
        return paramVo;
    }

    /**
     * 测试-配置返回值处理器-3
     * @param p1
     * @return
     */
    @RequestMapping(value="/ret/3")
    //@ResponseBody           //响应数据：获得参数：123
    @ResultBeanResponseBody   //响应数据：{"code":"200","datas":[{"p1":"123","p2":"p2-value1"},{"p1":"123","p2":"p2-value2"}],"message":"success"}
    public List<ParamVo> retAnnotationTestDemo3(String p1){
        System.out.println("execute controller method retAnnotationTestDemo2 on url:/ret/2");
        ParamVo paramVo=new ParamVo();
        paramVo.setP1(p1);
        paramVo.setP2("p2-value1");
        ParamVo paramVo2=new ParamVo();
        paramVo2.setP1(p1);
        paramVo2.setP2("p2-value2");
        List<ParamVo> retList=new ArrayList<>();
        retList.add(paramVo);
        retList.add(paramVo2);
        return retList;
    }

    /**
     * 测试全局的异常处理类
     * @param p1
     * @return
     */
    @RequestMapping(value="/exception/1")
    @ResultBeanResponseBody
    public ParamVo exceptionHandlerTest1(String p1){
        System.out.println("execute controller method exceptionHandlerTest1 on url:/exception/1");
        ParamVo paramVo=new ParamVo();
        paramVo.setP1(p1);
        paramVo.setP2("p2-value1");
        System.out.println();
        if(Integer.valueOf(p1)!=1) {
            throw new BusinessException(BusinessResult.create(601L,"系统异常，请稍后重试。"));
        }
        return paramVo;
    }

}
