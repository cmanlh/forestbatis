package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作的目标（表或者表联合）
 */
public class TableNode implements SqlNode {
    // 主表
    private TableMeta tableMeta;

    // 联合表
    private List<RelationNode<JoinNode>> joinList;


    public TableNode(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    /**
     * 内联结表
     *
     * @param joinNode
     * @return
     */
    public TableNode innerJoin(JoinNode joinNode) {
        setupJoinList().add(new RelationNode<>(joinNode, NodeRelation.INNER_JOIN));

        return this;
    }

    /**
     * 内联结多表
     *
     * @param joinNodes
     * @return
     */
    public TableNode innerJoin(JoinNode... joinNodes) {
        List<RelationNode<JoinNode>> _joinList = setupJoinList();
        for (JoinNode joinNode : joinNodes) {
            _joinList.add(new RelationNode<>(joinNode, NodeRelation.INNER_JOIN));
        }

        return this;
    }

    /**
     * 左联结表
     *
     * @param joinNode
     * @return
     */
    public TableNode leftJoin(JoinNode joinNode) {
        setupJoinList().add(new RelationNode<>(joinNode, NodeRelation.LEFT_JOIN));

        return this;
    }

    /**
     * 左联结多表
     *
     * @param joinNodes
     * @return
     */
    public TableNode leftJoin(JoinNode... joinNodes) {
        List<RelationNode<JoinNode>> _joinList = setupJoinList();
        for (JoinNode joinNode : joinNodes) {
            _joinList.add(new RelationNode<>(joinNode, NodeRelation.LEFT_JOIN));
        }

        return this;
    }

    /**
     * 右联结表
     *
     * @param joinNode
     * @return
     */
    public TableNode rightJoin(JoinNode joinNode) {
        setupJoinList().add(new RelationNode<>(joinNode, NodeRelation.RIGHT_JOIN));

        return this;
    }

    /**
     * 右联结多表
     *
     * @param joinNodes
     * @return
     */
    public TableNode rightJoin(JoinNode... joinNodes) {
        List<RelationNode<JoinNode>> _joinList = setupJoinList();
        for (JoinNode joinNode : joinNodes) {
            _joinList.add(new RelationNode<>(joinNode, NodeRelation.RIGHT_JOIN));
        }

        return this;
    }

    /**
     * 是否为表联合查询
     *
     * @return
     */
    public boolean isJoined() {
        return null != this.joinList && !this.joinList.isEmpty();
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        if (null == this.joinList || this.joinList.isEmpty()) {
            this.tableMeta.toSql(builder, withAlias);
        } else {
            this.tableMeta.toSql(builder, true);
            this.joinList.forEach(join -> {
                join.getNodeRelation().toSql(builder, false);
                join.getNode().toSql(builder, true);
            });
        }
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder, true);
    }

    private List<RelationNode<JoinNode>> setupJoinList() {
        if (null == this.joinList) {
            this.joinList = new ArrayList<>();
        }

        return this.joinList;
    }
}