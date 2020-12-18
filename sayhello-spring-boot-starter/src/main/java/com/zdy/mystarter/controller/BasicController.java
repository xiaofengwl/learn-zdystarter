package com.zdy.mystarter.controller;

import com.alibaba.fastjson.JSON;
import com.zdy.mystarter.annotations.ResultBeanResponseBody;
import com.zdy.mystarter.basic.exceptions.BusinessException;
import com.zdy.mystarter.facade.basic.IBasicFacade;
import com.zdy.mystarter.intergration.mapper.basic.BasicMapper;
import com.zdy.mystarter.model.basic.CourseVo;
import com.zdy.mystarter.model.basic.RoleVo;
import com.zdy.mystarter.model.basic.StudentVo;
import com.zdy.mystarter.model.basic.TeacherVo;
import com.zdy.mystarter.vo.BusinessResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jason on 2020/1/13.
 */
@Controller
@RequestMapping("/basic")
public class BasicController {

    /**
     * 装配mapper--测试联合查询
     */
    @Resource
    BasicMapper basicMapper;

    /**
     * 装备service--测试事务管理
     */
    @Resource
    IBasicFacade basicFacade;

    /**
     * mybatis连通性测试-1
     * @param p1
     * @return
     */
    @RequestMapping("/st/{url}")
    @ResultBeanResponseBody
    public List<StudentVo> qryAllStudentVoList(@PathVariable("url") String urlParam,
                                               @RequestParam("p1") String p1){
        System.out.println("BasicController...qryAllStudentVoList..");
        StudentVo studentVo=new StudentVo();
        studentVo.setStId(p1);
        List<StudentVo> stList=null;
        if(!StringUtils.isEmpty(urlParam)){
            switch(urlParam){
                case "1":stList=basicMapper.qryAllStudentVoList(studentVo);break;
                case "2":stList=basicMapper.qryAllStudentVoList2(studentVo);break;

                default:break;
            }
        }else{
            stList= Collections.emptyList();
        }
        if(stList.size()<=0) {
            throw new BusinessException(BusinessResult.create(601L,"系统异常，请稍后重试。"));
        }else{
            for (StudentVo s:stList) {
                System.out.println(s.toString());
            }
        }
        return stList;
    }

    /**
     * mybatis连通性测试-2
     * @param p1
     * @return
     */
    @RequestMapping("/teacher/{url}")
    @ResultBeanResponseBody
    public List<TeacherVo> qryAllTeacherVoList(@PathVariable("url") String urlParam,
                                               @RequestParam("p1") String p1){
        System.out.println("BasicController...qryAllTeacherVoList..");
        List<TeacherVo> stList=null;
        CourseVo c=new CourseVo();
        try{
            TeacherVo teacherVo=new TeacherVo();
            //teacherVo.setTId(StringUtils.isEmpty(p1)?null:Integer.parseInt(p1));
            if(!Objects.isNull(urlParam)){
                switch(urlParam){
                    case "1": stList=basicMapper.qryAllTeacherVoList(teacherVo);break;
                    case "2": stList=basicMapper.qryAllTeacherVoList2(teacherVo);break;
                    case "3": stList=basicMapper.qryAllTeacherVoListAllWayWithoutSelect(teacherVo);break;
                    case "4": stList=basicMapper.qryAllTeacherVoListAllWayWithSelect(teacherVo);break;


                    default:break;
                }
            }else{
                stList= Collections.emptyList();
            }
            if(stList.size()<=0) {
                throw new BusinessException(BusinessResult.create(601L,"系统异常，请稍后重试。"));
            }else{
                for (TeacherVo t:stList) {
                    System.out.println(t.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(BusinessResult.create(602L,"系统异常:"+e.getMessage()));
        }
        return stList;
    }

    /**
     * todo mybatis事务测试-1
     * 测试结果：OK
     * @param tId
     * @return
     * TODO 重复测试-要删除的脚本
     *  DELETE FROM teacher WHERE t_id=4;
     *  DELETE FROM course WHERE c_id=10;
     *  DELETE FROM student WHERE st_id like 'ST00000%';
     */
    @RequestMapping("/teacher/add")
    @ResultBeanResponseBody
    public String insertTeacherRelationDatas(@RequestParam("p1") String tId){
        System.out.println("BasicController...insertTeacherRelationDatas..");
        boolean retF=false;
        try{
            TeacherVo teacherVo=new TeacherVo();
            if(!StringUtils.isEmpty(tId)){
               // teacherVo.setTId(Integer.valueOf(tId));
                retF=basicFacade.insertOneTeacherInfo(teacherVo);
            }else{
                throw new Exception("请求参数p1不可为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(BusinessResult.create(602L,"系统异常:"+e.getMessage()));
        }
        return retF?"新增成功":"新增失败";
    }


    /**
     * todo 测试Spring Data JPA
     */
    @RequestMapping("/jpa/1")
    @ResultBeanResponseBody
    public String springDataJpaDemo1(@RequestParam("p1") String tId){
        System.out.println("BasicController...springDataJpaDemo1..");
        boolean retF=false;
        try{
            RoleVo roleVo=new RoleVo(1);
            List<RoleVo> list=basicFacade.qryAllTeachInfo(roleVo);
            if(list!=null && list.size()>0){
                retF=true;
            }
            System.out.println(JSON.toJSON(list));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(BusinessResult.create(602L,"系统异常:"+e.getMessage()));
        }
        return retF?"操作成功":"操作失败";
    }

}
