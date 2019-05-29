package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;

import java.util.List;
import java.util.Optional;

/**
 * 临时表的元信息
 */
public class TempTable<T> implements TableMeta, ValueBindingSqlNode<T> {
    // 临时表别名
    private String alias;

    // 临时表查询语句
    private SubSelect subSelect;

    public TempTable(String alias, SubSelect subSelect) {
        this.alias = alias;
        this.subSelect = subSelect;
    }

    @Override
    public String getName() {
        return this.alias;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public Optional<List<ColumnMeta>> getColumn() {
        return Optional.empty();
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        toSql(builder, config, withAlias, null);
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        toSql(builder, config, true, null);
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias, T value) {
        this.subSelect.toSql(builder, config, withAlias, value);
    }

    @Override
    public void toSql(StringBuilder builder, Config config, T value) {
        toSql(builder, config, true, value);
    }

    public boolean isRuntimeChangeable() {
        return this.subSelect.isRuntimeChangeable();
    }
}
