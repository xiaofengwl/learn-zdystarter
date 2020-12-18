package com.zdy.mystarter.controller;

import com.zdy.mystarter.annotations.ParameterAnntation;
import com.zdy.mystarter.model.MessageEntity;
import com.zdy.mystarter.model.common.Person;
import com.zdy.mystarter.tools.BeanToMap;
import com.zdy.mystarter.vo.ParamVo;
import com.zdy.mystarter.vo.annotation.MessageBody;
import com.zdy.mystarter.vo.annotation.MessageVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/17 15:21
 * @desc
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    /**
     * todo 测试1-
     * @param msgVo
     * @return
     */
    @ResponseBody
    @RequestMapping("/1")
    public MessageVo<MessageBody> deal(@ParameterAnntation(type=MessageBody.class)
                                    MessageVo<MessageBody> msgVo){
        ParamVo paramVo=new ParamVo();
        System.out.println(msgVo.toString());
        return msgVo;
    }

    /**
     * todo 测试2-
     */
    @ResponseBody
    @RequestMapping(name="/2")
    public MessageEntity deal2(@RequestBody MessageEntity reVo){
        return reVo;
    }

    /**
     * todo 测试3-
     * @param person
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/convert")
    @ResponseBody
    public  Map<String,Object> converter(@RequestBody Person person) {
        System.out.println(person.toString());

        Map<String,Object> map= BeanToMap.javabeanToMap(person);

        return map;
    }
}
