package com.lifeonwalden.forestbatis.biz.dao;

public interface KeyBasedDAO<T> extends CommonDAO<T> {
    /**
     * update the record based on primary key
     *
     * @param param
     * @return
     */
    Integer update(T param);

    /**
     * update non-null field of the record based on primary key
     *
     * @param param
     * @return
     */
    Integer updateDynamic(T param);

    /**
     * update the special field of the record based on primary key
     *
     * @param param
     * @return
     */
    Integer directUpdate(T param);

    /**
     * get a record based on primary key
     *
     * @param param
     * @return
     */
    T get(T param);

    /**
     * delete a record based on primary key
     *
     * @param param
     * @return
     */
    Integer delete(T param);

    /**
     * logically delete a record based on primary key
     *
     * @param param
     * @return
     */
    Integer logicalDelete(T param);
}
