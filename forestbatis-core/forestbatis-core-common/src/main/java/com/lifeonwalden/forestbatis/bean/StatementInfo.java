package com.lifeonwalden.forestbatis.bean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StatementInfo {
    private Optional<List<PropertyInfo>> props = Optional.empty();

    private Optional<List<ColumnInfo>> returnColumns = Optional.empty();

    private String sql;

    private String debugSql;

    public Optional<List<ColumnInfo>> getReturnColumns() {
        return returnColumns;
    }

    public StatementInfo setReturnColumns(Optional<List<ColumnInfo>> returnColumns) {
        this.returnColumns = returnColumns;

        return this;
    }

    public Optional<List<PropertyInfo>> getProps() {
        return props;
    }

    public StatementInfo setProps(Optional<List<PropertyInfo>> props) {
        this.props = props;

        return this;
    }

    public String getSql() {
        return sql;
    }

    public StatementInfo setSql(String sql) {
        this.sql = sql;

        return this;
    }

    public String getDebugSql() {
        return debugSql;
    }

    public StatementInfo setDebugSql(String debugSql) {
        this.debugSql = debugSql;

        return this;
    }
}
