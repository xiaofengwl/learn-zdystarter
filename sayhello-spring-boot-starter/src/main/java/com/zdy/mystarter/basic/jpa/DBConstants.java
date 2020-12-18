package com.zdy.mystarter.basic.jpa;

/**
 * TODO DB查询常用工具
 * <pre>
 *     添加描述
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/1/16 11:06
 * @desc
 */
public interface DBConstants {

    /**
     * TODO hql常用查询常量
     */
    public interface HQL{

        /**
         * 分隔符
         */
        String SYMBOL="o_o";

        /**
         * 等于
         */
        String EQ="eq";

        /**
         * 或者
         */
        String OR="or";

        /**
         * 全部等于--collection集合
         */
        String ALLEQ="allEq";

        /**
         * 不等于
         */
        String NEQ="neq";

        /**
         * 全部不等于
         */
        String ALLEEQ="allNeq";

        /**
         * 大于>
         */
        String GT="gt";

        /**
         * 大于等于 >=
         */
        String GE="ge";

        /**
         * 小于<
         */
        String LT="lt";

        /**
         * 小于等于<=
         */
        String LE="le";

        /**
         * 是null
         */
        String ISNULL="isNull";

        /**
         * 不是null
         */
        String ISNOTNULL="isNotNull";

        /**
         * isEnpty 为空
         */
        String ISEMPTY="isEmpty";

        /**
         * isNotEmpty 不为空
         */
        String ISNOTEMPTY="isNotEmpty";

        /**
         * in  值在XXX中
         */
        String IN="in";

        /**
         * like 像XX,精确匹配like XXX
         */
        String LIKE="like";

        /**
         * llike  左像XX,精确匹配like %XXX 慎用，会导致索引失效，数据量少的时候可以使用
         */
        String LLIKE="llike";

        /**
         * rlike  右像XX,精确匹配like XXX%
         */
        String RLIKE="rlike";

        /**
         * alike  全像XX,精确匹配like %XXX%
         */
        String ALIKE="alike";

        /**
         *  between 区间值
         */
        String BETWEEN="between";

        /**
         * substring 查找
         */
        String SUBSTRING="substring";

        /**
         * order 排序
         */
        String ORDER="order";

        /**
         * desc 倒序排序（降序）
         */
        String DESC="desc";

        /**
         * asc 正序排序（升序）
         */
        String ASC="asc";

        /**
         * groupby 分组查询
         */
        String GROUP_BY="groupby";

        /**
         * 子对象属性分割标识.
         */
        String SUBOBJ_PROPERTY_SYMBOL=".";

        /**
         * 子对象别名
         */
        String SUB_OBJECT_ALIAS="sub_object";
    }

    /**
     * TODO 注解常量
     */
    public interface Annotation{

        /**
         * UUID
         */
        String UUID="uuid";

        /**
         * 表明是引用类型，需要dao解析需要永达
         */
        String REFERENCE="RE";
    }

    /**
     * TODO 数据库操作常用常量
     */
    public interface OP{

        /**
         * 逗号分隔符，常用于界面批量操作
         */
        String COMMA=",";
    }


}
