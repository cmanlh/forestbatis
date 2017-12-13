package com.lifeonwalden.forestbatis.biz.dao;

import java.util.List;

public interface CommonDAO<T> {
    /**
     * select based on query condition
     *
     * @param param
     * @return
     */
    List<T> select(T param);

    /**
     * select special fields based on query condition
     *
     * @param param
     * @return
     */
    List<T> directSelect(T param);

    /**
     * full select
     *
     * @return
     */
    List<T> selectAll();

    /**
     * full select with special fields
     *
     * @return
     */
    List<T> directSelectAll(T param);

    /**
     * insert a record
     *
     * @param param
     * @return
     */
    Integer insert(T param);

    /**
     * remove based on query condition
     *
     * @param param
     * @return
     */
    Integer remove(T param);
}
