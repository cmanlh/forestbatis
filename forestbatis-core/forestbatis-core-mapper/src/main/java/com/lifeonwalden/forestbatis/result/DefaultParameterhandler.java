package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.PropertyInfo;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DefaultParameterhandler implements ParameterHandler {
    private static Logger logger = LoggerFactory.getLogger(DefaultParameterhandler.class);

    @Override
    public <T> void set(StatementInfo statementInfo, PreparedStatement preparedStatement, T param) throws SQLException {
        List<PropertyInfo> propertyInfoList = statementInfo.getProps().get();

        if (logger.isDebugEnabled()) {
            String debugSql = statementInfo.getDebugSql();

            for (PropertyInfo propertyInfo : statementInfo.getProps().get()) {
                debugSql = fillDebugSql(debugSql, propertyInfo, param);
            }

            logger.debug(debugSql);
        }

        for (PropertyInfo propertyInfo : statementInfo.getProps().get()) {
            switch (propertyInfo.getJdbcType()) {
                case VARCHAR:
                case NVARCHAR:
                case LONGVARCHAR:
                case TEXT:
                case MEDIUMTEXT:
                case LONGTEXT:
                case CHAR:
                case NCHAR: {
                    String value = fetchValue(propertyInfo, param);
                    if (null == value) {
                        preparedStatement.setNull(propertyInfo.getIndex(), propertyInfo.getJdbcType().getValue());
                    } else {
                        preparedStatement.setString(propertyInfo.getIndex(), value);
                    }
                    break;
                }
                case INTEGER:
                case INT: {
                    Integer value = fetchValue(propertyInfo, param);
                    if (null == value) {
                        preparedStatement.setNull(propertyInfo.getIndex(), propertyInfo.getJdbcType().getValue());
                    } else {
                        preparedStatement.setInt(propertyInfo.getIndex(), value);
                    }
                    break;
                }
                case DECIMAL: {
                    BigDecimal value = fetchValue(propertyInfo, param);
                    if (null == value) {
                        preparedStatement.setNull(propertyInfo.getIndex(), propertyInfo.getJdbcType().getValue());
                    } else {
                        preparedStatement.setBigDecimal(propertyInfo.getIndex(), value);
                    }
                    break;
                }
                case DATE: {
                    Date value = fetchValue(propertyInfo, param);
                    if (null == value) {
                        preparedStatement.setNull(propertyInfo.getIndex(), propertyInfo.getJdbcType().getValue());
                    } else {
                        preparedStatement.setDate(propertyInfo.getIndex(), new java.sql.Date(value.getTime()));
                    }
                    break;
                }
                case TIMESTAMP:
                case DATETIME: {
                    Date value = fetchValue(propertyInfo, param);
                    if (null == value) {
                        preparedStatement.setNull(propertyInfo.getIndex(), propertyInfo.getJdbcType().getValue());
                    } else {
                        preparedStatement.setTimestamp(propertyInfo.getIndex(), new Timestamp(value.getTime()));
                    }
                    break;
                }
                case BIGINT: {
                    Long value = fetchValue(propertyInfo, param);
                    if (null == value) {
                        preparedStatement.setNull(propertyInfo.getIndex(), propertyInfo.getJdbcType().getValue());
                    } else {
                        preparedStatement.setLong(propertyInfo.getIndex(), value);
                    }
                    break;
                }
                default:
                    throw new RuntimeException("Not supported Jdbc Type.");
            }
        }
    }

    protected <T> String fillDebugSql(String sql, PropertyInfo propertyInfo, T param) {
        return sql.replace("#".concat(String.valueOf(propertyInfo.getIndex())), fetchString(propertyInfo, param));
    }

    protected <P, T> P fetchValue(PropertyInfo propertyInfo, T param) {
        Map bean = (Map) param;
        if (propertyInfo.isListProperty()) {
            Object _list = bean.get(propertyInfo.getName());
            if (null == _list) {
                return null;
            }

            List list = (List) _list;
            Object result = list.get(propertyInfo.getListIndex());
            if (null == result) {
                return null;
            }
            return (P) result;
        } else {
            Object result = bean.get(propertyInfo.getName());
            if (null == result) {
                return null;
            }
            return (P) result;
        }
    }

    protected <T> String fetchString(PropertyInfo propertyInfo, T param) {
        Object value = fetchValue(propertyInfo, param);
        if (value instanceof java.util.Date) {
            switch (propertyInfo.getJdbcType()) {
                case DATE:
                    return DateUtil.formatDate((java.util.Date) value, DateUtil.DATE);
                default:
                    return DateUtil.formatDate((java.util.Date) value, DateUtil.DATE_TIME);
            }
        } else {
            return String.valueOf(value);
        }
    }
}
