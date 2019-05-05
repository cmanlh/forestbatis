package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AbstractInQueryNode<T> extends AbstractQueryNode<T> {

    @Override
    public QueryNodeEnableType enabled(T value) {
        int listSize = fetchListSize(value);
        if ((null == enableCheck || enableCheck.apply(null == value ? Optional.empty() : Optional.of(value))) && listSize > 0) {
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

    @Override
    protected void selfBuild(StringBuilder builder, boolean withAlias, T value) {
        this.column.toSql(builder, withAlias);
        this.compareRelation.toSql(builder);
        ((AbstractPropertyMeta) this.property).toSql(builder, withAlias, fetchListSize(value));
    }

    private int fetchListSize(T value) {
        if (null != value && value instanceof Map) {
            Object list = ((Map) value).get(this.property.getName());
            if (null != list && list instanceof List) {
                return ((List) list).size();
            }
        }

        return 0;
    }
}
