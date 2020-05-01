package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;
import com.lifeonwalden.forestbatis.exception.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AbstractRecordHandler<T> implements RecordHandler<T> {

    public abstract T newBeanInstance();

    @Override
    public T convert(ResultSet resultSet, List<ColumnInfo> columnInfoList) {
        T t = newBeanInstance();
        Map bean = (Map) t;
        try {
            String propertyName;
            int index;
            for (ColumnInfo columnInfo : columnInfoList) {
                propertyName = columnInfo.getPropertyName();
                index = columnInfo.getIndex();
                switch (columnInfo.getJdbcType()) {
                    case VARCHAR:
                    case NVARCHAR:
                    case LONGVARCHAR:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT:
                    case CHAR:
                    case NCHAR:
                        bean.put(propertyName, resultSet.getString(index));
                        break;
                    case INTEGER:
                    case INT: {
                        int value = resultSet.getInt(index);
                        if (!resultSet.wasNull()) {
                            bean.put(propertyName, value);
                        }
                        break;
                    }
                    case NUMERIC:
                    case DECIMAL:
                        bean.put(propertyName, resultSet.getBigDecimal(index));
                        break;
                    case DATE:
                        bean.put(propertyName, resultSet.getDate(index));
                        break;
                    case TIMESTAMP:
                    case DATETIME:
                        bean.put(propertyName, resultSet.getTimestamp(index));
                        break;
                    case BIGINT: {
                        long value = resultSet.getLong(index);
                        if (!resultSet.wasNull()) {
                            bean.put(propertyName, value);
                        }
                        break;
                    }
                    default:
                        throw new RuntimeException("Not supported Jdbc Type.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return t;
    }
}
