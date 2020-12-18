package com.zdy.mystarter.springtest;

import com.zdy.mystarter.springtest.conf.AppConfig;
import com.zdy.mystarter.springtest.entity.Entitlement;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/7/13 14:36
 * @desc
 */
public class JavaConfigTest {
    public static void main(String[] arg) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        Entitlement ent = (Entitlement)ctx.getBean("entitlement");
        System.out.println(ent.getName());
        System.out.println(ent.getTime());

        Entitlement ent2 = (Entitlement)ctx.getBean("entitlement2");
        System.out.println(ent2.getName());
        System.out.println(ent2.getTime());

        ctx.close();
    }
}
