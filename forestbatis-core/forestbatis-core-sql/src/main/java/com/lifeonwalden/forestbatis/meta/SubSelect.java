package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.NodeRelation;
import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;
import com.lifeonwalden.forestbatis.constant.SqlCommandType;

import java.util.ArrayList;
import java.util.List;

/**
 * 子查询
 *
 * @param <T> 值类型
 */
public class SubSelect<T> implements ValueBindingSqlNode<T> {
    private List<ColumnMeta> toReturnColumnList = new ArrayList<>();
    private QueryNode queryNode;
    private TableNode tableNode;
    private List<Order> orderList;

    public SubSelect setTableNode(TableNode tableNode) {
        this.tableNode = tableNode;

        return this;
    }

    public SubSelect fetchColumn(ColumnMeta columnMeta) {
        toReturnColumnList.add(columnMeta);

        return this;
    }

    public SubSelect fetchColumn(ColumnMeta... columnMetas) {
        for (ColumnMeta columnMeta : columnMetas) {
            toReturnColumnList.add(columnMeta);
        }

        return this;
    }

    public SubSelect setQuery(QueryNode queryNode) {
        this.queryNode = queryNode;

        return this;
    }

    public SubSelect setOrderBy(Order... orders) {
        for (Order order : orders) {
            this.orderList.add(order);
        }

        return this;
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias, T value) {
        SqlCommandType.SELECT.toSql(builder, config, withAlias);
        builder.append(" ");

        if (null == this.toReturnColumnList || this.toReturnColumnList.isEmpty()) {
            builder.append(1);
        } else {
            int index = 0;
            ColumnMeta column = this.toReturnColumnList.get(index);
            column.toSql(builder, config, withAlias);
            for (index = 1; index < this.toReturnColumnList.size(); index++) {
                builder.append(", ");
                this.toReturnColumnList.get(index).toSql(builder, config, withAlias);
            }
        }

        NodeRelation.FORM.toSql(builder, config, withAlias);

        if (null == this.tableNode) {
            throw new RuntimeException("Has to specify a table or join table list for query");
        }
        this.tableNode.toSql(builder, config, withAlias);

        if (null != this.queryNode && QueryNodeEnableType.DISABLED != this.queryNode.enabled(value)) {
            NodeRelation.WHERE.toSql(builder, config, withAlias);
            queryNode.toSql(builder, config, withAlias, value);
        }

        if (null != this.orderList && this.orderList.size() > 0) {
            NodeRelation.ORDER_BY.toSql(builder, config, withAlias);
            int index = 0;
            this.orderList.get(index).toSql(builder, config, withAlias);
            for (index = 1; index < this.orderList.size(); index++) {
                builder.append(", ");
                this.orderList.get(index).toSql(builder, config, withAlias);
            }
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config, T value) {
        toSql(builder, config, false, value);
    }

    public boolean isRuntimeChangeable() {
        if (null == this.queryNode) {
            return false;
        }

        return this.queryNode.isRuntimeChangeable();
    }
}
