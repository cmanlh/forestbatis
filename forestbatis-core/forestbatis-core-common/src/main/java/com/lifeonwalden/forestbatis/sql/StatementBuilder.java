package com.lifeonwalden.forestbatis.sql;

public interface StatementBuilder<T> {

    /**
     * 构建sql
     *
     * @param value 值对象
     * @return
     */
    String build(T value);

    /**
     * 构建sql
     *
     * @return
     */
    String build();

    /**
     * 运行时构建的SQL语句是否可变
     *
     * @return
     */
    boolean isRuntimeChangeable();
}
