package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.NodeRelation;

public class LogicalNode implements SqlNode {
    private ColumnMeta column;
    private PropertyMeta property;
    private NodeRelation relation;

    public LogicalNode(ColumnMeta column, NodeRelation relation) {
        this.column = column;
        this.relation = relation;
    }

    public LogicalNode(PropertyMeta property, NodeRelation relation) {
        this.property = property;
        this.relation = relation;
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        if (null != column) {
            column.toSql(builder, config, withAlias);
        }
        if (null != property) {
            property.toSql(builder, config, withAlias);
        }
        if (null != relation) {
            relation.toSql(builder, config, withAlias);
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        toSql(builder, config, false);
    }
}
