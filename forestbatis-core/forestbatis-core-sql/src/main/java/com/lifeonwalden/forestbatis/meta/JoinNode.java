package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

/**
 * 构建表联合查询
 */
public class JoinNode implements SqlNode {
    // 目标联合表的信息
    private TableMeta tableMeta;

    // 联合的条件
    private JoinCondition joinCondition;

    public JoinNode(TableMeta tableMeta, JoinCondition joinCondition) {
        this.tableMeta = tableMeta;
        this.joinCondition = joinCondition;
    }

    /**
     * 获得要联合的表的信息
     *
     * @return
     */
    TableMeta getTable() {
        return this.tableMeta;
    }

    /**
     * 获得表联合的条件
     *
     * @return
     */
    JoinCondition getJoinCondition() {
        return this.joinCondition;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        this.tableMeta.toSql(builder, true);
        NodeRelation.ON.toSql(builder, false);
        this.joinCondition.toSql(builder, true);
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder, true);
    }
}
