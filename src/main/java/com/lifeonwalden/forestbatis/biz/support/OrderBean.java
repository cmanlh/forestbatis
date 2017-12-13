package com.lifeonwalden.forestbatis.biz.support;

public class OrderBean {
    private String columnName;

    private OrderEnum order;

    public String getColumnName() {
        return columnName;
    }

    public OrderBean setColumnName(String columnName) {
        this.columnName = columnName;

        return this;
    }

    public String getOrder() {
        return order.getOrder();
    }

    public OrderBean setOrder(OrderEnum order) {
        this.order = order;

        return this;
    }
}
