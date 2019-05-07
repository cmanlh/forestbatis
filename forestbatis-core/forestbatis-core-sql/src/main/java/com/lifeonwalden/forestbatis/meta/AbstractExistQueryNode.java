package com.lifeonwalden.forestbatis.meta;

public class AbstractExistQueryNode<T> extends AbstractQueryNode<T> {
    // 子查询临时表
    protected TempTable tempTable;

    @Override
    public boolean hasSubQuery() {
        return true;
    }

    @Override
    protected void selfBuild(StringBuilder builder, boolean withAlias, T value) {
        this.compareRelation.toSql(builder);
        builder.append("(");
        this.tempTable.toSql(builder, withAlias, value);
        builder.append(")");
    }

    @Override
    public boolean isRuntimeChangeable() {
        return this.tempTable.isRuntimeChangeable();
    }
}

