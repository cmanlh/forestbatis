package com.lifeonwalden.forestbatis.result;

public class ColumnMetaData {
    private int index;
    private int JdbcType;
    private String columnLabel;

    public int getIndex() {
        return index;
    }

    public ColumnMetaData setIndex(int index) {
        this.index = index;

        return this;
    }

    public int getJdbcType() {
        return JdbcType;
    }

    public ColumnMetaData setJdbcType(int jdbcType) {
        JdbcType = jdbcType;

        return this;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public ColumnMetaData setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;

        return this;
    }
}
