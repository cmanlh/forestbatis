package com.lifeonwalden.forestbatis.mapper;


import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.OrderBy;
import com.lifeonwalden.forestbatis.result.RecordHandler;
import com.lifeonwalden.forestbatis.result.StreamResultSetCallback;
import com.lifeonwalden.forestbatis.builder.DeleteSqlBuilder;
import com.lifeonwalden.forestbatis.builder.InsertSqlBuilder;
import com.lifeonwalden.forestbatis.builder.SelectSqlBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateSqlBuilder;

import java.util.List;

/**
 * 通用jdbc数据处理mapper
 *
 * @param <P>
 */
public interface CommonMapper<P> {

    /**
     * 返回全表
     *
     * @return
     */
    List<P> selectAll();

    /**
     * 返回全表
     *
     * @param excludeReturnColumnList 要排除的字段
     * @return
     */
    List<P> selectAll(ColumnMeta... excludeReturnColumnList);

    /**
     * 返回全表
     *
     * @param orderByList 排序信息
     * @return
     */
    List<P> selectAll(OrderBy... orderByList);

    /**
     * 返回全表
     *
     * @param streamResultSetCallback 流式结果处理callback
     */
    void selectAll(StreamResultSetCallback<P> streamResultSetCallback);

    /**
     * 返回全表
     *
     * @param streamResultSetCallback 流式结果处理callback
     * @param fetchSize               查询缓存区大小
     */
    void selectAll(StreamResultSetCallback<P> streamResultSetCallback, int fetchSize);

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
     */
    void select(P param, StreamResultSetCallback<P> streamResultSetCallback);

    /**
     * 基于条件查询
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param streamResultSetCallback 流式结果处理callback
     * @param fetchSize               查询缓存区大小
     */
    void select(P param, StreamResultSetCallback<P> streamResultSetCallback, int fetchSize);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句
     *
     * @param param         查询条件
     * @param selectSqlBuilder SQL生成
     * @return
     */
    List<P> select(P param, SelectSqlBuilder<P> selectSqlBuilder);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param selectSqlBuilder           SQL生成
     * @param streamResultSetCallback 流式结果处理callback
     */
    void select(P param, SelectSqlBuilder<P> selectSqlBuilder, StreamResultSetCallback<P> streamResultSetCallback);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param selectSqlBuilder           SQL生成
     * @param streamResultSetCallback 流式结果处理callback
     * @param fetchSize               查询缓存区大小
     */
    void select(P param, SelectSqlBuilder<P> selectSqlBuilder, StreamResultSetCallback<P> streamResultSetCallback, int fetchSize);

    /**
     * 基于条件查询，自定义结果解析
     *
     * @param param         查询条件
     * @param recordHandler 结果解析
     * @return
     */
    List<P> select(P param, RecordHandler<P> recordHandler);

    /**
     * 基于条件查询，自定义结果解析
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param recordHandler           结果解析
     * @param streamResultSetCallback 流式结果处理callback
     */
    void select(P param, RecordHandler<P> recordHandler, StreamResultSetCallback<P> streamResultSetCallback);

    /**
     * 基于条件查询，自定义结果解析
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param recordHandler           结果解析
     * @param streamResultSetCallback 流式结果处理callback
     * @param fetchSize               查询缓存区大小
     */
    void select(P param, RecordHandler<P> recordHandler, StreamResultSetCallback<P> streamResultSetCallback, int fetchSize);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句和自定义结果解析
     *
     * @param param         查询条件
     * @param selectSqlBuilder SQL生成
     * @param recordHandler 结果解析
     * @return
     */
    List<P> select(P param, SelectSqlBuilder<P> selectSqlBuilder, RecordHandler<P> recordHandler);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句和自定义结果解析
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param selectSqlBuilder           SQL生成
     * @param recordHandler           结果解析
     * @param streamResultSetCallback 流式结果处理callback
     */
    void select(P param, SelectSqlBuilder<P> selectSqlBuilder, RecordHandler<P> recordHandler, StreamResultSetCallback<P> streamResultSetCallback);

    /**
     * 基于条件查询，并可在默认SQL生成的基础上根据定制参数动态生成SQL语句和自定义结果解析
     * 且采用回调的方式来应对大结果集的场景
     *
     * @param param                   查询条件
     * @param selectSqlBuilder           SQL生成
     * @param recordHandler           结果解析
     * @param streamResultSetCallback 流式结果处理callback
     * @param fetchSize               查询缓存区大小
     */
    void select(P param, SelectSqlBuilder<P> selectSqlBuilder, RecordHandler<P> recordHandler, StreamResultSetCallback<P> streamResultSetCallback, int fetchSize);

    /**
     * 根据查询参数将结果数据集严格更新为指定对象对应的值，其中主键不在更新范围内
     * 如指定对象对应值为null，则表字段也将更新为null
     *
     * @param value 指定对象
     * @return
     */
    Integer updateWithQuery(P value);

    /**
     * 根据查询参数与SQL生成参数共同决定如何更新查询结果数据集，其中主键不在更新范围内
     *
     * @param value         指定对象
     * @param updateSqlBuilder SQL生成
     * @return
     */
    Integer updateWithQuery(P value, UpdateSqlBuilder<P> updateSqlBuilder);

    /**
     * 批量根据查询参数与SQL生成参数共同决定如何更新查询结果数据集，其中主键不在更新范围内
     * 指定对象、参数与返回结果按照下标一一对应
     *
     * @param valueList     指定对象
     * @param updateSqlBuilder SQL生成
     * @return
     */
    int[] updateWithQuery(List<P> valueList, UpdateSqlBuilder<P> updateSqlBuilder);

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
     * @param deleteSqlBuilder SQL生成
     * @return
     */
    Integer deleteWithQuery(P param, DeleteSqlBuilder<P> deleteSqlBuilder);

    /**
     * 批量根据查询参数与SQL生成参数共同决定删除结果集
     * 指定对象与返回结果按照下标一一对应
     *
     * @param paramList     查询参数
     * @param deleteSqlBuilder SQL生成
     * @return
     */
    int[] deleteWithQuery(List<P> paramList, DeleteSqlBuilder<P> deleteSqlBuilder);

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
     * @param insertSqlBuilder
     * @return
     */
    Integer insert(P value, InsertSqlBuilder<P> insertSqlBuilder);

    /**
     * 批量插入，如指定对象字段为null，则数据库相应字段也更新为null
     *
     * @param valueList
     * @return
     */
    int[] insert(List<P> valueList);

    /**
     * 批量插入，并可定义插入时字段赋值的方式
     *
     * @param valueList
     * @param insertSqlBuilder
     * @return
     */
    int[] insert(List<P> valueList, InsertSqlBuilder<P> insertSqlBuilder);

    /**
     * truncate表格，慎用
     *
     * @return
     */
    Integer truncate();

}
