package com.lifeonwalden.forestbatis.constant;

import com.lifeonwalden.forestbatis.meta.SqlNode;

/**
 * SQL DML type
 */
public enum SqlCommandType implements SqlNode {
    SELECT("select"), UPDATE("update"), INSERT("insert into"), DELETE("delete");

    private String sign;

    private String SQL_FRAGMENT;

    SqlCommandType(String sign) {
        this.sign = sign;
        this.SQL_FRAGMENT = this.sign;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        builder.append(this.SQL_FRAGMENT);
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder, false);
    }
}
