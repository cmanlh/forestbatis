package com.lifeonwalden.forestbatis.builder;

import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.constant.NodeRelation;
import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;
import com.lifeonwalden.forestbatis.constant.SqlCommandType;
import com.lifeonwalden.forestbatis.meta.QueryNode;
import com.lifeonwalden.forestbatis.meta.TableNode;
import com.lifeonwalden.forestbatis.parsing.PropertyParser;

/**
 * 删除语句构建器
 */
public class DeleteBuilder<T> implements com.lifeonwalden.forestbatis.sql.DeleteBuilder<T> {
    private QueryNode queryNode;
    private TableNode tableNode;
    private boolean runtimeChangeable;

    private volatile StatementInfo cachedStatement;

    public DeleteBuilder(TableNode tableNode) {
        this(tableNode, null);
    }

    public DeleteBuilder(TableNode tableNode, QueryNode queryNode) {
        this.tableNode = tableNode;
        this.queryNode = queryNode;
        if (null == queryNode) {
            this.runtimeChangeable = false;
        } else {
            this.runtimeChangeable = this.queryNode.isRuntimeChangeable();
        }
    }

    /**
     * 覆盖查询条件并构造一个新的SQL构建器
     *
     * @param queryNode
     * @return
     */
    public DeleteBuilder overrideQuery(QueryNode queryNode) {
        return new DeleteBuilder<T>(this.tableNode, queryNode);
    }

    @Override
    public StatementInfo build(T value) {
        if (this.isRuntimeChangeable() == false && this.cachedStatement != null) {
            return this.cachedStatement;
        }

        StringBuilder builder = new StringBuilder();
        boolean withAlias = this.tableNode.isJoined() || this.queryNode.isJoined();

        SqlCommandType.DELETE.toSql(builder, withAlias);
        NodeRelation.FORM.toSql(builder, withAlias);
        this.tableNode.toSql(builder, withAlias);

        if (null != this.queryNode && QueryNodeEnableType.DISABLED != this.queryNode.enabled(value)) {
            NodeRelation.WHERE.toSql(builder, withAlias);
            queryNode.toSql(builder, withAlias, value);
        }

        if (false == this.isRuntimeChangeable()) {
            this.cachedStatement = PropertyParser.parse(builder.toString());
            return this.cachedStatement;
        } else {
            return PropertyParser.parse(builder.toString());
        }
    }

    @Override
    public StatementInfo build() {
        return build(null);
    }

    @Override
    public boolean isRuntimeChangeable() {
        return this.runtimeChangeable;
    }
}
