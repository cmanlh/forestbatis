package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.bean.Config;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

public abstract class AbstractSpringKeyMapper<T> extends AbstractKeyMapper<T> implements SpringMapper {
    protected Config config;
    protected DataSource dataSource;

    @Override
    protected Config getConfig() {
        return this.config;
    }

    @Override
    protected Connection getConnection() {
        return DataSourceUtils.getConnection(this.dataSource);
    }

    @Override
    protected void releaseConnection(Connection connection) {
        DataSourceUtils.releaseConnection(connection, this.dataSource);
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
