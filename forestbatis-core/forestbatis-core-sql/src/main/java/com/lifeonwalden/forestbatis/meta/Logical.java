package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.NodeRelation;

public class Logical<T> extends AbstractQueryNode<T> {
    private LogicalNode[] nodes;
    private PropertyMeta resultProp;
    private ColumnMeta resultColumn;

    public Logical(PropertyMeta resultProp, LogicalNode... nodes) {
        this.resultProp = resultProp;
        this.nodes = nodes;
    }

    public Logical(ColumnMeta resultColumn, LogicalNode... nodes) {
        this.resultColumn = resultColumn;
        this.nodes = nodes;
    }

    @Override
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        builder.append("(");

        for (LogicalNode node : nodes) {
            node.toSql(builder, config, withAlias);
        }

        NodeRelation.EQ.toSql(builder, config, withAlias);
        if (null != resultProp) {
            this.resultProp.toSql(builder, config, withAlias);
        }
        if (null != resultColumn) {
            this.resultColumn.toSql(builder, config, withAlias);
        }

        builder.append(")");
    }
}
