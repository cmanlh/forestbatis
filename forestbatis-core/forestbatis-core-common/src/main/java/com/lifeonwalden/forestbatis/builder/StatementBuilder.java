package com.lifeonwalden.forestbatis.builder;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.bean.StatementInfo;

public interface StatementBuilder<T> {

    /**
     * 构建sql
     *
     * @param value 值对象
     * @return
     */
    StatementInfo build(T value, Config config);

    /**
     * 构建sql
     *
     * @return
     */
    StatementInfo build(Config config);

    /**
     * 运行时构建的SQL语句是否可变
     *
     * @return
     */
    boolean isRuntimeChangeable();
}
