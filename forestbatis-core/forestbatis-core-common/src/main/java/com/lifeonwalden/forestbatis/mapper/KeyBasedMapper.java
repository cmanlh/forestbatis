package com.lifeonwalden.forestbatis.mapper;


import com.lifeonwalden.forestbatis.meta.ColumnMeta;

import java.util.List;
import java.util.Optional;

public interface KeyBasedMapper<P> extends CommonMapper<P> {

    /**
     * 基于主键查询，全字段返回
     *
     * @param param 查询条件
     * @return
     */
    Optional<P> get(P param);

    /**
     * 基于主键查询，仅返回returnColumnList包含的字段
     *
     * @param param            查询条件
     * @param returnColumnList 要排除的字段
     * @return
     */
    Optional<P> getColumns(P param, ColumnMeta... returnColumnList);

    /**
     * 基于主键查询，返回excludeReturnColumnList之外的字段
     *
     * @param param                   查询条件
     * @param excludeReturnColumnList 要排除的字段
     * @return
     */
    Optional<P> getWithoutColumns(P param, ColumnMeta... excludeReturnColumnList);

    /**
     * 根据主键更新记录，当value中某属性为null时，对应记录字段也将被更新为null
     *
     * @param value 指定对象
     * @return
     */
    Integer update(P value);

    /**
     * 根据主键更新记录，当value中某属性为null时，对应记录字段将不进行更新
     *
     * @param value 指定对象
     * @return
     */
    Integer updateWithoutNull(P value);

    /**
     * 根据主键更新记录，且仅更新toUpdateColumnList指定的列
     * 当value中某属性为null时，对应记录字段也将被更新为null
     *
     * @param value              指定对象
     * @param toUpdateColumnList 指定要更新的列
     * @return
     */
    Integer updateColumns(P value, ColumnMeta... toUpdateColumnList);

    /**
     * 根据主键更新记录，且不更新notUpdateColumnList指定的列
     * 当value中某属性为null时，对应记录字段也将被更新为null
     *
     * @param value               指定对象
     * @param notUpdateColumnList 指定要更新的列
     * @return
     */
    Integer updateWithoutColumns(P value, ColumnMeta... notUpdateColumnList);

    /**
     * 根据主键更新记录，且仅更新toUpdateColumnList指定的列
     * 当value中某属性为null时，对应记录字段将不进行更新
     *
     * @param value              指定对象
     * @param toUpdateColumnList 指定要更新的列
     * @return
     */
    Integer updateColumnsWithoutNull(P value, ColumnMeta... toUpdateColumnList);

    /**
     * 根据主键更新记录，且不更新notUpdateColumnList指定的列
     * 当value中某属性为null时，对应记录字段将不进行更新
     *
     * @param value               指定对象
     * @param notUpdateColumnList 指定要更新的列
     * @return
     */
    Integer updateWithoutColumnsNull(P value, ColumnMeta... notUpdateColumnList);

    /**
     * 根据主键更新记录，当value中某属性为null时，对应记录字段也将被更新为null
     *
     * @param valueList 指定对象
     * @return
     */
    int[] update(List<P> valueList);

    /**
     * 根据主键更新记录，且仅更新toUpdateColumnList指定的列
     * 当value中某属性为null时，对应记录字段也将被更新为null
     *
     * @param valueList          指定对象
     * @param toUpdateColumnList 指定要更新的列
     * @return
     */
    int[] updateColumns(List<P> valueList, ColumnMeta... toUpdateColumnList);

    /**
     * 根据主键更新记录，且不更新notUpdateColumnList指定的列
     * 当value中某属性为null时，对应记录字段也将被更新为null
     *
     * @param valueList           指定对象
     * @param notUpdateColumnList 指定要更新的列
     * @return
     */
    int[] updateWithoutColumns(List<P> valueList, ColumnMeta... notUpdateColumnList);

    /**
     * 根据主键删除记录
     *
     * @param param 查询参数
     * @return
     */
    Integer delete(P param);

    /**
     * 批量根据主键删除记录
     * 指定对象与返回结果按照下标一一对应
     *
     * @param paramList 查询参数
     * @return
     */
    int[] delete(List<P> paramList);
}