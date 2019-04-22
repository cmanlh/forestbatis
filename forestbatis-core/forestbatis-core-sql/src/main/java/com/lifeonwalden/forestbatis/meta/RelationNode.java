package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.LogicRelation;

/**
 * 逻辑关系节点
 *
 * @param <T>
 */
public class RelationNode<T> {
    // 节点
    private T node;

    // 该节点与主节点的逻辑关系
    private LogicRelation logicRelation;

    public RelationNode(T node, LogicRelation logicRelation) {
        this.node = node;
        this.logicRelation = logicRelation;
    }

    public T getNode() {
        return node;
    }

    public RelationNode setNode(T node) {
        this.node = node;

        return this;
    }

    public LogicRelation getLogicRelation() {
        return logicRelation;
    }

    public RelationNode setLogicRelation(LogicRelation logicRelation) {
        this.logicRelation = logicRelation;

        return this;
    }
}
