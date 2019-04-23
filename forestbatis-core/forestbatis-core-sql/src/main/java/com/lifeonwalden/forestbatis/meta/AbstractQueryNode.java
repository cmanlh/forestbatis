package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractQueryNode<T> implements QueryNode<T> {
    // 值与字段的比较关系
    protected NodeRelation compareRelation;
    // 表字段
    protected ColumnMeta column;
    // 值
    protected PropertyMeta property;
    // 兄弟条件
    protected List<RelationNode<QueryNode>> siblingList;

    // 该条件是否参与构建SQL语句的判断函数
    protected Function<T, Boolean> enableCheck;

    @Override
    public QueryNode and(QueryNode queryNode) {
        setupSiblingList().add(new RelationNode<>(queryNode, NodeRelation.AND));

        return this;
    }

    @Override
    public QueryNode and(QueryNode... queryNodes) {
        List<RelationNode<QueryNode>> _siblingList = setupSiblingList();
        for (QueryNode queryNode : queryNodes) {
            _siblingList.add(new RelationNode<>(queryNode, NodeRelation.AND));
        }

        return this;
    }

    @Override
    public QueryNode or(QueryNode queryNode) {
        setupSiblingList().add(new RelationNode<>(queryNode, NodeRelation.OR));

        return this;
    }

    @Override
    public QueryNode or(QueryNode... queryNodes) {
        List<RelationNode<QueryNode>> _siblingList = setupSiblingList();
        for (QueryNode queryNode : queryNodes) {
            _siblingList.add(new RelationNode<>(queryNode, NodeRelation.OR));
        }

        return this;
    }

    @Override
    public boolean enabled(T value) {
        if (null != enableCheck) {
            return enableCheck.apply(value);
        }

        return true;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias, T value) {
        if (!enabled(value)) {
            return;
        }

        boolean complex = (null != this.siblingList && this.siblingList.size() > 0);
        if (complex) {
            builder.append("(");
        }

        this.column.toSql(builder, withAlias);
        compareRelation.toSql(builder, withAlias);
        this.property.toSql(builder, withAlias);

        if (complex) {
            siblingList.forEach(sibling -> {
                sibling.getNodeRelation().toSql(builder, withAlias);
                sibling.getNode().toSql(builder, withAlias);
            });

            builder.append(")");
        }
    }

    @Override
    public void toSql(StringBuilder builder, T value) {
        toSql(builder, false, value);
    }

    protected List<RelationNode<QueryNode>> setupSiblingList() {
        if (null == this.siblingList) {
            this.siblingList = new ArrayList<>();
        }

        return this.siblingList;
    }
}
