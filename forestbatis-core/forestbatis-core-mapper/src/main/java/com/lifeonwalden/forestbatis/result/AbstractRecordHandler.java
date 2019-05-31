package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.AbstractDTOMapBean;
import com.lifeonwalden.forestbatis.bean.ColumnInfo;
import com.lifeonwalden.forestbatis.exception.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractRecordHandler<T> implements RecordHandler<T> {

    public abstract T newBeanInstance();

    @Override
    public T convert(ResultSet resultSet, List<ColumnInfo> columnInfoList) {
        T t = newBeanInstance();
        AbstractDTOMapBean bean = (AbstractDTOMapBean) t;
        try {
            for (ColumnInfo columnInfo : columnInfoList) {
                switch (columnInfo.getJdbcType()) {
                    case VARCHAR:
                    case NVARCHAR:
                    case LONGVARCHAR:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT:
                    case CHAR:
                    case NCHAR:
                        bean.put(columnInfo.getPropertyName(), resultSet.getString(columnInfo.getIndex()));
                        break;
                    case INTEGER:
                    case INT: {
                        Object value = resultSet.getObject(columnInfo.getIndex());
                        if (null == value) {
                            bean.put(columnInfo.getPropertyName(), null);
                        } else {
                            bean.put(columnInfo.getPropertyName(), value);
                        }
                        break;
                    }
                    case DECIMAL:
                        bean.put(columnInfo.getPropertyName(), resultSet.getBigDecimal(columnInfo.getIndex()));
                        break;
                    case DATE:
                    case TIMESTAMP:
                    case DATETIME:
                        bean.put(columnInfo.getPropertyName(), resultSet.getDate(columnInfo.getIndex()));
                        break;
                    case BIGINT: {
                        Object value = resultSet.getObject(columnInfo.getIndex());
                        if (null == value) {
                            bean.put(columnInfo.getPropertyName(), null);
                        } else {
                            bean.put(columnInfo.getPropertyName(), value);
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
