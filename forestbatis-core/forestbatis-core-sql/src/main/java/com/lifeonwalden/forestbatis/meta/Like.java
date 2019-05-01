package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

import java.util.Optional;
import java.util.function.Function;

/**
 * like
 *
 * @param <T>
 */
public class Like<T> extends AbstractQueryNode<T> {
    /**
     * 构造函数
     *
     * @param column   表字段
     * @param property 值属性
     */
    public Like(ColumnMeta column, PropertyMeta property) {
        this(column, property, null);
    }

    /**
     * 构造函数
     *
     * @param column 表字段
     */
    public Like(ColumnMeta column) {
        if (column.getJavaProperty().isPresent()) {
            this.column = column;
            this.property = column.getJavaProperty().get();
            this.compareRelation = NodeRelation.LIKE;
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
    public Like(ColumnMeta column, PropertyMeta property, Function<Optional<T>, Boolean> enableCheck) {
        this.column = column;
        this.property = property;
        this.compareRelation = NodeRelation.LIKE;
        this.enableCheck = enableCheck;
    }

    /**
     * 构造函数
     *
     * @param column      表字段
     * @param enableCheck 判断该节点是否参与构建SQL的函数
     */
    public Like(ColumnMeta column, Function<Optional<T>, Boolean> enableCheck) {
        if (column.getJavaProperty().isPresent()) {
            this.column = column;
            this.property = column.getJavaProperty().get();
            this.compareRelation = NodeRelation.LIKE;
            this.enableCheck = enableCheck;
        } else {
            throw new RuntimeException("Has to specify a java property for column");
        }
    }
}
