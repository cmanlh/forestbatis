package com.lifeonwalden.forestbatis.meta;

import java.util.List;
import java.util.Optional;

/**
 * 数据库表的元信息
 */
public interface TableMeta extends SqlNode {
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
