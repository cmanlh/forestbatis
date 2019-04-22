package com.lifeonwalden.forestbatis.constant;

import com.lifeonwalden.forestbatis.meta.SqlNode;

/**
 * 逻辑关系枚举
 */
public enum LogicRelation implements SqlNode {
    AND("and"), OR("or");
    private String sign;

    LogicRelation(String sign) {
        this.sign = sign;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        builder.append(" ").append(this.sign).append(" ");
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder);
    }
}
