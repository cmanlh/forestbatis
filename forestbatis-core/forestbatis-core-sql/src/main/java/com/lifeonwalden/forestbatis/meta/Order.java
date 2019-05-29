package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.OrderEnum;

/**
 * order by 节点
 */
public class Order implements SqlNode {

    // 排序的列
    private ColumnMeta column;

    // 排序的方式
    private OrderEnum order;

    public Order(ColumnMeta column, OrderEnum order) {
        this.column = column;
        this.order = order;
    }

    public Order(ColumnMeta column) {
        this(column, OrderEnum.ASC);
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        this.column.toSql(builder, config, withAlias);
        builder.append(" ");
        this.order.toSql(builder, config, withAlias);
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        toSql(builder, config, false);
    }
}
