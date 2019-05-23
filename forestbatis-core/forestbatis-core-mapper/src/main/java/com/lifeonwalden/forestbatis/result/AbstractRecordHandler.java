package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.AbstractDTOMapBean;
import com.lifeonwalden.forestbatis.bean.ColumnInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public abstract class AbstractRecordHandler<T> implements RecordHandler<T> {

    public abstract T newInstance();


    @Override
    public T convert(ResultSet resultSet, Set<ColumnInfo> columnSet) {
        AbstractDTOMapBean bean = (AbstractDTOMapBean) newInstance();
        columnSet.forEach(columnInfo -> {
            switch (columnInfo.getJdbcType()) {
                case VARCHAR:
                case NVARCHAR:
                case LONGVARCHAR:
                case TEXT:
                case MEDIUMTEXT:
                case LONGTEXT:
                case CHAR:
                case NCHAR:
                    try {
                        bean.put(columnInfo.getPropertyName(), resultSet.getString(columnInfo.getIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case INTEGER:
                case INT:
                    try {
                        bean.put(columnInfo.getPropertyName(), resultSet.getInt(columnInfo.getIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case DECIMAL:
                    try {
                        bean.put(columnInfo.getPropertyName(), resultSet.getBigDecimal(columnInfo.getIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case DATE:
                    try {
                        bean.put(columnInfo.getPropertyName(), resultSet.getDate(columnInfo.getIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case TIMESTAMP:
                case DATETIME:
                    try {
                        bean.put(columnInfo.getPropertyName(), resultSet.getDate(columnInfo.getIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case BIGINT:
                    try {
                        bean.put(columnInfo.getPropertyName(), resultSet.getLong(columnInfo.getIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    throw new RuntimeException("Not supported Jdbc Type.");
            }
        });
        return null;
    }
}
