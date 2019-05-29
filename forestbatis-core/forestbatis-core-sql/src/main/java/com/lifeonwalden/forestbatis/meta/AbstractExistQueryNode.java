package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;

public class AbstractExistQueryNode<T> extends AbstractQueryNode<T> {
    // 子查询临时表
    protected TempTable tempTable;

    @Override
    public boolean hasSubQuery() {
        return true;
    }

    @Override
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        this.compareRelation.toSql(builder, config);
        builder.append("(");
        this.tempTable.toSql(builder, config, withAlias, value);
        builder.append(")");
    }

    @Override
    public boolean isRuntimeChangeable() {
        return this.tempTable.isRuntimeChangeable();
    }
}

