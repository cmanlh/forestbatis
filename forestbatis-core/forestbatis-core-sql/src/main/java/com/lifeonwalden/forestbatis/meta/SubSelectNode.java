package com.lifeonwalden.forestbatis.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * 子查询
 *
 * @param <T> 值类型
 */
public class SubSelectNode<T> implements ValueBindingSqlNode<T> {
    private List<ColumnMeta> toReturnColumnList = new ArrayList<>();
    private QueryNode queryNode;

    SubSelectNode fetchColumn(ColumnMeta columnMeta) {
        toReturnColumnList.add(columnMeta);

        return this;
    }

    SubSelectNode fetchColumn(ColumnMeta... columnMetas) {
        for (ColumnMeta columnMeta : columnMetas) {
            toReturnColumnList.add(columnMeta);
        }

        return this;
    }

    SubSelectNode setQuery(QueryNode queryNode) {
        this.queryNode = queryNode;

        return this;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias, T value) {

    }

    @Override
    public void toSql(StringBuilder builder, T value) {

    }
}
