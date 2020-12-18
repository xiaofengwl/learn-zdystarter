package com.zdy.mystarter.sensitive;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/3/21 16:43
 * @desc
 */
public class UserTest {


    private static User build(){
      User user=new User();
      user.setPhone("18307484872");
      return user;
    }
    /**
     * TODO 测试入口
     * @param args
     */
    public static void main(String[] args)throws Exception{

      /**
       * 测试-1-OK
       *  @Sensitive(strategy = StrategyTelephoneNuber.class)
       */
       User user=build();
       System.out.println("@Sensitive脱敏前的数据"+user);
       User user1=SensitiveUtil.desCopy(user);
       System.out.println("@Sensitive脱敏后的数据"+user1);

      /**
       * 测试-2-
       */
      User user2=build();
      User user3=build();
      User user4=build();
      UserEntity entity=new UserEntity();
      entity.setUser(user2);
      entity.setUserArray(new User[]{user3});
      entity.setUserList(Arrays.asList(user4));
      System.out.println(" @SensitiveEntity脱敏前的数据"+ JSON.toJSONString(entity));
      UserEntity entity1=SensitiveUtil.desCopy(entity);
      System.out.println(" @SensitiveEntity脱敏后的数据"+ JSON.toJSONString(entity1));


    }



}
