package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.result.ResultHandler;
import com.lifeonwalden.forestbatis.sql.SQLBuilder;

import java.util.List;

public interface CommonMapper<P> {

    /**
     * 基于条件查询
     *
     * @param param 查询条件
     * @return
     */
    List<P> select(P param);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据生成参数动态生成SQL语句
     *
     * @param param 查询条件
     * @param sqlBuilder SQL生成参数
     * @return
     */
    List<P> select(P param, SQLBuilder sqlBuilder);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据生成参数动态生成SQL语句
     *
     * @param param
     * @param sqlBuilder
     * @param resultHandler
     * @return
     */
    List<P> select(P param, SQLBuilder sqlBuilder, ResultHandler resultHandler);
}
