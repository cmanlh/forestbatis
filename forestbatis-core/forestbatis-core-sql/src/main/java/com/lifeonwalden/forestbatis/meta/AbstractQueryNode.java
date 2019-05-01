package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

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
    public boolean enabled(T value) {
        if (null == enableCheck && (null == this.siblingList || this.siblingList.isEmpty()) && null == this.compoundQuery) {
            return true;
        }

        if (null == enableCheck || enableCheck.apply(null == value ? Optional.empty() : Optional.of(value))) {
            if (null == this.compoundQuery) {
                return true;
            } else {
                return innerNodeIsAble(value);
            }
        } else {
            if (null != this.siblingList && this.siblingList.size() > 0) {
                for (RelationNode<QueryNode> relationNode : this.siblingList) {
                    if (relationNode.getNode().enabled(value)) {
                        return true;
                    }
                }
            }

            return false;
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

    @Override
    public void toSql(StringBuilder builder, boolean withAlias, T value) {
        boolean hadLeadNode = false;
        int nodeCount = 0;
        StringBuilder innerBuilder = new StringBuilder();
        if (null == enableCheck || enableCheck.apply(null == value ? Optional.empty() : Optional.of(value))) {
            if (null == this.compoundQuery) {
                this.column.toSql(innerBuilder, withAlias);
                compareRelation.toSql(innerBuilder, withAlias);
                this.property.toSql(innerBuilder, withAlias);

                hadLeadNode = true;
                nodeCount++;
            } else {
                if (this.compoundQuery.enabled(value)) {
                    StringBuilder compoundBuilder = new StringBuilder();
                    this.compoundQuery.toSql(compoundBuilder, withAlias, value);
                    if (compoundBuilder.length() > 0) {
                        innerBuilder.append("(");
                        innerBuilder.append(compoundBuilder.toString());
                        innerBuilder.append(")");

                        hadLeadNode = true;
                        nodeCount++;
                    }
                }
            }
        }

        if (null != this.siblingList && this.siblingList.size() > 0) {
            for (RelationNode<QueryNode> sibling : this.siblingList) {
                QueryNode queryNode = sibling.getNode();
                if (queryNode.enabled(value)) {
                    nodeCount++;
                    if (hadLeadNode) {
                        sibling.getNodeRelation().toSql(innerBuilder, withAlias);
                    } else {
                        hadLeadNode = true;
                    }
                    queryNode.toSql(innerBuilder, withAlias, value);
                }
            }
        }

        if (1 <= nodeCount) {
            builder.append(innerBuilder.toString());
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

    private boolean innerNodeIsAble(T value) {
        if (this.compoundQuery.enabled(value)) {
            return true;
        } else {
            if (null != this.siblingList && this.siblingList.size() > 0) {
                for (RelationNode<QueryNode> relationNode : this.siblingList) {
                    if (relationNode.getNode().enabled(value)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
