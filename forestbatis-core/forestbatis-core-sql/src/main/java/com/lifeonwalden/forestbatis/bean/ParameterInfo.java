package com.lifeonwalden.forestbatis.bean;

import java.util.Optional;

public class ParameterInfo {
    private Optional<PropertyInfo[]> props;

    private String sql;

    private String debugSql;

    public Optional<PropertyInfo[]> getProps() {
        return props;
    }

    public ParameterInfo setProps(Optional<PropertyInfo[]> props) {
        this.props = props;

        return this;
    }

    public String getSql() {
        return sql;
    }

    public ParameterInfo setSql(String sql) {
        this.sql = sql;

        return this;
    }

    public String getDebugSql() {
        return debugSql;
    }

    public ParameterInfo setDebugSql(String debugSql) {
        this.debugSql = debugSql;

        return this;
    }
}
