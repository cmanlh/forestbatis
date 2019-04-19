package com.lifeonwalden.forestbatis.meta;

/**
 * 表格字段元信息
 */
public class ColumnMetaInfo {
    // 字段在所处执行语句上下文中的下标值
    private int index;
    // 字段的jdbc类型
    private int JdbcType;
    // 字段名
    private String columnLabel;
    // 字段对应的java数据类型
    private int javaType;
    // 字段对应的java类属性名
    private String propertyName;

    public int getIndex() {
        return index;
    }

    public ColumnMetaInfo setIndex(int index) {
        this.index = index;

        return this;
    }

    public int getJdbcType() {
        return JdbcType;
    }

    public ColumnMetaInfo setJdbcType(int jdbcType) {
        JdbcType = jdbcType;

        return this;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public ColumnMetaInfo setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;

        return this;
    }

    public int getJavaType() {
        return javaType;
    }

    public ColumnMetaInfo setJavaType(int javaType) {
        this.javaType = javaType;

        return this;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public ColumnMetaInfo setPropertyName(String propertyName) {
        this.propertyName = propertyName;

        return this;
    }
}
