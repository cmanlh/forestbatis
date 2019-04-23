package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.NodeRelation;

/**
 * 逻辑关系节点
 *
 * @param <T>
 */
public class RelationNode<T> {
    // 节点
    private T node;

    // 该节点与主节点的逻辑关系
    private NodeRelation nodeRelation;

    public RelationNode(T node, NodeRelation nodeRelation) {
        this.node = node;
        this.nodeRelation = nodeRelation;
    }

    public T getNode() {
        return node;
    }

    public RelationNode setNode(T node) {
        this.node = node;

        return this;
    }

    public NodeRelation getNodeRelation() {
        return nodeRelation;
    }

    public RelationNode setNodeRelation(NodeRelation nodeRelation) {
        this.nodeRelation = nodeRelation;

        return this;
    }
}
