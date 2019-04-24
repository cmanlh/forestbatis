package com.lifeonwalden.forestbatis.constant;

import com.lifeonwalden.forestbatis.meta.SqlNode;

/**
 * SQL DML type
 */
public enum SqlCommandType implements SqlNode {
    SELECT("select"), UPDATE("update"), INSERT("insert"), DELETE("delete");

    private String sign;

    SqlCommandType(String sign) {
        this.sign = sign;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        builder.append(this.sign).append(" ");
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder);
    }
}
