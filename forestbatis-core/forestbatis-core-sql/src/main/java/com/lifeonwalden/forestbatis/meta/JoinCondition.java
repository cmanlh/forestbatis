package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.LogicRelation;
import com.lifeonwalden.forestbatis.constant.SqlOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * 表联合条件
 */
public class JoinCondition implements SqlNode {
    // 表字段
    private ColumnMeta column;

    // 另一表的字段
    private ColumnMeta anotherTableColumn;

    // 值
    private PropertyMeta property;

    // 与兄弟条件的逻辑关系
    private LogicRelation logicRelation;

    // 兄弟条件
    private List<RelationNode<JoinCondition>> siblingList;

    /**
     * 基于两表字段构建表联合条件
     *
     * @param column
     * @param anthoerTableColumn
     */
    private JoinCondition(ColumnMeta column, ColumnMeta anthoerTableColumn) {
        this.column = column;
        this.anotherTableColumn = anthoerTableColumn;
    }

    /**
     * 基于表字段与值构建表联合条件
     *
     * @param column
     * @param property
     */
    private JoinCondition(ColumnMeta column, PropertyMeta property) {
        this.column = column;
        this.property = property;
    }

    /**
     * 表联合，添加“且”关系条件
     *
     * @param joinCondition 表联合条件
     * @return
     */
    JoinCondition and(JoinCondition joinCondition) {
        setupSiblingList().add(new RelationNode<>(joinCondition, LogicRelation.AND));

        return this;
    }

    /**
     * 表联合，添加多个“且”关系条件
     *
     * @param joinConditions 表联合条件
     * @return
     */
    JoinCondition and(JoinCondition... joinConditions) {
        for (JoinCondition joinCondition : joinConditions) {
            setupSiblingList().add(new RelationNode<>(joinCondition, LogicRelation.AND));
        }

        return this;
    }

    /**
     * 表联合，添加“或”关系条件
     *
     * @param joinCondition 表联合条件
     * @return
     */
    JoinCondition or(JoinCondition joinCondition) {
        setupSiblingList().add(new RelationNode<>(joinCondition, LogicRelation.OR));

        return this;
    }

    /**
     * 表联合，添加多个“或”关系条件
     *
     * @param joinConditions 表联合条件
     * @return
     */
    JoinCondition or(JoinCondition... joinConditions) {
        for (JoinCondition joinCondition : joinConditions) {
            setupSiblingList().add(new RelationNode<>(joinCondition, LogicRelation.OR));
        }

        return this;
    }

    private List<RelationNode<JoinCondition>> setupSiblingList() {
        if (null == this.siblingList) {
            this.siblingList = new ArrayList<>();
        }

        return this.siblingList;
    }

    @Override
    public void toSql(StringBuilder builder, boolean withAlias) {
        if (null == anotherTableColumn && null == property) {
            throw new RuntimeException("Join condition has to have two side object.");
        }

        boolean complex = (null != this.siblingList && this.siblingList.size() > 0);
        if (complex) {
            builder.append("(");
        }

        this.column.toSql(builder, true);
        SqlOperator.EQ.toSql(builder, false);
        if (null != this.anotherTableColumn) {
            this.anotherTableColumn.toSql(builder, true);
        } else {
            this.property.toSql(builder, true);
        }

        if (complex) {
            siblingList.forEach(sibling -> {
                sibling.getLogicRelation().toSql(builder, false);
                sibling.getNode().toSql(builder, true);
            });

            builder.append(")");
        }
    }

    @Override
    public void toSql(StringBuilder builder) {
        toSql(builder, true);
    }
}
