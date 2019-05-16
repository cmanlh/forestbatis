package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.AbstractDTOMapBean;
import com.lifeonwalden.forestbatis.bean.PropertyInfo;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public abstract class AbstractParameterhandler<T> implements ParameterHandler<T> {
    private static Logger logger = LoggerFactory.getLogger(AbstractParameterhandler.class);

    @Override
    public void set(StatementInfo statementInfo, PreparedStatement preparedStatement, T param) throws SQLException {
        List<PropertyInfo> propertyInfoList = statementInfo.getProps().get();

        if (logger.isDebugEnabled()) {
            String debugSql = statementInfo.getDebugSql();

            for (PropertyInfo propertyInfo : statementInfo.getProps().get()) {
                debugSql = fillDebugSql(debugSql, propertyInfo, param);
            }
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
                case NCHAR:
                    preparedStatement.setString(propertyInfo.getIndex(), fetchValue(propertyInfo, param));
                    break;
                case INTEGER:
                case INT:
                    preparedStatement.setInt(propertyInfo.getIndex(), fetchValue(propertyInfo, param));
                    break;
                case DECIMAL:
                    preparedStatement.setBigDecimal(propertyInfo.getIndex(), fetchValue(propertyInfo, param));
                    break;
                case DATE:
                    preparedStatement.setDate(propertyInfo.getIndex(), new Date((this.<java.util.Date>fetchValue(propertyInfo, param)).getTime()));
                    break;
                case TIMESTAMP:
                case DATETIME:
                    preparedStatement.setTimestamp(propertyInfo.getIndex(), new Timestamp((this.<java.util.Date>fetchValue(propertyInfo, param)).getTime()));
                    break;
                case BIGINT:
                    preparedStatement.setLong(propertyInfo.getIndex(), fetchValue(propertyInfo, param));
                    break;
                default:
                    throw new RuntimeException("Not supported Jdbc Type.");
            }
        }
    }

    protected String fillDebugSql(String sql, PropertyInfo propertyInfo, T param) {
        return sql.replace("#".concat(String.valueOf(propertyInfo.getIndex())), fetchValue(propertyInfo, param));
    }

    protected <P> P fetchValue(PropertyInfo propertyInfo, T param) {
        AbstractDTOMapBean bean = (AbstractDTOMapBean) param;
        if (propertyInfo.isListProperty()) {
            List list = (List) bean.get(propertyInfo.getName());
            return (P) list.get(propertyInfo.getListIndex());
        } else {
            return (P) bean.get(propertyInfo.getName());
        }
    }
}
