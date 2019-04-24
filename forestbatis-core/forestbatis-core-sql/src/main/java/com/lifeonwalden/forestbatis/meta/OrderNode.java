package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.OrderEnum;

/**
 * order by 节点
 */
public class OrderNode implements SqlNode {

    // 排序的列
    private ColumnMeta column;

    // 排序的方式
    private OrderEnum order;

    public OrderNode(ColumnMeta column, OrderEnum order) {
        this.column = column;
        this.order = order;
    }

    public OrderNode(ColumnMeta column) {
        this(column, OrderEnum.ASC);
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        this.column.toSql(builder, withAlias);
        builder.append(" ");
        this.order.toSql(builder, withAlias);
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder, false);
    }
}
