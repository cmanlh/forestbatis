package com.lifeonwalden.forestbatis.biz.support;

public enum OrderEnum {
    ASC("ASC"), DESC("DESC");
    private String order;

    OrderEnum(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }
}
