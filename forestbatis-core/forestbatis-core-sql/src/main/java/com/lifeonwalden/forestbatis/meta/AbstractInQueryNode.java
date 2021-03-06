package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
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
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        this.column.toSql(builder, config, withAlias);
        this.compareRelation.toSql(builder, config);
        builder.append("(");
        ((AbstractPropertyMeta) this.property).toSql(builder, config, withAlias, fetchListSize(value));
        builder.append(")");
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

    @Override
    public boolean isRuntimeChangeable() {
        return true;
    }
}
