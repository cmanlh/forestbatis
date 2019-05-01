package com.lifeonwalden.forestbatis.meta;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractTableMeta implements TableMeta {
    protected String name;
    protected String alias;
    protected List<ColumnMeta> columnList;

    public AbstractTableMeta(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public AbstractTableMeta(String name, String alias, List<ColumnMeta> columnList) {
        this.name = name;
        this.alias = alias;
        this.columnList = Collections.unmodifiableList(columnList);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public Optional<List<ColumnMeta>> getColumn() {
        if (null == this.columnList || this.columnList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.columnList);
        }
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        builder.append(this.name);
        if (withAlias) {
            builder.append(" ").append(this.alias);
        }
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder, false);
    }
}
