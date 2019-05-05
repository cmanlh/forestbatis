package com.lifeonwalden.forestbatis.builder;

import com.lifeonwalden.forestbatis.constant.NodeRelation;
import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;
import com.lifeonwalden.forestbatis.constant.SqlCommandType;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.Order;
import com.lifeonwalden.forestbatis.meta.QueryNode;
import com.lifeonwalden.forestbatis.meta.TableNode;
import com.lifeonwalden.forestbatis.sql.SQLBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL构建参数
 */
public class SelectBuilder<T> implements SQLBuilder<T> {
    protected List<ColumnMeta> toReturnColumnList;
    private QueryNode queryNode;
    private TableNode tableNode;
    private List<Order> orderList;

    public SelectBuilder(List<ColumnMeta> toReturnColumnList, TableNode tableNode) {
        this(toReturnColumnList, tableNode, null, null);
    }

    public SelectBuilder(List<ColumnMeta> toReturnColumnList, TableNode tableNode, QueryNode queryNode) {
        this(toReturnColumnList, tableNode, queryNode, null);
    }

    public SelectBuilder(List<ColumnMeta> toReturnColumnList, TableNode tableNode, List<Order> orderList) {
        this(toReturnColumnList, tableNode, null, orderList);
    }

    public SelectBuilder(List<ColumnMeta> toReturnColumnList, TableNode tableNode, QueryNode queryNode, List<Order> orderList) {
        this.toReturnColumnList = toReturnColumnList;
        this.tableNode = tableNode;
        this.queryNode = queryNode;
        this.orderList = orderList;
    }

    /**
     * 覆盖返回列并构造一个新的SQL构建器
     *
     * @param toReturnColumnList
     * @return
     */
    public SelectBuilder overrideReturnColumn(List<ColumnMeta> toReturnColumnList) {
        return new SelectBuilder<T>(toReturnColumnList, this.tableNode, this.queryNode, this.orderList);
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
        return new SelectBuilder<T>(_toReturnColumnList, this.tableNode, this.queryNode, this.orderList);
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
        return new SelectBuilder<T>(_toReturnColumnList, this.tableNode, this.queryNode, this.orderList);
    }

    /**
     * 覆盖排序并构造一个新的SQL构建器
     *
     * @param orderList
     * @return
     */
    public SelectBuilder overrideOrder(List<Order> orderList) {
        return new SelectBuilder<T>(this.toReturnColumnList, this.tableNode, this.queryNode, orderList);
    }

    /**
     * 覆盖查询条件并构造一个新的SQL构建器
     *
     * @param queryNode
     * @return
     */
    public SelectBuilder overrideQuery(QueryNode queryNode) {
        return new SelectBuilder<T>(this.toReturnColumnList, this.tableNode, queryNode, this.orderList);
    }

    @Override
    public String build(T value) {
        StringBuilder builder = new StringBuilder();
        boolean withAlias = this.tableNode.isJoined() || this.queryNode.isJoined();

        SqlCommandType.SELECT.toSql(builder, withAlias);

        if (null == this.toReturnColumnList || this.toReturnColumnList.isEmpty()) {
            builder.append(1);
        } else {
            int index = 0;
            ColumnMeta column = this.toReturnColumnList.get(index);
            column.toSql(builder, withAlias);
            for (index = 1; index < this.toReturnColumnList.size(); index++) {
                builder.append(", ");
                this.toReturnColumnList.get(index).toSql(builder, withAlias);
            }
        }

        NodeRelation.FORM.toSql(builder, withAlias);

        if (null == this.tableNode) {
            throw new RuntimeException("Has to specify a table or join table list for query");
        }
        this.tableNode.toSql(builder, withAlias);

        if (null != this.queryNode && QueryNodeEnableType.DISABLED != this.queryNode.enabled(value)) {
            NodeRelation.WHERE.toSql(builder, withAlias);
            queryNode.toSql(builder, withAlias, value);
        }

        if (null != this.orderList && this.orderList.size() > 0) {
            NodeRelation.ORDER_BY.toSql(builder, withAlias);
            int index = 0;
            this.orderList.get(index).toSql(builder, withAlias);
            for (index = 1; index < this.orderList.size(); index++) {
                builder.append(", ");
                this.orderList.get(index).toSql(builder, withAlias);
            }
        }

        return builder.toString();
    }

    @Override
    public String build() {
        return build(null);
    }
}
