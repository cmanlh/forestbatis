package com.lifeonwalden.forestbatis.builder;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.constant.NodeRelation;
import com.lifeonwalden.forestbatis.constant.QueryNodeEnableType;
import com.lifeonwalden.forestbatis.constant.SqlCommandType;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.QueryNode;
import com.lifeonwalden.forestbatis.meta.TableNode;
import com.lifeonwalden.forestbatis.parsing.PropertyParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 更新语句构建器
 */
public class UpdateBuilder<T> implements UpdateSqlBuilder<T> {
    protected List<ColumnMeta> toUpdateColumnList;
    private TableNode tableNode;
    private Config config;
    private QueryNode queryNode;
    // 是否将null字段更新到数据库
    private boolean updateNull;
    private boolean runtimeChangeable;

    private volatile StatementInfo cachedStatement;

    public UpdateBuilder(TableNode tableNode, Config config, List<ColumnMeta> toUpdateColumnList) {
        this(tableNode, config, toUpdateColumnList, null, true);
    }

    public UpdateBuilder(TableNode tableNode, Config config, List<ColumnMeta> toUpdateColumnList, boolean updateNull) {
        this(tableNode, config, toUpdateColumnList, null, updateNull);
    }

    public UpdateBuilder(TableNode tableNode, Config config, List<ColumnMeta> toUpdateColumnList, QueryNode queryNode) {
        this(tableNode, config, toUpdateColumnList, queryNode, true);
    }

    public UpdateBuilder(TableNode tableNode, Config config, List<ColumnMeta> toUpdateColumnList, QueryNode queryNode, boolean updateNull) {
        this.toUpdateColumnList = toUpdateColumnList;
        this.tableNode = tableNode;
        this.config = config;
        this.queryNode = queryNode;
        this.updateNull = updateNull;

        if (this.updateNull) {
            if (null == this.queryNode) {
                this.runtimeChangeable = false;
            } else {
                this.runtimeChangeable = this.queryNode.isRuntimeChangeable();
            }
        } else {
            this.runtimeChangeable = true;
        }
    }

    /**
     * 覆盖更新列并构造一个新的SQL构建器
     *
     * @param toUpdateColumnList
     * @return
     */
    public UpdateBuilder overrideUpdateColumn(List<ColumnMeta> toUpdateColumnList) {
        return new UpdateBuilder<T>(this.tableNode, this.config, toUpdateColumnList, this.queryNode, this.updateNull);
    }

    /**
     * 覆盖插入列并构造一个新的SQL构建器
     *
     * @param updateNull
     * @return
     */
    public UpdateBuilder overrideUpdateColumn(boolean updateNull) {
        return new UpdateBuilder<T>(this.tableNode, this.config, this.toUpdateColumnList, this.queryNode, updateNull);
    }

    /**
     * 覆盖插入列并构造一个新的SQL构建器
     *
     * @param toUpdateColumnList
     * @param updateNull
     * @return
     */
    public UpdateBuilder overrideUpdateColumn(List<ColumnMeta> toUpdateColumnList, boolean updateNull) {
        return new UpdateBuilder<T>(this.tableNode, this.config, toUpdateColumnList, this.queryNode, updateNull);
    }

    /**
     * 在当前插入列基础上剔除指定列并构造一个新的SQL构建器
     *
     * @param excludeUpdateColumnList
     * @return
     */
    public UpdateBuilder excludeUpdateColumn(List<ColumnMeta> excludeUpdateColumnList) {
        List<ColumnMeta> _toUpdateColumnList = new ArrayList<>();
        this.toUpdateColumnList.forEach(columnMeta -> {
            if (!excludeUpdateColumnList.contains(columnMeta)) {
                _toUpdateColumnList.add(columnMeta);
            }
        });
        return new UpdateBuilder<T>(this.tableNode, this.config, _toUpdateColumnList, this.queryNode, this.updateNull);
    }

    /**
     * 在当前插入列基础上剔除指定列并构造一个新的SQL构建器
     *
     * @param excludeUpdateColumnList
     * @param updateNull
     * @return
     */
    public UpdateBuilder excludeUpdateColumn(List<ColumnMeta> excludeUpdateColumnList, boolean updateNull) {
        List<ColumnMeta> _toUpdateColumnList = new ArrayList<>();
        this.toUpdateColumnList.forEach(columnMeta -> {
            if (!excludeUpdateColumnList.contains(columnMeta)) {
                _toUpdateColumnList.add(columnMeta);
            }
        });
        return new UpdateBuilder<T>(this.tableNode, this.config, _toUpdateColumnList, this.queryNode, updateNull);
    }

