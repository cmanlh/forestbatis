package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.NodeRelation;

import java.util.Optional;
import java.util.function.Function;

/**
 * 逻辑与计算
 *
 * @param <T>
 */
public class LogicalAnd<T> extends AbstractQueryNode<T> {
    // 逻辑与计算结果
    private PropertyMeta result;

    /**
     * 构造函数
     *
     * @param column 表字段
     * @param factor 计算因子
     * @param result 计算结果
     */
    public LogicalAnd(ColumnMeta column, PropertyMeta factor, PropertyMeta result) {
        this(column, factor, result, null);
    }

    /**
     * 构造函数
     *
     * @param column      表字段
     * @param factor      计算因子
     * @param result      计算结果
     * @param enableCheck 判断该节点是否参与构建SQL的函数
     */
    public LogicalAnd(ColumnMeta column, PropertyMeta factor, PropertyMeta result, Function<Optional<T>, Boolean> enableCheck) {
        this.column = column;
        this.compareRelation = NodeRelation.LOGICAL_AND;
        this.property = factor;
        this.result = result;
        this.enableCheck = enableCheck;
    }

    @Override
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        builder.append("(");
        this.column.toSql(builder, config, withAlias);
        this.compareRelation.toSql(builder, config, withAlias);
        this.property.toSql(builder, config, withAlias);
        NodeRelation.EQ.toSql(builder, config, withAlias);
        this.result.toSql(builder, config, withAlias);
        builder.append(")");
    }
}
