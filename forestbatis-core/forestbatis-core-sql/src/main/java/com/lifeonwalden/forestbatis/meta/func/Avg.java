package com.lifeonwalden.forestbatis.meta.func;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.PropertyMeta;

public class Avg extends Func {
    public Avg(ColumnMeta colum) {
        this.column = colum;
    }

    public Avg(ColumnMeta colum, PropertyMeta javaProperty) {
        this.column = colum;
        this.javaProperty = javaProperty;
    }

    @Override
    protected void selfBuild(StringBuilder builder, Config config, boolean withAlias) {
        builder.append("avg");
    }
}
