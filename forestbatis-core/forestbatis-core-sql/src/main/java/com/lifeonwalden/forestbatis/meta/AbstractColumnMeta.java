package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;

import java.util.Optional;

public abstract class AbstractColumnMeta implements ColumnMeta {
    // 列名
    protected String label;

    // 列对应的JdbcType
    protected JdbcType jdbcType;

    //列在所处执行语句上下文中的下标值
    protected int index;

    // 所在表
    protected TableMeta table;

    // 对应java类相映射的属性
    protected PropertyMeta javaProperty;

    public AbstractColumnMeta(String label) {
        this.label = label;
    }

    public AbstractColumnMeta(String label, JdbcType jdbcType) {
        this.label = label;
        this.jdbcType = jdbcType;
    }

    public AbstractColumnMeta(String label, JdbcType jdbcType, TableMeta table) {
        this.label = label;
        this.jdbcType = jdbcType;
        this.table = table;
    }

    public AbstractColumnMeta(String label, JdbcType jdbcType, TableMeta table, PropertyMeta javaProperty) {
        this.label = label;
        this.jdbcType = jdbcType;
        this.table = table;
        this.javaProperty = javaProperty;
    }


    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Optional<String> getAlias() {
        if (null == this.table) {
            return Optional.empty();
        } else {
            return Optional.of(this.table.getAlias());
        }
    }

    @Override
    public Optional<JdbcType> getJdbcType() {
        if (null == this.jdbcType) {
            return Optional.empty();
        } else {
            return Optional.of(this.jdbcType);
        }
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    public AbstractColumnMeta setIndex(int index) {
        this.index = index;

        return this;
    }

    @Override
    public Optional<TableMeta> getTable() {
        if (null == this.table) {
            return Optional.empty();
        } else {
            return Optional.of(this.table);
        }
    }

    @Override
    public Optional<PropertyMeta> getJavaProperty() {
        if (null == this.javaProperty) {
            return Optional.empty();
        } else {
            return Optional.of(this.javaProperty);
        }
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        if (withAlias) {
            if (this.getAlias().isPresent()) {
                builder.append(this.getAlias().get()).append(".").append(this.getLabel());
            } else {
                throw new RuntimeException("Don't bind to a table");
            }
        } else {
            builder.append(this.getLabel());
        }
    }

    @Override
    public void toSql(StringBuilder builder) {
        this.toSql(builder, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof AbstractColumnMeta) {
            AbstractColumnMeta _o = (AbstractColumnMeta) o;
            return this.label.equals(_o.label) && this.table == _o.table;
        }

        return false;
    }
}
