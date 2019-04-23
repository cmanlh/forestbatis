package com.lifeonwalden.forestbatis.meta;

/**
 * 查询条件节点
 *
 * @param <T> 值类型
 */
public interface QueryNode<T> {
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
    boolean enabled(T value);

    /**
     * 允许根据参数生成带或者不带别名的片段
     * 并且根据值可决定该节点是否参与构建SQL
     *
     * @param builder
     * @param withAlias
     */
    void toSql(StringBuilder builder, boolean withAlias, T value);

    /**
     * 生成默认片段
     * 并且根据值可决定该节点是否参与构建SQL
     *
     * @param builder
     * @return
     */
    void toSql(StringBuilder builder, T value);
}
