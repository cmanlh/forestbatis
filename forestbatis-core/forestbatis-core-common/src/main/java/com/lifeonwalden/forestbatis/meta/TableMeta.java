package com.lifeonwalden.forestbatis.meta;

/**
 * 数据库表的元信息
 */
public interface TableMeta extends SqlNode {
    /**
     * 获得表名
     *
     * @return
     */
    String getLabel();

    /**
     * 获得表的别名
     *
     * @return
     */
    String getAlias();
}
