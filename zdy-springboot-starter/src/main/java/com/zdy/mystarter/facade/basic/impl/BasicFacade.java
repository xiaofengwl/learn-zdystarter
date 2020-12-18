package com.zdy.mystarter.facade.basic.impl;

import com.zdy.mystarter.basic.jpa.impl.JpaTemplate;
import com.zdy.mystarter.facade.basic.IBasicFacade;
import com.zdy.mystarter.model.basic.CourseVo;
import com.zdy.mystarter.model.basic.RoleVo;
import com.zdy.mystarter.model.basic.StudentVo;
import com.zdy.mystarter.model.basic.TeacherVo;
import com.zdy.mystarter.service.basic.IBasicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2020/1/14.
 */
@Service
public class BasicFacade implements IBasicFacade{

    /**
     * 装配服务
     */
    @Resource
    IBasicService basicService;

    /**
     * 装配JPA
     */
    @Resource
    JpaTemplate jpaTemplate;

    /**
     * 新增Tacher数据（综合方法）---事务管理
     *
     * @param teacherVo
     * @return
     */
    @Transactional
    @Override
    public boolean insertOneTeacherInfo(TeacherVo teacherVo) {
        /**
         * TODO 准备数据
         */
        //data-1
        CourseVo cvo=new CourseVo();
        cvo.setCId(10);
        cvo.setCName("区块链技术研讨");
        cvo.setCAddress("某某某国际会展中心旁的小饭店大堂");
        cvo.setCRen("就知道忽悠人");

        //data-3
        int len=5;
        List<StudentVo> stuList=new ArrayList<>(len);
        for (int i=0;i<len;i++){
            StudentVo stvo=new StudentVo();
            stvo.setStId("ST00000"+i);
            stvo.setStName("群演-编号"+i);
            stvo.setStAge("50");
            stvo.setStEmail("****@qq.com");
            stvo.setStAddress("东门大桥下");
            stvo.setStRen("能忽悠一个是一个");
            stvo.setStOthers("哔哔赖赖");
            stvo.setStSex("WOMEN");
           // stvo.setStTeacherId(teacherVo.getTId());
            stuList.add(stvo);
        }

        //data-3
        TeacherVo tvo=new TeacherVo();
        tvo.setTId(teacherVo.getTId());
        tvo.setTName("测试Teacher-"+teacherVo.getTId());
        tvo.setTSex("女");
        tvo.setTAddress("广东省深圳市福田区购物中心站平安金融大厦南塔13层299号");
        //tvo.setTCourseId(cvo.getCId());
        tvo.setCourse(cvo);
        tvo.setStudents(stuList);

        /**
         * TODO 数据操作
         */
        boolean tf=basicService.insertOneTeacherInfo(tvo);
        boolean cf=basicService.insertOneCourseInfo(cvo);
        boolean sf=basicService.insertMoreStudentInfo(stuList);
        boolean ff=(tf && cf && sf);
        if(!ff){
            System.out.println("存在执行失败项：tf="+tf+",cf="+cf+",sf"+sf);
            return false;
        }
        System.out.println("执行成功：tf="+tf+",cf="+cf+",sf="+sf);
        return true;
    }

    /**
     * 根据条件查询信息
     *
     * @param roleVo
     * @return
     */
    @Override
    public List<RoleVo> qryAllTeachInfo(RoleVo roleVo) {
        String sql="SELECT *FROM role WHERE id=?";
        return jpaTemplate.findBySql(RoleVo.class,sql,roleVo.getRoleId());
    }
}