    /**
     * 在当前插入列基础上增加指定列并构造一个新的SQL构建器
     *
     * @param toAddUpdateColumnList
     * @return
     */
    public UpdateBuilder addUpdateColumn(List<ColumnMeta> toAddUpdateColumnList) {
        List<ColumnMeta> _toUpdateColumnList = new ArrayList<>();
        _toUpdateColumnList.addAll(this.toUpdateColumnList);
        _toUpdateColumnList.addAll(toAddUpdateColumnList);
        return new UpdateBuilder<T>(this.tableNode, this.config, _toUpdateColumnList, this.queryNode, this.updateNull);
    }

    /**
     * 在当前插入列基础上增加指定列并构造一个新的SQL构建器
     *
     * @param toAddUpdateColumnList
     * @param updateNull
     * @return
     */
    public UpdateBuilder addUpdateColumn(List<ColumnMeta> toAddUpdateColumnList, boolean updateNull) {
        List<ColumnMeta> _toUpdateColumnList = new ArrayList<>();
        _toUpdateColumnList.addAll(this.toUpdateColumnList);
        _toUpdateColumnList.addAll(toAddUpdateColumnList);
        return new UpdateBuilder<T>(this.tableNode, this.config, _toUpdateColumnList, this.queryNode, updateNull);
    }

    /**
     * 覆盖查询条件并构造一个新的SQL构建器
     *
     * @param queryNode
     * @return
     */
    public UpdateBuilder overrideQuery(QueryNode queryNode) {
        return new UpdateBuilder<T>(this.tableNode, this.config, this.toUpdateColumnList, queryNode, this.updateNull);
    }

    /**
     * 覆盖查询条件并构造一个新的SQL构建器
     *
     * @param queryNode
     * @return
     */
    public UpdateBuilder overrideQuery(QueryNode queryNode, boolean updateNull) {
        return new UpdateBuilder<T>(this.tableNode, this.config, this.toUpdateColumnList, queryNode, updateNull);
    }

    @Override
    public StatementInfo build(T value) {
        if (this.isRuntimeChangeable() == false && this.cachedStatement != null) {
            return this.cachedStatement;
        }

        StringBuilder builder = new StringBuilder();
        boolean withAlias = this.tableNode.isJoined() || this.queryNode.isJoined();

        SqlCommandType.UPDATE.toSql(builder, this.config);
        builder.append(" ");
        this.tableNode.toSql(builder, this.config, withAlias);
        NodeRelation.SET.toSql(builder, this.config);

        if (this.isRuntimeChangeable()) {
            Map<String, Object> _value = toValue(value);

            ColumnMeta columnMeta;
            boolean notFirstOne = false;
            for (int idx = 0; idx < this.toUpdateColumnList.size(); idx++) {
                columnMeta = this.toUpdateColumnList.get(idx);

                if (null != _value.get(columnMeta.getJavaProperty().get().getName())) {
                    if (notFirstOne) {
                        builder.append(", ");
                    }
                    columnMeta = this.toUpdateColumnList.get(idx);
                    columnMeta.toSql(builder, this.config, withAlias);
                    NodeRelation.EQ.toSql(builder, this.config);
                    columnMeta.getJavaProperty().get().toSql(builder, this.config);

                    notFirstOne = true;
                }
            }
        } else {
            int idx = 0;
            ColumnMeta columnMeta = this.toUpdateColumnList.get(idx);
            columnMeta.toSql(builder, this.config, withAlias);
            NodeRelation.EQ.toSql(builder, this.config);
            columnMeta.getJavaProperty().get().toSql(builder, this.config);
            for (idx = 1; idx < this.toUpdateColumnList.size(); idx++) {
                builder.append(", ");

                columnMeta = this.toUpdateColumnList.get(idx);
                columnMeta.toSql(builder, this.config, withAlias);
                NodeRelation.EQ.toSql(builder, this.config);
                columnMeta.getJavaProperty().get().toSql(builder, this.config);
            }
        }

        if (null != this.queryNode && QueryNodeEnableType.DISABLED != this.queryNode.enabled(value)) {
            NodeRelation.WHERE.toSql(builder, this.config, withAlias);
            queryNode.toSql(builder, this.config, withAlias, value);
        }

        if (false == this.isRuntimeChangeable()) {
            this.cachedStatement = PropertyParser.parse(builder.toString());
            return this.cachedStatement;
        } else {
            return PropertyParser.parse(builder.toString());
        }
    }

    @Override
    public StatementInfo build() {
        return build(null);
    }

    @Override
    public boolean isRuntimeChangeable() {
        return this.runtimeChangeable;
    }

    private Map<String, Object> toValue(T value) {
        if (null != value && value instanceof Map) {
            return (Map<String, Object>) value;
        } else {
            throw new RuntimeException("Build dynamic insert statement should be an instance of Map");
        }
    }
}
