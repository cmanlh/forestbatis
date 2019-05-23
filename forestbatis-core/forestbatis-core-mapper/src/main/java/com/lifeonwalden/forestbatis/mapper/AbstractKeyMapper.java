package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;

import java.util.List;
import java.util.Optional;

public abstract class AbstractKeyMapper<T> extends AbstractCommonMapper<T> implements KeyBasedMapper<T> {

    protected abstract DeleteBuilder<T> getKeyDeleteBuilder();

    protected abstract UpdateBuilder<T> getKeyUpdateBuilder(boolean withNull);

    protected abstract SelectBuilder<T> getGetBuilder();

    @Override
    public Optional<T> get(T param) {
        List<T> resultList = select(param, getGetBuilder());
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    @Override
    public Optional<T> get(T param, List<ColumnMeta> excludeReturnColumnList) {
        List<T> resultList = select(param, getGetBuilder().excludeReturnColumn(excludeReturnColumnList));
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    @Override
    public Integer update(T value) {
        return updateWithQuery(value, getKeyUpdateBuilder(true));
    }

    @Override
    public Integer updateWithoutNull(T value) {
        return updateWithQuery(value, getKeyUpdateBuilder(false));
    }

    @Override
    public Integer update(T value, List<ColumnMeta> toUpdateColumnList) {
        return updateWithQuery(value, getKeyUpdateBuilder(true).overrideUpdateColumn(toUpdateColumnList));
    }

    @Override
    public Integer updateWithoutNull(T value, List<ColumnMeta> toUpdateColumnList) {
        return updateWithQuery(value, getKeyUpdateBuilder(false).overrideUpdateColumn(toUpdateColumnList, false));
    }

    @Override
    public int[] update(List<T> valueList) {
        return updateWithQuery(valueList, getKeyUpdateBuilder(true));
    }

    @Override
    public int[] updateWithoutNull(List<T> valueList) {
        return updateWithQuery(valueList, getKeyUpdateBuilder(false));
    }

    @Override
    public int[] update(List<T> valueList, List<ColumnMeta> toUpdateColumnList) {
        return updateWithQuery(valueList, getKeyUpdateBuilder(true).overrideUpdateColumn(toUpdateColumnList));
    }

    @Override
    public int[] updateWithoutNull(List<T> valueList, List<ColumnMeta> toUpdateColumnList) {
        return updateWithQuery(valueList, getKeyUpdateBuilder(false).overrideUpdateColumn(toUpdateColumnList, false));
    }

    @Override
    public Integer delete(T param) {
        return deleteWithQuery(param, getKeyDeleteBuilder());
    }

    @Override
    public int[] delete(List<T> paramList) {
        return deleteWithQuery(paramList, getKeyDeleteBuilder());
    }
}
