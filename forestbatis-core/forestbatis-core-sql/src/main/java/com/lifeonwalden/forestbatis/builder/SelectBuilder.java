package com.lifeonwalden.forestbatis.builder;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.constant.NodeRelation;
import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;
import com.lifeonwalden.forestbatis.constant.SqlCommandType;
import com.lifeonwalden.forestbatis.meta.*;
import com.lifeonwalden.forestbatis.parsing.PropertyParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询语句构建器
 */
public class SelectBuilder<T> implements SelectSqlBuilder<T> {
    protected List<ColumnMeta> toReturnColumnList;
    private QueryNode queryNode;
    private Config config;
    private TableNode tableNode;
    private List<? extends OrderBy> orderList;
    private boolean runtimeChangeable;

    private volatile StatementInfo cachedStatement;

    public SelectBuilder(TableNode tableNode, Config config, List<ColumnMeta> toReturnColumnList) {
        this(tableNode, config, toReturnColumnList, null, null);
    }

    public SelectBuilder(TableNode tableNode, Config config, List<ColumnMeta> toReturnColumnList, QueryNode queryNode) {
        this(tableNode, config, toReturnColumnList, queryNode, null);
    }

    public SelectBuilder(TableNode tableNode, Config config, List<ColumnMeta> toReturnColumnList, List<? extends OrderBy> orderList) {
        this(tableNode, config, toReturnColumnList, null, orderList);
    }

    public SelectBuilder(TableNode tableNode, Config config, List<ColumnMeta> toReturnColumnList, QueryNode queryNode, List<? extends OrderBy> orderList) {
        this.tableNode = tableNode;
        this.config = config;
        this.toReturnColumnList = toReturnColumnList;
        this.queryNode = queryNode;
        this.orderList = orderList;

        if (null == this.queryNode) {
            this.runtimeChangeable = false;
        } else {
            this.runtimeChangeable = this.queryNode.isRuntimeChangeable();
        }
    }

    /**
     * 覆盖返回列并构造一个新的SQL构建器
     *
     * @param toReturnColumnList
     * @return
     */
    public SelectBuilder overrideReturnColumn(List<ColumnMeta> toReturnColumnList) {
        return new SelectBuilder<T>(this.tableNode, this.config, toReturnColumnList, this.queryNode, this.orderList);
    }

    /**
     * 在当前返回列基础上剔除指定列并构造一个新的SQL构建器
     *
     * @param excludeReturnColumnList
     * @return
     */
    public SelectBuilder excludeReturnColumn(List<ColumnMeta> excludeReturnColumnList) {
        List<ColumnMeta> _toReturnColumnList = new ArrayList<>();
        this.toReturnColumnList.forEach(columnMeta -> {
            if (!excludeReturnColumnList.contains(columnMeta)) {
                _toReturnColumnList.add(columnMeta);
            }
        });
        return new SelectBuilder<T>(this.tableNode, this.config, _toReturnColumnList, this.queryNode, this.orderList);
    }

    /**
     * 在当前返回列基础上增加指定列并构造一个新的SQL构建器
     *
     * @param toAddReturnColumnList
     * @return
     */
    public SelectBuilder addReturnColumn(List<ColumnMeta> toAddReturnColumnList) {
        List<ColumnMeta> _toReturnColumnList = new ArrayList<>();
        _toReturnColumnList.addAll(this.toReturnColumnList);
        _toReturnColumnList.addAll(toAddReturnColumnList);
        return new SelectBuilder<T>(this.tableNode, this.config, _toReturnColumnList, this.queryNode, this.orderList);
    }

    /**
     * 覆盖排序并构造一个新的SQL构建器
     *
     * @param orderList
     * @return
     */
    public SelectBuilder overrideOrder(List<? extends OrderBy> orderList) {
        return new SelectBuilder<T>(this.tableNode, this.config, this.toReturnColumnList, this.queryNode, orderList);
    }

    /**
     * 覆盖查询条件并构造一个新的SQL构建器
     *
     * @param queryNode
     * @return
     */
    public SelectBuilder overrideQuery(QueryNode queryNode) {
        return new SelectBuilder<T>(this.tableNode, this.config, this.toReturnColumnList, queryNode, this.orderList);
    }

    @Override
    public StatementInfo build(T value) {
        if (this.isRuntimeChangeable() == false && this.cachedStatement != null) {
            return this.cachedStatement;
        }

        StringBuilder builder = new StringBuilder();
        boolean withAlias = this.tableNode.isJoined() || this.queryNode.isJoined();

        SqlCommandType.SELECT.toSql(builder, config, withAlias);
        builder.append(" ");

        if (null == this.toReturnColumnList || this.toReturnColumnList.isEmpty()) {
            builder.append(1);
        } else {
            int index = 0;
            ColumnMeta column = this.toReturnColumnList.get(index);
            column.toSql(builder, this.config, withAlias);
            for (index = 1; index < this.toReturnColumnList.size(); index++) {
                builder.append(", ");
                this.toReturnColumnList.get(index).toSql(builder, this.config, withAlias);
            }
        }

        NodeRelation.FORM.toSql(builder, this.config, withAlias);

        if (null == this.tableNode) {
            throw new RuntimeException("Has to specify a table or join table list for query");
        }
        this.tableNode.toSql(builder, config, withAlias);

        if (null != this.queryNode && QueryNodeEnableType.DISABLED != this.queryNode.enabled(value)) {
            NodeRelation.WHERE.toSql(builder, this.config, withAlias);
            queryNode.toSql(builder, this.config, withAlias, value);
        }

        if (null != this.orderList && this.orderList.size() > 0) {
            NodeRelation.ORDER_BY.toSql(builder, this.config, withAlias);
            int index = 0;
            this.orderList.get(index).toSql(builder, config, withAlias);
            for (index = 1; index < this.orderList.size(); index++) {
                builder.append(", ");
                this.orderList.get(index).toSql(builder, this.config, withAlias);
            }
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
