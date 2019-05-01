package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

import java.util.Optional;
import java.util.function.Function;

/**
 * is null
 *
 * @param <T>
 */
public class IsNull<T> extends AbstractQueryNode<T> {
    /**
     * 构造函数
     *
     * @param column 表字段
     */
    public IsNull(ColumnMeta column) {
        this(column, null);
    }

    /**
     * 构造函数
     *
     * @param column      表字段
     * @param enableCheck 判断该节点是否参与构建SQL的函数
     */
    public IsNull(ColumnMeta column, Function<Optional<T>, Boolean> enableCheck) {
        this.column = column;
        this.compareRelation = NodeRelation.IS_NULL;
        this.enableCheck = enableCheck;
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

        if (complex) {
            siblingList.forEach(sibling -> {
                sibling.getNodeRelation().toSql(builder, withAlias);
                sibling.getNode().toSql(builder, withAlias);
            });

            builder.append(")");
        }
    }
}
