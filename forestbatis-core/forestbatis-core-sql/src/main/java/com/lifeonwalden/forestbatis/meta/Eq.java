package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.NodeRelation;

import java.util.Optional;
import java.util.function.Function;

/**
 * 等于
 *
 * @param <T>
 */
public class Eq<T> extends AbstractQueryNode<T> {
    // 另一表的关联字段
    protected ColumnMeta anotherTableColumn;

    /**
     * 构造函数
     *
     * @param column             表字段
     * @param anotherTableColumn 另一表的字段
     */
    public Eq(ColumnMeta column, ColumnMeta anotherTableColumn) {
        this.column = column;
        this.anotherTableColumn = anotherTableColumn;
        this.compareRelation = NodeRelation.EQ;
    }

    /**
     * 构造函数
     *
     * @param column   表字段
     * @param property 值属性
     */
    public Eq(ColumnMeta column, PropertyMeta property) {
        this(column, property, null);
    }

    /**
     * 构造函数
     *
     * @param column 表字段
     */
    public Eq(ColumnMeta column) {
        if (column.getJavaProperty().isPresent()) {
            this.column = column;
            this.property = column.getJavaProperty().get();
            this.compareRelation = NodeRelation.EQ;
        } else {
            throw new RuntimeException("Has to specify a java property for column");
        }
    }

    /**
     * 构造函数
     *
     * @param column      表字段
     * @param property    值属性
     * @param enableCheck 判断该节点是否参与构建SQL的函数
     */
    public Eq(ColumnMeta column, PropertyMeta property, Function<Optional<T>, Boolean> enableCheck) {
        this.column = column;
        this.property = property;
        this.compareRelation = NodeRelation.EQ;
        this.enableCheck = enableCheck;
    }

    /**
     * 构造函数
     *
     * @param column      表字段
     * @param enableCheck 判断该节点是否参与构建SQL的函数
     */
    public Eq(ColumnMeta column, Function<Optional<T>, Boolean> enableCheck) {
        if (column.getJavaProperty().isPresent()) {
            this.column = column;
            this.property = column.getJavaProperty().get();
            this.compareRelation = NodeRelation.EQ;
            this.enableCheck = enableCheck;
        } else {
            throw new RuntimeException("Has to specify a java property for column");
        }
    }

    @Override
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias, T value) {
        if (null != this.anotherTableColumn) {
            this.column.toSql(builder, config, withAlias);
            compareRelation.toSql(builder, config, withAlias);
            this.anotherTableColumn.toSql(builder, config, withAlias);
        } else {
            super.selfBuild(builder, config, withAlias, value);
        }
    }
}
