package com.lifeonwalden.forestbatis.mapper;


import com.lifeonwalden.forestbatis.result.RecordHandler;
import com.lifeonwalden.forestbatis.result.StreamResultSetCallback;
import com.lifeonwalden.forestbatis.sql.DeleteBuilder;
import com.lifeonwalden.forestbatis.sql.InsertBuilder;
import com.lifeonwalden.forestbatis.sql.SelectBuilder;
import com.lifeonwalden.forestbatis.sql.UpdateBuilder;

import java.util.List;

/**
 * 通用jdbc数据处理mapper
 *
 * @param <P>
 */
public interface CommonMapper<P> {

    /**
     * 基于条件查询
     *
     * @param param 查询条件
     * @return
     */
    List<P> select(P param);

    /**
     * 基于条件查询
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param streamResultSetCallback 流式结果处理callback
     * @return
     */
    List<P> select(P param, StreamResultSetCallback streamResultSetCallback);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句
     *
     * @param param         查询条件
     * @param selectBuilder SQL生成
     * @return
     */
    List<P> select(P param, SelectBuilder selectBuilder);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param selectBuilder           SQL生成
     * @param streamResultSetCallback 流式结果处理callback
     * @return
     */
    List<P> select(P param, SelectBuilder selectBuilder, StreamResultSetCallback streamResultSetCallback);

    /**
     * 基于条件查询，自定义结果解析
     *
     * @param param         查询条件
     * @param resultHandler 结果解析
     * @return
     */
    List<P> select(P param, RecordHandler resultHandler);

    /**
     * 基于条件查询，自定义结果解析
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param resultHandler           结果解析
     * @param streamResultSetCallback 流式结果处理callback
     * @return
     */
    List<P> select(P param, RecordHandler resultHandler, StreamResultSetCallback streamResultSetCallback);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句和自定义结果解析
     *
     * @param param         查询条件
     * @param selectBuilder SQL生成
     * @param resultHandler 结果解析
     * @return
     */
    List<P> select(P param, SelectBuilder selectBuilder, RecordHandler resultHandler);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句和自定义结果解析
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param selectBuilder           SQL生成
     * @param resultHandler           结果解析
     * @param streamResultSetCallback 流式结果处理callback
     * @return
     */
    void select(P param, SelectBuilder selectBuilder, RecordHandler resultHandler, StreamResultSetCallback streamResultSetCallback);

    /**
     * 根据查询参数将结果数据集严格更新为指定对象对应的值，其中主键不在更新范围内
     * 如指定对象对应值为null，则表字段也将更新为null
     *
     * @param value 指定对象
     * @param param 查询参数
     * @return
     */
    Integer updateWithQuery(P value, P param);

    /**
     * 根据查询参数与SQL生成参数共同决定如何更新查询结果数据集，其中主键不在更新范围内
     *
     * @param value         指定对象
     * @param param         查询参数
     * @param updateBuilder SQL生成
     * @return
     */
    Integer updateWithQuery(P value, P param, UpdateBuilder updateBuilder);

    /**
     * 批量根据查询参数将结果数据集严格更新为指定对象对应的值，其中主键不在更新范围内
     * 如指定对象对应值为null，则表字段也将更新为null
     * 指定对象、参数与返回结果按照下标一一对应
     *
     * @param valueList 指定对象
     * @param paramList 查询参数
     * @return
     */
    List<Integer> updateWithQuery(List<P> valueList, List<P> paramList);

    /**
     * 批量根据查询参数与SQL生成参数共同决定如何更新查询结果数据集，其中主键不在更新范围内
     * 指定对象、参数与返回结果按照下标一一对应
     *
     * @param valueList     指定对象
     * @param paramList     查询参数
     * @param updateBuilder SQL生成
     * @return
     */
    List<Integer> updateWithQuery(List<P> valueList, List<P> paramList, UpdateBuilder updateBuilder);

    /**
     * 根据查询参数删除结果集
     *
     * @param param 查询参数
     * @return
     */
    Integer deleteWithQuery(P param);

    /**
     * 根据查询参数与SQL生成参数共同决定删除结果集
     *
     * @param param
     * @param deleteBuilder SQL生成
     * @return
     */
    Integer deleteWithQuery(P param, DeleteBuilder deleteBuilder);

    /**
     * 批量根据查询参数删除结果集
     * 指定对象与返回结果按照下标一一对应
     *
     * @param paramList 查询参数
     * @return
     */
    List<Integer> deleteWithQuery(List<P> paramList);

    /**
     * 批量根据查询参数与SQL生成参数共同决定删除结果集
     * 指定对象与返回结果按照下标一一对应
     *
     * @param paramList     查询参数
     * @param deleteBuilder SQL生成
     * @return
     */
    List<Integer> deleteWithQuery(List<P> paramList, DeleteBuilder deleteBuilder);

    /**
     * 插入，如指定对象字段为null，则数据库相应字段也更新为null
     *
     * @param value
     * @return
     */
    Integer insert(P value);

    /**
     * 插入，并可定义插入时字段赋值的方式
     *
     * @param value
     * @param insertBuilder
     * @return
     */
    Integer insert(P value, InsertBuilder insertBuilder);

    /**
     * 批量插入，如指定对象字段为null，则数据库相应字段也更新为null
     *
     * @param valueList
     * @return
     */
    List<Integer> insert(List<P> valueList);

    /**
     * 批量插入，并可定义插入时字段赋值的方式
     *
     * @param valueList
     * @param insertBuilder
     * @return
     */
    List<Integer> insert(List<P> valueList, InsertBuilder insertBuilder);

    /**
     * truncate表格，慎用
     *
     * @return
     */
    Integer truncate();

}