package com.zdy.mystarter.basic.jpa.impl;

import com.zdy.mystarter.basic.jpa.IOrmTemplate;
import com.zdy.mystarter.basic.jpa.JpaSearchDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.IntStream;

/**
 * TODO JPA标准实现，包括基本的增删查改等操作
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/16 14:12
 * @desc
 */
@Component              //组件化注入IOC容器
public class JpaTemplate  implements IOrmTemplate{

    /**
     * todo 1.自动装配实体管理器-EntityManager
     * todo 2.介绍EntityManager是什么？
     *      EntityManager是JPA中用于增删改查的接口，它的作用相当于一座桥梁，
     *      连接内存中的java对象和数据库的数据存储。
     * todo 3.需要在pom.xml添加Spring Data JPA依赖
     *      <dependency>
     *           <groupId>org.springframework.boot</groupId>
     *           <artifactId>spring-boot-starter-data-jpa</artifactId>
     *      </dependency>
     * todo 4.EntityManager内置常用方法有哪些？
     *     (1)entityManager.persist(Object entity);　　新增数据；
     *     (2)entityManager.merge(T entity);　将 Detached状态的Entity实例转至Managed状态；
     *     (3)entityManager.remove(Object entity);　　删除数据；
     *     (4)entityManager.find(Class<T> entityClass, Object primaryKey);　　根据主键查找数据；
     *     (5)entityManager.clear();将所有的Entity实例状态转至Detached状态；
     *     (6)entityManager.flush();将所有Managed状态的Entity实例同步到数据库；
     *     (7)entityManager.refresh(Object entity);加载Entity实例后，数据库该条数据被修改，refresh该实例，能得到数据库最新的修改，覆盖原来的Entity实例；
     * todo 5.
     */
    @Resource
    private EntityManager entityManager;

    /**
     * 新增
     *
     * @param entity
     */
    @Transactional
    @Override
    public <T> void save(T entity) {
        entityManager.persist(entity);
    }

    /**
     * 批量新增
     *
     * @param entityList
     */
    @Transactional
    @Override
    public <T> void saveAll(List<T> entityList) {
        IntStream.range(0,entityList.size()).forEach(index->{       //Java8 Stream的特性
            entityManager.persist(entityList.get(index));
            if(index%20==0){            //每20笔刷一次
                entityManager.flush();
                entityManager.clear();
            }
        });
    }

    /**
     * 更新
     *
     * @param entity
     */
    @Transactional
    @Override
    public <T> void update(T entity) {
        entityManager.merge(entity);
    }

    /**
     * 批量更新
     *
     * @param entityList
     */
    @Transactional
    @Override
    public <T> void updateAll(List<T> entityList) {
        IntStream.range(0,entityList.size()).forEach(index->{       //Java8 Stream的特性
            entityManager.merge(entityList.get(index));
            if(index%20==0){            //每20笔刷一次
                entityManager.flush();
                entityManager.clear();
            }
        });
    }

    /**
     * 删除
     *
     * @param entity
     */
    @Transactional
    @Override
    public <T> void delete(T entity) {
        entityManager.remove(entity);
    }

    /**
     * 批量删除
     *
     * @param entityList
     */
    @Transactional
    @Override
    public <T> void deleteAll(List<T> entityList) {
        entityList.forEach(entity->{
            if(!entityManager.contains(entity)){
                entityManager.remove(entityManager.merge(entity));
            }else{
                entityManager.remove(entity);
            }
        });
    }

    /**
     * 根据id删除
     *
     * @param clazz
     * @param id
     */
    @Transactional
    @Override
    public <T> void delete(Class<T> clazz, Serializable id) {
        entityManager.remove(entityManager.find(clazz,id));
    }

    /**
     * 根据id删除
     *
     * @param clazz
     * @param ids
     */
    @Transactional
    @Override
    public <T> void deleteByIds(Class<T> clazz, Object... ids) {

    }

    /**
     * 根据id删除
     *
     * @param clazz
     * @param ids
     */
    @Transactional
    @Override
    public <T> void deleteByIds(Class<T> clazz, List<Serializable> ids) {

    }

    /**
     * 根据条件删除
     *
     * @param clazz
     * @param entityFiedName
     * @param value
     */
    @Transactional
    @Override
    public <T> void deleteByPropertyEq(Class<T> clazz, String entityFiedName, Object value) {

    }

    /**
     * 脱离session管制，变为游离态
     *
     * @param t
     */
    @Transactional
    @Override
    public <T> void detach(T t) {

    }

