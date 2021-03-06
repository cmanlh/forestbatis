package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
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
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        this.column.toSql(builder, config, withAlias);
        compareRelation.toSql(builder, config, withAlias);
    }
}
