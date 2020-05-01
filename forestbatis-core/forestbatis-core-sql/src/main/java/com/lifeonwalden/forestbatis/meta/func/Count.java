package com.lifeonwalden.forestbatis.meta.func;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.PropertyMeta;
import com.lifeonwalden.forestbatis.meta.TableMeta;

import java.util.Optional;

public class Count extends Func {
    private ColumnMeta[] distinctColumns;

    public Count(PropertyMeta javaProperty) {
        this.javaProperty = javaProperty;
    }

    public Count(PropertyMeta javaProperty, ColumnMeta... distinctColumns) {
        this.javaProperty = javaProperty;
        this.distinctColumns = distinctColumns;
    }

    @Override
    public String getLabel() {
        return this.javaProperty.getName();
    }

    @Override
    public Optional<String> getAlias() {
        return Optional.empty();
    }

    @Override
    public Optional<JdbcType> getJdbcType() {
        return Optional.of(JdbcType.INT);
    }

    @Override
    public Optional<TableMeta> getTable() {
        return Optional.empty();
    }

    @Override
    public Optional<PropertyMeta> getJavaProperty() {
        return Optional.of(this.javaProperty);
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        String caseSensitiveSign = config.isCaseSensitive() ? config.getSensitiveSign() : "";
        if (null == this.distinctColumns || this.distinctColumns.length == 0) {
            builder.append("count(1) as ").append(caseSensitiveSign).append(this.javaProperty.getName()).append(caseSensitiveSign);
        } else {
            builder.append("count(distinct ");
            distinctColumns[0].toSql(builder, config, withAlias);
            for (int i = 1; i < distinctColumns.length; i++) {
                builder.append(", ");
                distinctColumns[i].toSql(builder, config, withAlias);
            }
            builder.append(") as ").append(caseSensitiveSign).append(this.javaProperty.getName()).append(caseSensitiveSign);
        }
    }

    @Override
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias) {
        // do nothing
    }
}
