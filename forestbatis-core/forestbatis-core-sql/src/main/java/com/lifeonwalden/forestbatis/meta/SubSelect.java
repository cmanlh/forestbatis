package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;
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

    SubSelect setTableNode(TableNode tableNode) {
        this.tableNode = tableNode;

        return this;
    }

    SubSelect fetchColumn(ColumnMeta columnMeta) {
        toReturnColumnList.add(columnMeta);

        return this;
    }

    SubSelect fetchColumn(ColumnMeta... columnMetas) {
        for (ColumnMeta columnMeta : columnMetas) {
            toReturnColumnList.add(columnMeta);
        }

        return this;
    }

    SubSelect setQuery(QueryNode queryNode) {
        this.queryNode = queryNode;

        return this;
    }

    SubSelect setOrderBy(Order... orders) {
        for (Order order : orders) {
            this.orderList.add(order);
        }

        return this;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias, T value) {
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

        if (null != this.queryNode && this.queryNode.enabled(value)) {
            NodeRelation.WHERE.toSql(builder, withAlias);
            builder.append(" ");
            queryNode.toSql(builder, withAlias, value);
        }

        if (null != this.orderList && this.orderList.size() > 0) {
            NodeRelation.ORDER_BY.toSql(builder, withAlias);
            builder.append(" ");
            int index = 0;
            this.orderList.get(index).toSql(builder, withAlias);
            for (index = 1; index < this.orderList.size(); index++) {
                builder.append(", ");
                this.orderList.get(index).toSql(builder, withAlias);
            }
        }
    }

    @Override
    public void toSql(StringBuilder builder, T value) {
        toSql(builder, false, value);
    }
}
