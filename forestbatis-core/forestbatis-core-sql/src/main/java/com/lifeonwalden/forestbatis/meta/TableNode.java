package com.lifeonwalden.forestbatis.meta;

/**
 * 数据库操作的目标（表或者表联合）
 */
public interface TableNode {

    /**
     * 内联结表
     *
     * @param joinNode
     * @return
     */
    TableNode innerJoin(JoinNode joinNode);

    /**
     * 左联结表
     *
     * @param joinNode
     * @return
     */
    TableNode leftJoin(JoinNode joinNode);

    /**
     * 右联结表
     *
     * @param joinNode
     * @return
     */
    TableNode rightJoin(JoinNode joinNode);
}