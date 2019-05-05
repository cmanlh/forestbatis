package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

/**
 * not exists
 *
 * @param <T>
 */
public class NotExists<T> extends AbstractExistQueryNode<T> {

    /**
     * 构造函数
     *
     * @param tempTable 临时表
     */
    public NotExists(TempTable tempTable) {
        this.tempTable = tempTable;
        this.compareRelation = NodeRelation.NOT_EXISTS;
    }
}
