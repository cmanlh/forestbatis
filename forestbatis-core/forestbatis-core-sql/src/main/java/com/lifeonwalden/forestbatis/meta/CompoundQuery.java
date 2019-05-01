package com.lifeonwalden.forestbatis.meta;

import java.util.Optional;
import java.util.function.Function;

public class CompoundQuery<T> extends AbstractQueryNode<T> {
    /**
     * 构建组合查询条件
     *
     * @param compoundQuery
     */
    public CompoundQuery(QueryNode compoundQuery) {
        this.compoundQuery = compoundQuery;
    }

    /**
     * 构建组合查询条件
     *
     * @param compoundQuery 组合查询条件
     * @param enableCheck   判断该节点是否参与构建SQL的函数
     */
    public CompoundQuery(QueryNode compoundQuery, Function<Optional<T>, Boolean> enableCheck) {
        this.compoundQuery = compoundQuery;
        this.enableCheck = enableCheck;
    }
}
