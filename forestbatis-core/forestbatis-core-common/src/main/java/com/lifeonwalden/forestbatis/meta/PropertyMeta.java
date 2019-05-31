package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;

import java.util.Optional;

/**
 * Java类中属性的元信息
 */
public interface PropertyMeta extends SqlNode {
    /**
     * 类属性名
     *
     * @return
     */
    String getName();

    /**
     * 获得该属性对应的JdbcType
     *
     * @return
     */
    Optional<JdbcType> getJdbcType();

    /**
     * 赋予新的属性名构造一个新的Property
     *
     * @param name
     * @return
     */
    PropertyMeta overrideName(String name);
}
