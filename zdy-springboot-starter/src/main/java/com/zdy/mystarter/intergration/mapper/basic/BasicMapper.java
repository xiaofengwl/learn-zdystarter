package com.zdy.mystarter.intergration.mapper.basic;

import com.zdy.mystarter.caches.mybatiscache.MybatisRedisCache;
import com.zdy.mystarter.model.basic.CourseVo;
import com.zdy.mystarter.model.basic.StudentVo;
import com.zdy.mystarter.model.basic.TeacherVo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mybatis对应的mapper接口
 * Created by Jason on 2020/1/13.
 */
@Mapper
@Component
//该注解需要配合@Select来生效
//注意事项：测试发现：在mapper*.xml中配置cache和此处使用@CacheNamespace是相互冲突的，会导致启动sqlSession启动异常。
//@CacheNamespace(implementation = MybatisRedisCache.class,size = 65535)
public interface BasicMapper {

    /**
     * [1]测试-1
     * todo- 测试点：
     * （1）<sql><sql/> 和 <include/>    通用列查询测试
     * （2）<where></where> 和 <if><if/> 查询测试
     * （3）parameterType="实体类路径"     接收参数类型测试
     * （4）resultType="实体类路径"        返回参数类型测试
     * @param studentVo
     * @return
     */
    List<StudentVo> qryAllStudentVoList(StudentVo studentVo);

    /**
     * [2]测试-2
     * todo- 测试点：
     * （1）parameterType="实体类路径"     接收参数类型测试
     * （2）resultType="map"             返回参数类型测试
     *
     * @param studentVo
     * @return
     */
    List<StudentVo> qryAllStudentVoList2(StudentVo studentVo);


    /**
     * [3]测试-3
     * todo- 测试点：
     * （1）parameterType="实体类路径"     接收参数类型测试
     * （2）resultType="map"             返回参数类型测试
     * （3）association 一对一关联（不用select）
     * @param teacherVo
     * @return
     */
    List<TeacherVo> qryAllTeacherVoList(TeacherVo teacherVo);

    /**
     * [3]测试-3
     * todo- 测试点：
     * （1）parameterType="实体类路径"     接收参数类型测试
     * （2）resultType="map"             返回参数类型测试
     * （3）association 一对一关联（用select）
     * @param teacherVo
     * @return
     */
    List<TeacherVo> qryAllTeacherVoList2(TeacherVo teacherVo);

    /**
     * [4]测试-4
     * todo- 测试点：
     * （1）parameterType="实体类路径"     接收参数类型测试
     * （2）resultType="map"             返回参数类型测试
     * （3）association 一对一关联（不用select）
     * （4）collection  一对多关联（不用select）
     * @param teacherVo
     * @return
     */
    List<TeacherVo> qryAllTeacherVoListAllWayWithoutSelect(TeacherVo teacherVo);

    /**
     * [5]测试-5
     * todo- 测试点：
     * （1）parameterType="实体类路径"     接收参数类型测试
     * （2）resultType="map"             返回参数类型测试
     * （3）association 一对一关联（用select）
     * （4）collection  一对多关联（用select）
     * @param teacherVo
     * @return
     */
    List<TeacherVo> qryAllTeacherVoListAllWayWithSelect(TeacherVo teacherVo);


    /**
     * [6] 事务测试-1
     * @param teacherVo
     * @return
     */
    int insertOneTeacherInfo(TeacherVo teacherVo);
    /**
     * [7] 事务测试-2
     * @param courseVo
     * @return
     */
    int insertOneCourseInfo(CourseVo courseVo);
    /**
     * [8] 事务测试-3
     * @param stuList
     * @return
     */
    int insertMoreStudentInfo(@Param("stuList") List<StudentVo> stuList);
}
