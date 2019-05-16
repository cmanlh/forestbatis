package com.lifeonwalden.forestbatis.meta;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractTableMeta implements TableMeta {
    protected String name;
    protected String alias;
    protected boolean beWithSchema;
    protected String schema;
    protected List<ColumnMeta> columnList;

    public AbstractTableMeta(String name, String alias) {
        this(name, alias, false, null, null);
    }

    public AbstractTableMeta(String name, String alias, boolean beWithSchema, String schema) {
        this(name, alias, beWithSchema, schema, null);
    }

    public AbstractTableMeta(String name, String alias, List<ColumnMeta> columnList) {
        this(name, alias, false, null, columnList);
    }

    public AbstractTableMeta(String name, String alias, boolean beWithSchema, String schema, List<ColumnMeta> columnList) {
        this.name = name;
        this.alias = alias;
        this.beWithSchema = beWithSchema;
        this.schema = schema;
        this.columnList = Collections.unmodifiableList(columnList);
    }

    @Override
    public boolean beWithSchema() {
        return this.beWithSchema;
    }

    @Override
    public String getSchema() {
        return this.schema;
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
        if (this.beWithSchema) {
            builder.append(this.schema).append(".").append(this.name);
        } else {
            builder.append(this.name);
        }
        if (withAlias) {
            builder.append(" ").append(this.alias);
        }
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj instanceof AbstractTableMeta) {
                AbstractTableMeta _obj = (AbstractTableMeta) obj;
                return this.name.equals(_obj.name);
            }

            return false;
        }
    }
}
