package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.NodeRelation;
import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    // 组合查询条件
    protected QueryNode compoundQuery;

    // 该条件是否参与构建SQL语句的判断函数
    protected Function<Optional<T>, Boolean> enableCheck;

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
    public QueryNodeEnableType enabled(T value) {
        if (null == enableCheck || enableCheck.apply(null == value ? Optional.empty() : Optional.of(value))) {
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
    public boolean hasSubQuery() {
        return false;
    }

    @Override
    public boolean isJoined() {
        if (this.hasSubQuery()) {
            return true;
        }

        if (null == this.siblingList || this.siblingList.isEmpty()) {
            return false;
        } else {
            for (RelationNode<QueryNode> node : this.siblingList) {
                if (node.getNode().isJoined()) {
                    return true;
                }
            }

            return false;
        }
    }

    protected void toSql(StringBuilder builder, Config config, boolean withAlias, T value, boolean hasLeadNode) {
        boolean hadLeadNode = hasLeadNode;
        int nodeCount = 0;
        StringBuilder innerBuilder = new StringBuilder();
        if (QueryNodeEnableType.NODE == enabled(value)) {
            selfBuild(innerBuilder, config, withAlias, value);

            hadLeadNode = true;
            nodeCount++;
        }

        if (null != this.siblingList && this.siblingList.size() > 0) {
            for (RelationNode<QueryNode> sibling : this.siblingList) {
                AbstractQueryNode queryNode = (AbstractQueryNode) sibling.getNode();
                switch (queryNode.enabled(value)) {
                    case NODE: {
                        nodeCount++;
                        if (hadLeadNode) {
                            sibling.getNodeRelation().toSql(innerBuilder, config, withAlias);
                        }
                        queryNode.toSql(innerBuilder, config, withAlias, value, hadLeadNode);
                        hadLeadNode = true;

                        break;
                    }
                    case SIBLING_ONLY: {
                        nodeCount++;
                        queryNode.toSql(innerBuilder, config, withAlias, value, hadLeadNode);
                        hadLeadNode = true;

                        break;
                    }
                }
            }
        }

        if (0 < nodeCount) {
            builder.append(innerBuilder.toString());
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias, T value) {
        toSql(builder, config, withAlias, value, false);
    }

    @Override
    public void toSql(StringBuilder builder, Config config, T value) {
        toSql(builder, config, false, value);
    }

    protected List<RelationNode<QueryNode>> setupSiblingList() {
        if (null == this.siblingList) {
            this.siblingList = new ArrayList<>();
        }

        return this.siblingList;
    }

    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        this.column.toSql(builder, config, withAlias);
        compareRelation.toSql(builder, config, withAlias);
        this.property.toSql(builder, config, withAlias);
    }

    @Override
    public boolean isRuntimeChangeable() {
        if (null != this.enableCheck) {
            return true;
        }

        if (null != this.siblingList && !this.siblingList.isEmpty()) {
            for (RelationNode<QueryNode> node : this.siblingList) {
                if (node.getNode().isRuntimeChangeable()) {
                    return true;
                }
            }
        }

        return false;
    }
}
