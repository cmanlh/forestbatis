package com.lifeonwalden.forestbatis.result;

import java.sql.ResultSet;
import java.util.Set;

/**
 * 将数据库返回的每条记录转换为对应的业务对象
 */
public interface RecordHandler<P> {
    /**
     * @param resultSet
     * @param tableColumnSet
     * @param notTableColumnSet
     * @return
     */
    P convert(ResultSet resultSet, Set<String> tableColumnSet, Set<ColumnMetaData> notTableColumnSet);
}