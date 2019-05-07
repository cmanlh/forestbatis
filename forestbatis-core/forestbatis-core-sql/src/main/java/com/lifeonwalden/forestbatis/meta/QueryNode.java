package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;

/**
 * 查询条件节点
 *
 * @param <T> 值类型
 */
public interface QueryNode<T> extends ValueBindingSqlNode<T> {
    /**
     * 添加与本查询条件为“且”关系的查询节点
     *
     * @param queryNode
     * @return
     */
    QueryNode and(QueryNode queryNode);

    /**
     * 添加多个与本查询条件为“且”关系的查询节点
     *
     * @param queryNodes
     * @return
     */
    QueryNode and(QueryNode... queryNodes);

    /**
     * 添加与本查询条件为“或”关系的查询节点
     *
     * @param queryNode
     * @return
     */
    QueryNode or(QueryNode queryNode);

    /**
     * 添加多个与本查询条件为“或”关系的查询节点
     *
     * @param queryNodes
     * @return
     */
    QueryNode or(QueryNode... queryNodes);

    /**
     * 判断该条件是否参与构建查询语句
     * ｓ
     *
     * @param value
     * @return
     */
    QueryNodeEnableType enabled(T value);

    /**
     * 是否存在子查询
     *
     * @return
     */
    boolean hasSubQuery();

    /**
     * 是否构成多表组合
     */
    boolean isJoined();

    /**
     * 运行时构建的SQL语句是否可变
     *
     * @return
     */
    boolean isRuntimeChangeable();
}
