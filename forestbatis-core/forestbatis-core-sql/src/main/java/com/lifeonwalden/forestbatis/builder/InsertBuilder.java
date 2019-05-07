package com.lifeonwalden.forestbatis.builder;

import com.lifeonwalden.forestbatis.constant.SqlCommandType;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.TableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 插入语句构建器
 */
public class InsertBuilder<T> implements com.lifeonwalden.forestbatis.sql.InsertBuilder<T> {
    protected List<ColumnMeta> toInsertColumnList;
    private TableNode tableNode;
    // 是否将null字段插入数据库
    private boolean insertNull;

    private volatile String cachedStatement;

    public InsertBuilder(TableNode tableNode, List<ColumnMeta> toInsertColumnList) {
        this(tableNode, toInsertColumnList, true);
    }

    public InsertBuilder(TableNode tableNode, List<ColumnMeta> toInsertColumnList, boolean insertNull) {
        this.tableNode = tableNode;
        this.toInsertColumnList = toInsertColumnList;
        this.insertNull = insertNull;
    }

    /**
     * 覆盖插入列并构造一个新的SQL构建器
     *
     * @param toInsertColumnList
     * @return
     */
    public InsertBuilder overrideInsertColumn(List<ColumnMeta> toInsertColumnList) {
        return new InsertBuilder<T>(this.tableNode, toInsertColumnList, this.insertNull);
    }

    /**
     * 覆盖插入列并构造一个新的SQL构建器
     *
     * @param toInsertColumnList
     * @param insertNull
     * @return
     */
    public InsertBuilder overrideInsertColumn(List<ColumnMeta> toInsertColumnList, boolean insertNull) {
        return new InsertBuilder<T>(this.tableNode, toInsertColumnList, insertNull);
    }

    /**
     * 在当前插入列基础上剔除指定列并构造一个新的SQL构建器
     *
     * @param excludeInsertColumnList
     * @return
     */
    public InsertBuilder excludeInsertColumn(List<ColumnMeta> excludeInsertColumnList) {
        List<ColumnMeta> _toInsertColumnList = new ArrayList<>();
        this.toInsertColumnList.forEach(columnMeta -> {
            if (!excludeInsertColumnList.contains(columnMeta)) {
                _toInsertColumnList.add(columnMeta);
            }
        });
        return new InsertBuilder<T>(this.tableNode, _toInsertColumnList, this.insertNull);
    }

    /**
     * 在当前插入列基础上剔除指定列并构造一个新的SQL构建器
     *
     * @param excludeInsertColumnList
     * @param insertNull
     * @return
     */
    public InsertBuilder excludeInsertColumn(List<ColumnMeta> excludeInsertColumnList, boolean insertNull) {
        List<ColumnMeta> _toInsertColumnList = new ArrayList<>();
        this.toInsertColumnList.forEach(columnMeta -> {
            if (!excludeInsertColumnList.contains(columnMeta)) {
                _toInsertColumnList.add(columnMeta);
            }
        });
        return new InsertBuilder<T>(this.tableNode, _toInsertColumnList, insertNull);
    }

    /**
     * 在当前插入列基础上增加指定列并构造一个新的SQL构建器
     *
     * @param toAddInsertColumnList
     * @return
     */
    public InsertBuilder addInsertColumn(List<ColumnMeta> toAddInsertColumnList) {
        List<ColumnMeta> _toInsertColumnList = new ArrayList<>();
        _toInsertColumnList.addAll(this.toInsertColumnList);
        _toInsertColumnList.addAll(toAddInsertColumnList);
        return new InsertBuilder<T>(this.tableNode, _toInsertColumnList, this.insertNull);
    }

    /**
     * 在当前插入列基础上增加指定列并构造一个新的SQL构建器
     *
     * @param toAddInsertColumnList
     * @param insertNull
     * @return
     */
    public InsertBuilder addInsertColumn(List<ColumnMeta> toAddInsertColumnList, boolean insertNull) {
        List<ColumnMeta> _toInsertColumnList = new ArrayList<>();
        _toInsertColumnList.addAll(this.toInsertColumnList);
        _toInsertColumnList.addAll(toAddInsertColumnList);
        return new InsertBuilder<T>(this.tableNode, _toInsertColumnList, insertNull);
    }

    @Override
    public String build(T value) {
        if (this.isRuntimeChangeable() == false && this.cachedStatement != null) {
            return this.cachedStatement;
        }

        StringBuilder builder = new StringBuilder();

        SqlCommandType.INSERT.toSql(builder);
        builder.append(" ");
        this.tableNode.toSql(builder);
        builder.append(" ");

        if (this.isRuntimeChangeable()) {
            Map<String, Object> _value = toValue(value);

            StringBuilder innerbuilder = new StringBuilder();
            builder.append("(");
            ColumnMeta columnMeta;
            boolean notFirstOne = false;
            for (int idx = 0; idx < this.toInsertColumnList.size(); idx++) {
                columnMeta = this.toInsertColumnList.get(idx);

                if (null != _value.get(columnMeta.getJavaProperty().get().getName())) {
                    if (notFirstOne) {
                        builder.append(", ");
                        innerbuilder.append(", ");
                    }
                    builder.append(columnMeta.getLabel());
                    columnMeta.getJavaProperty().get().toSql(innerbuilder);

                    notFirstOne = true;
                }
            }
            builder.append(")");

            builder.append(" values(").append(innerbuilder.toString()).append(")");
        } else {
            StringBuilder innerbuilder = new StringBuilder();
            builder.append("(");
            int idx = 0;
            ColumnMeta columnMeta = this.toInsertColumnList.get(idx);
            builder.append(columnMeta.getLabel());
            columnMeta.getJavaProperty().get().toSql(innerbuilder);
            for (idx = 1; idx < this.toInsertColumnList.size(); idx++) {
                columnMeta = this.toInsertColumnList.get(idx);
                builder.append(", ").append(columnMeta.getLabel());

                innerbuilder.append(", ");
                columnMeta.getJavaProperty().get().toSql(innerbuilder);
            }
            builder.append(")");

            builder.append(" values(").append(innerbuilder.toString()).append(")");
        }
        if (false == this.isRuntimeChangeable()) {
            this.cachedStatement = builder.toString();
            return this.cachedStatement;
        } else {
            return builder.toString();
        }
    }

    @Override
    public String build() {
        return build(null);
    }

    @Override
    public boolean isRuntimeChangeable() {
        return false == this.insertNull;
    }

    private Map<String, Object> toValue(T value) {
        if (null != value && value instanceof Map) {
            return (Map<String, Object>) value;
        } else {
            throw new RuntimeException("Build dynamic insert statement should be an instance of Map");
        }
    }
}
