package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.bean.Config;

import javax.sql.DataSource;

public interface SpringMapper {
    void setConfig(Config config);

    void setDataSource(DataSource dataSource);
}
