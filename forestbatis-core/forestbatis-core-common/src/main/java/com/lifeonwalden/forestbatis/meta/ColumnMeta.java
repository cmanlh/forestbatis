package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;

import java.util.Optional;

/**
 * 数据库列的元信息
 */
public interface ColumnMeta extends SqlNode {

    /**
     * 获得列名
     *
     * @return
     */
    String getLabel();

    /**
     * 获得列的别名
     *
     * @return
     */
    Optional<String> getAlias();

    /**
     * 获得列对应的JdbcType
     *
     * @return
     */
    Optional<JdbcType> getJdbcType();

    /**
     * 获得列在所处执行语句上下文中的下标值
     *
     * @return
     */
    int getIndex();

    /**
     * 获得所在表的信息
     *
     * @return
     */
    Optional<TableMeta> getTable();

    /**
     * 获得对应java类相映射的属性
     *
     * @return
     */
    Optional<PropertyMeta> getJavaProperty();
}
