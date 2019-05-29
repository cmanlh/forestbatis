package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.constant.NodeRelation;

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

    // 兄弟条件
    private List<RelationNode<JoinCondition>> siblingList;

    /**
     * 基于两表字段构建表联合条件
     *
     * @param column             表字段
     * @param anthoerTableColumn 另一表的字段
     */
    public JoinCondition(ColumnMeta column, ColumnMeta anthoerTableColumn) {
        this.column = column;
        this.anotherTableColumn = anthoerTableColumn;
    }

    /**
     * 基于表字段与值构建表联合条件
     *
     * @param column   表字段
     * @param property 值属性
     */
    public JoinCondition(ColumnMeta column, PropertyMeta property) {
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
        setupSiblingList().add(new RelationNode<>(joinCondition, NodeRelation.AND));

        return this;
    }

    /**
     * 表联合，添加多个“且”关系条件
     *
     * @param joinConditions 表联合条件
     * @return
     */
    JoinCondition and(JoinCondition... joinConditions) {
        List<RelationNode<JoinCondition>> _siblingList = setupSiblingList();
        for (JoinCondition joinCondition : joinConditions) {
            _siblingList.add(new RelationNode<>(joinCondition, NodeRelation.AND));
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
        setupSiblingList().add(new RelationNode<>(joinCondition, NodeRelation.OR));

        return this;
    }

    /**
     * 表联合，添加多个“或”关系条件
     *
     * @param joinConditions 表联合条件
     * @return
     */
    JoinCondition or(JoinCondition... joinConditions) {
        List<RelationNode<JoinCondition>> _siblingList = setupSiblingList();
        for (JoinCondition joinCondition : joinConditions) {
            _siblingList.add(new RelationNode<>(joinCondition, NodeRelation.OR));
        }

        return this;
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        if (null == anotherTableColumn && null == property) {
            throw new RuntimeException("Join condition has to have two side object.");
        }

        boolean complex = (null != this.siblingList && this.siblingList.size() > 0);
        if (complex) {
            builder.append("(");
        }

        this.column.toSql(builder, config, true);
        NodeRelation.EQ.toSql(builder, config, false);
        if (null != this.anotherTableColumn) {
            this.anotherTableColumn.toSql(builder, config, true);
        } else {
            this.property.toSql(builder, config, true);
        }

        if (complex) {
            siblingList.forEach(sibling -> {
                sibling.getNodeRelation().toSql(builder, config, false);
                sibling.getNode().toSql(builder, config, true);
            });

            builder.append(")");
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        toSql(builder, config, true);
    }

    private List<RelationNode<JoinCondition>> setupSiblingList() {
        if (null == this.siblingList) {
            this.siblingList = new ArrayList<>();
        }

        return this.siblingList;
    }
}
