package com.lifeonwalden.forestbatis.meta;

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
     * 获得java数据类型
     *
     * @return
     */
    String getJavaType();
}
