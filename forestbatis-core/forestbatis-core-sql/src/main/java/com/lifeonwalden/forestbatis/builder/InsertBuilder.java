package com.lifeonwalden.forestbatis.builder;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.constant.SqlCommandType;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.TableNode;
import com.lifeonwalden.forestbatis.parsing.PropertyParser;

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
    private Config config;

    private volatile StatementInfo cachedStatement;

    public InsertBuilder(TableNode tableNode, Config config, List<ColumnMeta> toInsertColumnList) {
        this(tableNode, config, toInsertColumnList, true);
    }

    public InsertBuilder(TableNode tableNode, Config config, List<ColumnMeta> toInsertColumnList, boolean insertNull) {
        this.tableNode = tableNode;
        this.config = config;
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
        return new InsertBuilder<T>(this.tableNode, this.config, toInsertColumnList, this.insertNull);
    }

    /**
     * 覆盖插入列并构造一个新的SQL构建器
     *
     * @param toInsertColumnList
     * @param insertNull
     * @return
     */
    public InsertBuilder overrideInsertColumn(List<ColumnMeta> toInsertColumnList, boolean insertNull) {
        return new InsertBuilder<T>(this.tableNode, this.config, toInsertColumnList, insertNull);
    }

    /**
     * 覆盖插入列并构造一个新的SQL构建器
     *
     * @param insertNull
     * @return
     */
    public InsertBuilder overrideInsertColumn(boolean insertNull) {
        return new InsertBuilder<T>(this.tableNode, this.config, this.toInsertColumnList, insertNull);
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
        return new InsertBuilder<T>(this.tableNode, this.config, _toInsertColumnList, this.insertNull);
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
        return new InsertBuilder<T>(this.tableNode, this.config, _toInsertColumnList, insertNull);
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
        return new InsertBuilder<T>(this.tableNode, this.config, _toInsertColumnList, this.insertNull);
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
        return new InsertBuilder<T>(this.tableNode, this.config, _toInsertColumnList, insertNull);
    }

    @Override
    public StatementInfo build(T value) {
        if (this.isRuntimeChangeable() == false && this.cachedStatement != null) {
            return this.cachedStatement;
        }

        StringBuilder builder = new StringBuilder();

        SqlCommandType.INSERT.toSql(builder,config);
        builder.append(" ");
        this.tableNode.toSql(builder,config);
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
                    columnMeta.getJavaProperty().get().toSql(innerbuilder, this.config);

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
            columnMeta.getJavaProperty().get().toSql(innerbuilder, this.config);
            for (idx = 1; idx < this.toInsertColumnList.size(); idx++) {
                columnMeta = this.toInsertColumnList.get(idx);
                builder.append(", ").append(columnMeta.getLabel());

                innerbuilder.append(", ");
                columnMeta.getJavaProperty().get().toSql(innerbuilder, this.config);
            }
            builder.append(")");

            builder.append(" values(").append(innerbuilder.toString()).append(")");
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
