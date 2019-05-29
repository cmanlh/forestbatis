package com.lifeonwalden.forestbatis.meta;

import com.lifeonwalden.forestbatis.bean.Config;

/**
 * 一个将要生成SQL片段的对象
 */
public interface ValueBindingSqlNode<T> {

    /**
     * 允许根据参数生成带或者不带别名的片段
     * 并且根据值可决定该节点是否参与构建SQL
     *
     * @param builder
     * @param config
     * @param withAlias
     */
    void toSql(StringBuilder builder, Config config, boolean withAlias, T value);

    /**
     * 生成默认片段
     * 并且根据值可决定该节点是否参与构建SQL
     *
     * @param builder
     * @param config
     * @return
     */
    void toSql(StringBuilder builder, Config config, T value);
}
