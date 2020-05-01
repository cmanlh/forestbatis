package com.lifeonwalden.forestbatis.meta.func;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.PropertyMeta;
import com.lifeonwalden.forestbatis.meta.TableMeta;

import java.util.Optional;

public abstract class Func implements ColumnMeta {
    // 列
    protected ColumnMeta column;

    //列在所处执行语句上下文中的下标值
    protected int index;

    // 对应java类相映射的属性
    protected PropertyMeta javaProperty;

    @Override
    public String getLabel() {
        return this.column.getLabel();
    }

    @Override
    public Optional<String> getAlias() {
        return this.column.getAlias();
    }

    @Override
    public Optional<JdbcType> getJdbcType() {
        return this.column.getJdbcType();
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public Optional<TableMeta> getTable() {
        return this.column.getTable();
    }

    @Override
    public Optional<PropertyMeta> getJavaProperty() {
        if (null == this.javaProperty) {
            return this.column.getJavaProperty();
        } else {
            return Optional.of(this.javaProperty);
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        selfBuild(builder, config, withAlias);
        builder.append("(");
        this.column.toSql(builder, config, withAlias);
        builder.append(") as ");

        if (null != this.javaProperty) {
            String caseSensitiveSign = config.isCaseSensitive() ? config.getSensitiveSign() : "";
            builder.append(caseSensitiveSign).append(this.javaProperty.getName()).append(caseSensitiveSign);
        } else {
            this.column.toSql(builder, config, withAlias);
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        this.toSql(builder, config, false);
    }

    protected abstract void selfBuild(StringBuilder builder, Config config, boolean withAlias);
}