    /**
     * 根据id获取
     *
     * @param clazz
     * @param id
     * @return
     */
    @Transactional
    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        return null;
    }

    /**
     * 获取所有数据列表，操作此方法时，确认数据库中数据量不要太大
     *
     * @param clazz
     * @return
     */
    @Transactional
    @Override
    public <T> List<T> loadAll(Class<T> clazz) {
        return null;
    }

    /**
     * 根据id查找对象列表
     *
     * @param clazz
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public <T> List<T> findByIds(Class<T> clazz, Object... ids) {
        return null;
    }

    /**
     * 分页查找数据列表
     *
     * @param clazz
     * @param page  从0开始
     * @param rows  每页展示个数
     * @return
     */
    @Transactional
    @Override
    public <T> List<T> findPage(Class<T> clazz, Integer page, Integer rows) {
        return null;
    }

    /**
     * 根据搜索条件查询--分页
     *
     * @param clazz     类对象
     * @param condition 查询条件
     * @param page      从0开始
     * @param rows      每页展示个数
     * @return 符合条件的数据列表
     */
    @Transactional
    @Override
    public <T> List<T> findPageByCondition(Class<T> clazz, List<JpaSearchDto> condition, Integer page, Integer rows) {
        return null;
    }

    /**
     * 根据搜索条件查询--分页
     *
     * @param clazz     类对象
     * @param condition 查询条件
     * @param page      从0开始
     * @param rows      每页展示个数
     * @return 符合条件的数据列表
     */
    @Transactional
    @Override
    public <T> List<T> findPageByCondition(Class<T> clazz, JpaSearchDto condition, Integer page, Integer rows) {
        return null;
    }

    /**
     * 根据搜索条件查询
     *
     * @param clazz     类对象
     * @param condition 查询条件
     * @return 查询列表数据
     */
    @Transactional
    @Override
    public <T> List<T> findByCondition(Class<T> clazz, JpaSearchDto condition) {
        return null;
    }

    /**
     * 根据搜索条件查询
     *
     * @param clazz     类对象
     * @param condition 查询条件
     * @return 查询列表数据
     */
    @Transactional
    @Override
    public <T> List<T> findByCondition(Class<T> clazz, List<JpaSearchDto> condition) {
        return null;
    }

    /**
     * 根据搜索条件查询唯一结果
     *
     * @param clazz     类对象
     * @param condition 查询条件
     * @return 查询列表数据
     */
    @Transactional
    @Override
    public <T> T findUniqueByCondition(Class<T> clazz, List<JpaSearchDto> condition) {
        return null;
    }

    /**
     * 查找符合条件列表
     *
     * @param clazz     类对象
     * @param fieldName 对象属性
     * @param value     对象值
     * @return 查询列表数据
     */
    @Transactional
    @Override
    public <T> List<T> findByPropertyEq(Class<T> clazz, String fieldName, Object value) {
        return null;
    }

    /**
     * 查找符合条件的唯一对象
     *
     * @param clazz     类对象
     * @param fieldName 对象属性
     * @param value     对象值
     * @return 查询列表数据
     */
    @Transactional
    @Override
    public <T> T findUniqueByPropertyEq(Class<T> clazz, String fieldName, Object value) {
        return null;
    }

    /**
     * 查找符合条件列表
     *
     * @param clazz  jpa类对象
     * @param hql    对象属性
     * @param params 参数对象
     * @return 符合条件列表的列表
     */
    @Transactional
    @Override
    public <T> List<T> findByHql(Class<T> clazz, String hql, Object... params) {
        return null;
    }

    /**
     * 查找符合条件列表
     *
     * @param clazz  jpa类对象
     * @param sql    sql语句
     * @param params sql参数
     * @return 符合条件列表的列表
     */
    @Transactional
    @Override
    public <T> List<T> findBySql(Class<T> clazz, String sql, Object... params) {
        Query createNativeQuery=entityManager.createNativeQuery(sql);
        IntStream.range(0,params.length).forEach(index->{
            createNativeQuery.setParameter(index+1,params[index]);
        });
        return createNativeQuery.getResultList();
    }

    /**
     * 使用原生SQL查询结果
     *
     * @param resultClazz
     * @param sql
     * @param params
     * @return
     */
    @Transactional
    @Override
    public <T> T findUniqueBySql(Class<T> resultClazz, String sql, Object... params) {
       List<T> resultList=this.findBySql(resultClazz,sql,params);
       if(resultList!=null && resultList.size()>0){
           return resultList.get(0);
       }else{
           return null;
       }
    }
    /****************_______其他普通方法________***********************************/
    /**
     * TODO 从指定类中获取ID名称
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> String findIdByField(Class<T> clazz){
        String fieldName=null;
        Field[] beanFields=clazz.getFields();
        //todo 查找id属性值
        for (Field field:beanFields) {
            boolean isId=field.isAnnotationPresent(Id.class);
            if(isId){
                fieldName=field.getName();
                break;
            }
        }
        return fieldName;
    }







}
