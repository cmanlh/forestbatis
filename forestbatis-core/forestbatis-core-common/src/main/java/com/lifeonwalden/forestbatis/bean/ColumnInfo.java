package com.lifeonwalden.forestbatis.bean;

import com.lifeonwalden.forestbatis.constant.JdbcType;

/**
 * 表格字段信息
 */
public class ColumnInfo {
    // 字段在所处执行语句上下文中的下标值
    private int index;
    // 字段的jdbc类型
    private JdbcType jdbcType;
    // 字段名
    private String label;
    // 字段对应的java数据类型
    private int javaType;
    // 字段对应的java类属性名
    private String propertyName;

    public int getIndex() {
        return index;
    }

    public ColumnInfo setIndex(int index) {
        this.index = index;

        return this;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public ColumnInfo setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;

        return this;
    }

    public String getLabel() {
        return label;
    }

    public ColumnInfo setLabel(String label) {
        this.label = label;

        return this;
    }

    public int getJavaType() {
        return javaType;
    }

    public ColumnInfo setJavaType(int javaType) {
        this.javaType = javaType;

        return this;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public ColumnInfo setPropertyName(String propertyName) {
        this.propertyName = propertyName;

        return this;
    }
}
