package com.lifeonwalden.forestbatis.constant;

import com.lifeonwalden.forestbatis.meta.SqlNode;

public enum OrderEnum implements SqlNode {
    ASC("asc"), DESC("desc");
    private String order;

    OrderEnum(String order) {
        this.order = order;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        builder.append(this.order);
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder);
    }
}
