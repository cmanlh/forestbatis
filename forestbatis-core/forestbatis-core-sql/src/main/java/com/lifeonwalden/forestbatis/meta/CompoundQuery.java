package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;

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

    @Override
    public QueryNodeEnableType enabled(T value) {
        if ((null == enableCheck || enableCheck.apply(null == value ? Optional.empty() : Optional.of(value)))
                && QueryNodeEnableType.DISABLED != this.compoundQuery.enabled(value)) {
            return QueryNodeEnableType.NODE;
        } else {
            if (null != this.siblingList && this.siblingList.size() > 0) {
                for (RelationNode<QueryNode> relationNode : this.siblingList) {
                    if (QueryNodeEnableType.DISABLED != relationNode.getNode().enabled(value)) {
                        return QueryNodeEnableType.SIBLING_ONLY;
                    }
                }
            }

            return QueryNodeEnableType.DISABLED;
        }
    }

    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        builder.append("(");
        this.compoundQuery.toSql(builder, config, withAlias, value);
        builder.append(")");
    }
}
