package com.lifeonwalden.forestbatis.meta;

import java.util.List;
import java.util.Optional;

/**
 * 数据库表的元信息
 */
public interface TableMeta extends SqlNode {

    /**
     * 表操作是否需要schema
     *
     * @return
     */
    boolean beWithSchema();

    /**
     * 获得数据库schema名称
     *
     * @return
     */
    String getSchema();

    /**
     * 获得表名
     *
     * @return
     */
    String getName();

    /**
     * 获得表的别名
     *
     * @return
     */
    String getAlias();

    /**
     * @return
     */
    Optional<List<ColumnMeta>> getColumn();
}
