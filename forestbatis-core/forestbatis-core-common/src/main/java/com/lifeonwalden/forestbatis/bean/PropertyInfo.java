package com.lifeonwalden.forestbatis.bean;

import com.lifeonwalden.forestbatis.constant.JdbcType;

public class PropertyInfo {
    private String name;

    private JdbcType jdbcType;

    private int index;

    private boolean listProperty;

    private int listIndex;

    public String getName() {
        return name;
    }

    public PropertyInfo setName(String name) {
        this.name = name;

        return this;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public PropertyInfo setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;

        return this;
    }

    public int getIndex() {
        return index;
    }

    public PropertyInfo setIndex(int index) {
        this.index = index;

        return this;
    }

    public boolean isListProperty() {
        return listProperty;
    }

    public PropertyInfo setListProperty(boolean listProperty) {
        this.listProperty = listProperty;

        return this;
    }

    public int getListIndex() {
        return listIndex;
    }

    public PropertyInfo setListIndex(int listIndex) {
        this.listIndex = listIndex;

        return this;
    }

    public PropertyInfo newListSibling(int index, int listIndex) {
        PropertyInfo prop = new PropertyInfo();
        prop.name = this.name;
        prop.jdbcType = this.jdbcType;
        prop.listProperty = this.listProperty;
        prop.index = index;
        prop.listIndex = listIndex;

        return prop;
    }
}
