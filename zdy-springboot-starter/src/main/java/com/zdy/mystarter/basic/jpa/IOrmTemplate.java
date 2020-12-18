package com.zdy.mystarter.basic.jpa;

import java.io.Serializable;
import java.util.List;

/**
 * TODO 主要包括 增删查改等常用操作
 * <pre>
 *     主要包括 增删查改等常用操作
 * </pre>
 * @author lvjun
 * @version 1.0
 * @date 2020/1/16 10:19
 * @desc
 */
public interface IOrmTemplate {

    /**
     * 新增
     *
     * @param entity
     * @param <T>
     */
    <T> void save(T entity);

    /**
     * 批量新增
     *
     * @param entityList
     * @param <T>
     */
    <T> void saveAll(List<T> entityList);

    /**
     * 更新
     *
     * @param entity
     * @param <T>
     */
    <T> void update(T entity);

    /**
     * 批量更新
     *
     * @param entityList
     * @param <T>
     */
    <T> void updateAll(List<T> entityList);

    /**
     * 删除
     *
     * @param entity
     * @param <T>
     */
    <T> void delete(T entity);

    /**
     * 批量删除
     *
     * @param entityList
     * @param <T>
     */
    <T> void deleteAll(List<T> entityList);

    /**
     * 根据id删除
     *
     * @param clazz
     * @param id
     * @param <T>
     */
    <T> void delete(Class<T> clazz, Serializable id);

    /**
     * 根据id删除
     *
     * @param clazz
     * @param ids
     * @param <T>
     */
    <T> void deleteByIds(Class<T> clazz, Object... ids);

    /**
     * 根据id删除
     *
     * @param clazz
     * @param ids
     * @param <T>
     */
    <T> void deleteByIds(Class<T> clazz, List<Serializable> ids);

    /**
     * 根据条件删除
     *
     * @param clazz
     * @param entityFiedName
     * @param value
     * @param <T>
     */
    <T> void deleteByPropertyEq(Class<T> clazz, String entityFiedName, Object value);

    /**
     * 脱离session管制，变为游离态
     *
     * @param t
     * @param <T>
     */
    <T> void detach(T t);

    /**
     * 根据id获取
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T get(Class<T> clazz, Serializable id);

    /**
     * 获取所有数据列表，操作此方法时，确认数据库中数据量不要太大
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> loadAll(Class<T> clazz);

    /**
     * 根据id查找对象列表
     *
      * @param clazz
     * @param ids
     * @param <T>
     * @return
     */
    <T> List<T> findByIds(Class<T> clazz, Object... ids);

    /**
     * 分页查找数据列表
     *
     * @param clazz
     * @param page 从0开始
     * @param rows 每页展示个数
     * @param <T>
     * @return
     */
    <T> List<T> findPage(Class<T> clazz, Integer page, Integer rows);

    /**
     * 根据搜索条件查询--分页
     *
     * @param clazz       类对象
     * @param condition   查询条件
     * @param page        从0开始
     * @param rows        每页展示个数
     * @param <T>         泛型类型
     * @return            符合条件的数据列表
     */
    <T> List<T> findPageByCondition(Class<T> clazz, List<JpaSearchDto> condition, Integer page, Integer rows);

    /**
     * 根据搜索条件查询--分页
     *
     * @param clazz       类对象
     * @param condition   查询条件
     * @param page        从0开始
     * @param rows        每页展示个数
     * @param <T>         泛型类型
     * @return            符合条件的数据列表
     */
    <T> List<T> findPageByCondition(Class<T> clazz, JpaSearchDto condition, Integer page, Integer rows);

    /**
     * 根据搜索条件查询
     *
     * @param clazz      类对象
     * @param condition  查询条件
     * @param <T>        泛型类型
     * @return           查询列表数据
     */
    <T> List<T> findByCondition(Class<T> clazz, JpaSearchDto condition);

    /**
     * 根据搜索条件查询
     *
     * @param clazz      类对象
     * @param condition  查询条件
     * @param <T>        泛型类型
     * @return           查询列表数据
     */
    <T> List<T> findByCondition(Class<T> clazz, List<JpaSearchDto> condition);

    /**
     * 根据搜索条件查询唯一结果
     *
     * @param clazz      类对象
     * @param condition  查询条件
     * @param <T>        泛型类型
     * @return           查询列表数据
     */
    <T> T findUniqueByCondition(Class<T> clazz, List<JpaSearchDto> condition);

    /**
     * 查找符合条件列表
     *
     * @param clazz      类对象
     * @param fieldName  对象属性
     * @param value      对象值
     * @param <T>        泛型类型
     * @return           查询列表数据
     */
    <T> List<T> findByPropertyEq(Class<T> clazz, String fieldName, Object value);

    /**
     * 查找符合条件的唯一对象
     *
     * @param clazz      类对象
     * @param fieldName  对象属性
     * @param value      对象值
     * @param <T>        泛型类型
     * @return           查询列表数据
     */
    <T> T findUniqueByPropertyEq(Class<T> clazz, String fieldName, Object value);

    /**
     * 查找符合条件列表
     * @param clazz      jpa类对象
     * @param hql        对象属性
     * @param params     参数对象
     * @param <T>        泛型类型
     * @return           符合条件列表的列表
     */
    <T> List<T> findByHql(Class<T> clazz, String hql, Object... params);

    /**
     * 查找符合条件列表
     * @param clazz      jpa类对象
     * @param sql        sql语句
     * @param params     sql参数
     * @param <T>        泛型类型
     * @return           符合条件列表的列表
     */
    <T> List<T> findBySql(Class<T> clazz, String sql, Object... params);

    /**
     * 使用原生SQL查询结果
     * @param resultClazz
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    <T> T findUniqueBySql(Class<T> resultClazz, String sql, Object... params);

}
