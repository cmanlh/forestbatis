package com.lifeonwalden.forestbatis.bean;

import java.util.List;

/**
 * 表格信息
 */
public class TableInfo {
    // 表格名称
    private String name;
    // 别名，主要用于多表联合查询场景
    private String alias;
    // 列信息
    private List<ColumnInfo> columnList;

    public String getName() {
        return name;
    }

    public TableInfo setName(String name) {
        this.name = name;

        return this;
    }

    public String getAlias() {
        return alias;
    }

    public TableInfo setAlias(String alias) {
        this.alias = alias;

        return this;
    }

    public List<ColumnInfo> getColumnList() {
        return columnList;
    }

    public TableInfo setColumnList(List<ColumnInfo> columnList) {
        this.columnList = columnList;

        return this;
    }
}
