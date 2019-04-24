package com.lifeonwalden.forestbatis.sql;

/**
 * sql语句构建类
 *
 * @param <T> 值对象类型
 */
public interface SQLBuilder<T> {
    /**
     * 构建sql
     *
     * @return
     */
    String build(T value);

    /**
     * 构建sql
     *
     * @return
     */
    String build();
}
