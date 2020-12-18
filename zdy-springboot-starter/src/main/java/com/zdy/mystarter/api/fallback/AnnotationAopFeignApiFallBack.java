package com.zdy.mystarter.api.fallback;

import com.zdy.mystarter.api.AnnotationAopFeignApi;
import org.springframework.stereotype.Component;

/**
 * TODO 添加主题
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/5/6 10:31
 * @desc
 */
@Component
public class AnnotationAopFeignApiFallBack implements AnnotationAopFeignApi {

    public void request(){
        System.out.println("AnnotationAopFeignApiFallBack....123.....");
    }

}
