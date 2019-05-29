package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

/**
 * 将数据库返回的每条记录转换为对应的业务对象
 */
public interface RecordHandler<P> {
    /**
     * @param resultSet
     * @param columnList
     * @return
     */
    P convert(ResultSet resultSet, List<ColumnInfo> columnList);
}