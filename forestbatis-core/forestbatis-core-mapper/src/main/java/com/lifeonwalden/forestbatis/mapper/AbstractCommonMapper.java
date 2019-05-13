package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.result.RecordHandler;
import com.lifeonwalden.forestbatis.result.StreamResultSetCallback;
import com.lifeonwalden.forestbatis.sql.DeleteBuilder;
import com.lifeonwalden.forestbatis.sql.InsertBuilder;
import com.lifeonwalden.forestbatis.sql.SelectBuilder;
import com.lifeonwalden.forestbatis.sql.UpdateBuilder;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractCommonMapper<T> implements CommonMapper<T> {
    protected abstract Connection getConnection();

    @Override
    public List<T> select(T param) {
        Connection connection = getConnection();

        return null;
    }

    @Override
    public List<T> select(T param, StreamResultSetCallback streamResultSetCallback) {
        return null;
    }

    @Override
    public List<T> select(T param, SelectBuilder selectBuilder) {
        return null;
    }

    @Override
    public List<T> select(T param, SelectBuilder selectBuilder, StreamResultSetCallback streamResultSetCallback) {
        return null;
    }

    @Override
    public List<T> select(T param, RecordHandler resultHandler) {
        return null;
    }

    @Override
    public List<T> select(T param, RecordHandler resultHandler, StreamResultSetCallback streamResultSetCallback) {
        return null;
    }

    @Override
    public List<T> select(T param, SelectBuilder selectBuilder, RecordHandler resultHandler) {
        return null;
    }

    @Override
    public void select(T param, SelectBuilder selectBuilder, RecordHandler resultHandler, StreamResultSetCallback streamResultSetCallback) {

    }

    @Override
    public Integer updateWithQuery(T value, T param) {
        return null;
    }

    @Override
    public Integer updateWithQuery(T value, T param, UpdateBuilder updateBuilder) {
        return null;
    }

    @Override
    public List<Integer> updateWithQuery(List<T> valueList, List<T> paramList) {
        return null;
    }

    @Override
    public List<Integer> updateWithQuery(List<T> valueList, List<T> paramList, UpdateBuilder updateBuilder) {
        return null;
    }

    @Override
    public Integer deleteWithQuery(T param) {
        return null;
    }

    @Override
    public Integer deleteWithQuery(T param, DeleteBuilder deleteBuilder) {
        return null;
    }

    @Override
    public List<Integer> deleteWithQuery(List<T> paramList) {
        return null;
    }

    @Override
    public List<Integer> deleteWithQuery(List<T> paramList, DeleteBuilder deleteBuilder) {
        return null;
    }

    @Override
    public Integer insert(T value) {
        return null;
    }

    @Override
    public Integer insert(T value, InsertBuilder insertBuilder) {
        return null;
    }

    @Override
    public List<Integer> insert(List<T> valueList) {
        return null;
    }

    @Override
    public List<Integer> insert(List<T> valueList, InsertBuilder insertBuilder) {
        return null;
    }

    @Override
    public Integer truncate() {
        return null;
    }
}
