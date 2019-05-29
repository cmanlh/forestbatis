package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractTableMeta implements TableMeta {
    protected String name;
    protected String alias;
    protected List<ColumnMeta> columnList;

    public AbstractTableMeta(String name, String alias) {
        this(name, alias, null);
    }

    public AbstractTableMeta(String name, String alias, List<ColumnMeta> columnList) {
        this.name = name;
        this.alias = alias;
        this.columnList = Collections.unmodifiableList(columnList);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public Optional<List<ColumnMeta>> getColumn() {
        if (null == this.columnList || this.columnList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.columnList);
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config, boolean withAlias) {
        String caseSensitiveSign = config.isCaseSensitive() ? config.getSensitiveSign() : "";

        if (config.isWithSchema()) {
            builder.append(config.getSchema()).append(".").append(caseSensitiveSign).append(this.name).append(caseSensitiveSign);
        } else {
            builder.append(caseSensitiveSign).append(this.name).append(caseSensitiveSign);
        }
        if (withAlias) {
            builder.append(" ").append(this.alias);
        }
    }

    @Override
    public void toSql(StringBuilder builder, Config config) {
        toSql(builder, config, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj instanceof AbstractTableMeta) {
                AbstractTableMeta _obj = (AbstractTableMeta) obj;
                return this.name.equals(_obj.name);
            }

            return false;
        }
    }
}
