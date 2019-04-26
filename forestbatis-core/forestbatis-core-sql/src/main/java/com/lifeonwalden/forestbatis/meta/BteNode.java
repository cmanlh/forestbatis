package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

import java.util.function.Function;

/**
 * 大于等于
 *
 * @param <T>
 */
public class BteNode<T> extends AbstractQueryNode<T> {

    /**
     * 构造函数
     *
     * @param column   表字段
     * @param property 值属性
     */
    public BteNode(ColumnMeta column, PropertyMeta property) {
        this(column, property, null);
    }

    /**
     * 构造函数
     *
     * @param column 表字段
     */
    public BteNode(ColumnMeta column) {
        if (column.getJavaProperty().isPresent()) {
            this.column = column;
            this.property = column.getJavaProperty().get();
            this.compareRelation = NodeRelation.BTE;
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
    public BteNode(ColumnMeta column, PropertyMeta property, Function<T, Boolean> enableCheck) {
        this.column = column;
        this.property = property;
        this.compareRelation = NodeRelation.BTE;
        this.enableCheck = enableCheck;
    }

    /**
     * 构造函数
     *
     * @param column      表字段
     * @param enableCheck 判断该节点是否参与构建SQL的函数
     */
    public BteNode(ColumnMeta column, Function<T, Boolean> enableCheck) {
        if (column.getJavaProperty().isPresent()) {
            this.column = column;
            this.property = column.getJavaProperty().get();
            this.compareRelation = NodeRelation.BTE;
            this.enableCheck = enableCheck;
        } else {
            throw new RuntimeException("Has to specify a java property for column");
        }
    }
}
