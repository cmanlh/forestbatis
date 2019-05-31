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
     * 基于主键查询，返回excludeReturnColumnList之外的字段
     *
     * @param param                   查询条件
     * @param excludeReturnColumnList SQL生成
     * @return
     */
    Optional<P> get(P param, List<ColumnMeta> excludeReturnColumnList);

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
    Integer update(P value, List<ColumnMeta> toUpdateColumnList);

    /**
     * 根据主键更新记录，且仅更新toUpdateColumnList指定的列
     * 当value中某属性为null时，对应记录字段将不进行更新
     *
     * @param value 指定对象
     * @return
     */
    Integer updateWithoutNull(P value, List<ColumnMeta> toUpdateColumnList);

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
    int[] update(List<P> valueList, List<ColumnMeta> toUpdateColumnList);

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