package com.lifeonwalden.forestbatis.constant;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.meta.SqlNode;

/**
 * 逻辑关系枚举
 */
public enum NodeRelation implements SqlNode {
    AND("and"), OR("or"), EQ("="), NEQ("<>"), BT(">"), LT("<"), BTE(">="), LTE("<="), LIKE("like"), NOT_LIKE("not like"), IN("in"), NOT_IN("not in"), EXISTS("exists"), NOT_EXISTS("not exists"), IS_NULL("is null"), IS_NOT_NULL("is not null"),
    ON("on"), LEFT_JOIN("left join"), RIGHT_JOIN("right join"), INNER_JOIN("inner join"), ORDER_BY("order by"), FORM("from"), WHERE("where"), SET("set"), GROUP_BY("group by");

    private String sign;

    private String SQL_FRAGMENT;

    NodeRelation(String sign) {
        this.sign = sign;
        this.SQL_FRAGMENT = " ".concat(this.sign).concat(" ");
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        builder.append(this.SQL_FRAGMENT);
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        toSql(builder, config, false);
    }
}
