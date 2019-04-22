package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;

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
    String getAlias();

    /**
     * 获得列对应的JdbcType
     *
     * @return
     */
    JdbcType getJdbcType();

    /**
     * 列在所处执行语句上下文中的下标值
     *
     * @return
     */
    int getIndex();

    /**
     * 获得所在表的信息
     *
     * @return
     */
    TableMeta getTable();
}
