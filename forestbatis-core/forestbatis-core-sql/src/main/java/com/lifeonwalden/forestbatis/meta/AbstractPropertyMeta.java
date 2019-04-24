package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;

import java.util.Optional;

public abstract class AbstractPropertyMeta implements PropertyMeta {
    // 属性名
    protected String name;

    // 对应的JdbcType
    protected JdbcType jdbcType;

    @Override
    public String getName() {
        return this.name;
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
    public void toSql(StringBuilder builder, boolean withAlias) {
        if (null == this.jdbcType) {
            throw new RuntimeException("Has to specify JdbcType for a Property.");
        }

        builder.append("#{").append(this.name).append(", JdbcType=").append(this.jdbcType.getJdbcType()).append("}");
    }

    @Override
    public void toSql(StringBuilder builder) {
        this.toSql(builder, false);
    }
}
