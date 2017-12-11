package com.lifeonwalden.forestbatis.biz.dao;

import java.util.List;

public interface CommonDAO<T, E> {
    /**
     * full select
     *
     * @return
     */
    List<T> selectAll();

    /**
     * select based on query condition
     *
     * @param param
     * @return
     */
    List<T> select(T param);

    /**
     * select special columns based on query condition
     *
     * @param param
     * @return
     */
    List<T> directSelect(E param);

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

    /**
     * logically remove based on query condition
     *
     * @param param
     * @return
     */
    Integer logicalRemove(T param);
}
