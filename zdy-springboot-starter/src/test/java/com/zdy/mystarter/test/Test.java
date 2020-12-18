package com.zdy.mystarter.test;

import com.alibaba.fastjson.JSON;
import com.zdy.mystarter.entity.EntitiyVO;
import com.zdy.mystarter.entity.EntityDTO;

import java.lang.reflect.Array;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/4/27 9:34
 * @desc
 */
public class Test {

  public static void main(String[] args){


      EntityDTO dto=new EntityDTO();
      dto.setDesc("123");
      dto.setName("xfwl");

      EntitiyVO p=(EntitiyVO)dto;
      System.out.println(JSON.toJSONString(p));


  }

}
