package com.lifeonwalden.forestbatis.constant;

import com.lifeonwalden.forestbatis.meta.SqlNode;

public enum SqlOperator implements SqlNode {
    EQ("="), NEQ("<>"), BT(">"), LT("<"), BTE(">="), LTE("<="), LIKE("like"), NOT_LIKE("not like"), IN("in"), NOT_IN("not in"), EXISTS("exists"), NOT_EXISTS("not exists"),
    ON("on"), LEFT_JOIN("left join"), RIGHT_JOIN("right join"), INNER_JOIN("right join");
    private String sign;

    SqlOperator(String sign) {
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
