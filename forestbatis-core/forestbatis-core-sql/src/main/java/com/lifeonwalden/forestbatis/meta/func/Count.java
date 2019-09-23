package com.lifeonwalden.forestbatis.meta.func;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.PropertyMeta;
import com.lifeonwalden.forestbatis.meta.TableMeta;

import java.util.Optional;

public class Count implements ColumnMeta {


    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public Optional<String> getAlias() {
        return Optional.empty();
    }

    @Override
    public Optional<JdbcType> getJdbcType() {
        return Optional.empty();
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public Optional<TableMeta> getTable() {
        return Optional.empty();
    }

    @Override
    public Optional<PropertyMeta> getJavaProperty() {
        return Optional.empty();
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {

    }

    @Override
    public void toSql(StringBuilder builder, Config config) {

    }
}
