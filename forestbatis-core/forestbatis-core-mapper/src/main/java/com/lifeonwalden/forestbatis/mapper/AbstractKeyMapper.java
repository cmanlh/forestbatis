package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;

import java.util.Arrays;
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
    public Optional<T> getColumns(T param, ColumnMeta... returnColumnList) {
        SelectBuilder<T> selectBuilder = getGetBuilder();
        if (null != returnColumnList && returnColumnList.length > 0) {
            selectBuilder = selectBuilder.overrideReturnColumn(Arrays.asList(returnColumnList));
        }
        List<T> resultList = select(param, selectBuilder);
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    @Override
    public Optional<T> getWithoutColumns(T param, ColumnMeta... excludeReturnColumnList) {
        SelectBuilder<T> selectBuilder = getGetBuilder();
        if (null != excludeReturnColumnList && excludeReturnColumnList.length > 0) {
            selectBuilder = selectBuilder.excludeReturnColumn(Arrays.asList(excludeReturnColumnList));
        }
        List<T> resultList = select(param, selectBuilder);
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    @Override
    public Integer updateWithoutColumns(T value, ColumnMeta... notUpdateColumnList) {
        UpdateBuilder<T> updateBuilder = getKeyUpdateBuilder(true);
        if (null != notUpdateColumnList && notUpdateColumnList.length > 0) {
            updateBuilder = updateBuilder.excludeUpdateColumn(Arrays.asList(notUpdateColumnList));
        }
        return updateWithQuery(value, updateBuilder);
    }

    @Override
    public Integer updateWithoutColumnsNull(T value, ColumnMeta... notUpdateColumnList) {
        UpdateBuilder<T> updateBuilder = getKeyUpdateBuilder(false);
        if (null != notUpdateColumnList && notUpdateColumnList.length > 0) {
            updateBuilder = updateBuilder.excludeUpdateColumn(Arrays.asList(notUpdateColumnList));
        }
        return updateWithQuery(value, updateBuilder);
    }

    @Override
    public int[] updateWithoutColumns(List<T> valueList, ColumnMeta... notUpdateColumnList) {
        UpdateBuilder<T> updateBuilder = getKeyUpdateBuilder(true);
        if (null != notUpdateColumnList && notUpdateColumnList.length > 0) {
            updateBuilder = updateBuilder.excludeUpdateColumn(Arrays.asList(notUpdateColumnList));
        }
        return updateWithQuery(valueList, updateBuilder);
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
    public Integer updateColumns(T value, ColumnMeta... toUpdateColumnList) {
        UpdateBuilder<T> updateBuilder = getKeyUpdateBuilder(true);
        if (null != toUpdateColumnList && toUpdateColumnList.length > 0) {
            updateBuilder = updateBuilder.overrideUpdateColumn(Arrays.asList(toUpdateColumnList));
        }
        return updateWithQuery(value, updateBuilder);
    }

    @Override
    public Integer updateColumnsWithoutNull(T value, ColumnMeta... toUpdateColumnList) {
        UpdateBuilder<T> updateBuilder = getKeyUpdateBuilder(false);
        if (null != toUpdateColumnList && toUpdateColumnList.length > 0) {
            updateBuilder = updateBuilder.overrideUpdateColumn(Arrays.asList(toUpdateColumnList));
        }
        return updateWithQuery(value, updateBuilder);
    }

    @Override
    public int[] update(List<T> valueList) {
        return updateWithQuery(valueList, getKeyUpdateBuilder(true));
    }

    @Override
    public int[] updateColumns(List<T> valueList, ColumnMeta... toUpdateColumnList) {
        UpdateBuilder<T> updateBuilder = getKeyUpdateBuilder(true);
        if (null != toUpdateColumnList && toUpdateColumnList.length > 0) {
            updateBuilder = updateBuilder.overrideUpdateColumn(Arrays.asList(toUpdateColumnList));
        }
        return updateWithQuery(valueList, updateBuilder);
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
