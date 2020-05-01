package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;

/**
 * group by 节点
 */
public class Group implements GroupBy {

    // 聚合的列
    private ColumnMeta[] columns;

    public Group(ColumnMeta... columns) {
        this.columns = columns;
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        if (null != this.columns && this.columns.length > 0) {
            this.columns[0].toSql(builder, config, withAlias);

            if (this.columns.length > 1) {
                for (int i = 1; i < this.columns.length; i++) {
                    builder.append(", ");
                    this.columns[i].toSql(builder, config, withAlias);
                }
            }
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        toSql(builder, config, false);
    }
}
