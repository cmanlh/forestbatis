package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

/**
 * exists
 *
 * @param <T>
 */
public class Exists<T> extends AbstractExistQueryNode<T> {

    /**
     * 构造函数
     *
     * @param tempTable 临时表
     */
    public Exists(TempTable tempTable) {
        this.tempTable = tempTable;
        this.compareRelation = NodeRelation.EXISTS;
    }
}
