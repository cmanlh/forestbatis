package com.lifeonwalden.forestbatis.mapper;


import com.lifeonwalden.forestbatis.sql.SelectBuilder;
import com.lifeonwalden.forestbatis.sql.UpdateBuilder;

import java.util.List;

public interface KeyBasedMapper<P> extends CommonMapper<P> {
    /**
     * 根据主键严格更新为指定对象对应的值
     * 如指定对象对应值为null，则表字段也将更新为null
     *
     * @param value 指定对象
     * @return
     */
    Integer update(P value);

    /**
     * 根据主键与SQL生成参数共同决定如何更新对应记录
     *
     * @param value         指定对象
     * @param selectBuilder SQL生成
     * @return
     */
    Integer update(P value, SelectBuilder selectBuilder);

    /**
     * 批量根据主键严格更新为指定对象对应的值
     * 如指定对象对应值为null，则表字段也将更新为null
     * 指定对象与返回结果按照下标一一对应
     *
     * @param valueList 指定对象
     * @return
     */
    List<Integer> update(List<P> valueList);

    /**
     * 批量根据主键与SQL生成参数共同决定如何更新对应记录
     * 指定对象与返回结果按照下标一一对应
     *
     * @param valueList     指定对象
     * @param updateBuilder SQL生成
     * @return
     */
    List<Integer> update(List<P> valueList, UpdateBuilder updateBuilder);

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
    List<Integer> delete(List<P> paramList);
}