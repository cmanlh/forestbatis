package com.lifeonwalden.forestbatis.parsing;

import com.lifeonwalden.forestbatis.bean.ParameterInfo;
import com.lifeonwalden.forestbatis.bean.PropertyInfo;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PropertyParser {
    Logger logger = LoggerFactory.getLogger(PropertyParser.class);

    String OPEN_TOKEN = "#{";
    String CLOSE_TOKEN = "}";

    String PROP_JDBC_TYPE = "JdbcType";
    String PROP_LIST_SIZE = "ListSize";

    char SIGN_SPLITTER = ',';
    char SIGN_EQUAL = '=';

    int OPEN_TOKEN_SIZE = OPEN_TOKEN.length();
    int CLOSE_TOKEN_SIZE = CLOSE_TOKEN.length();

    static ParameterInfo parse(String sql) {
        if (null == sql || sql.isEmpty()) {
            throw new RuntimeException("Invalid SQL statement");
        }

        int searchFromIndex = 0;
        int tokenStartIndex = sql.indexOf(OPEN_TOKEN, searchFromIndex);
        int tokenEndIndex;
        int tokenSearchStartIndex;
        int nameSplitterIndex;
        int otherSplitterIndex;
        int equalSplitterIndex;
        char[] src = sql.toCharArray();
        String propValue;
        StringBuilder sqlBuilder = new StringBuilder();
        List<PropertyInfo> propertyInfoList = new ArrayList<>();
        int parameterIndex = 0;

        if (logger.isDebugEnabled()) {
            if (-1 == tokenStartIndex) {
                return new ParameterInfo().setProps(Optional.empty()).setSql(sql).setDebugSql(sql);
            }

            StringBuilder debugSqlBuilder = new StringBuilder();
            while (tokenStartIndex > -1) {
                sqlBuilder.append(src, searchFromIndex, tokenStartIndex - searchFromIndex);
                debugSqlBuilder.append(src, searchFromIndex, tokenStartIndex - searchFromIndex);

                PropertyInfo propertyInfo = new PropertyInfo();

                tokenSearchStartIndex = tokenStartIndex + OPEN_TOKEN_SIZE;
                tokenEndIndex = sql.indexOf(CLOSE_TOKEN, tokenSearchStartIndex);
                if (-1 == tokenEndIndex) {
                    throw new RuntimeException("Invalid sql statement. The property expression is not closed.");
                }

                nameSplitterIndex = sql.indexOf(SIGN_SPLITTER, tokenSearchStartIndex);
                if (-1 == nameSplitterIndex || nameSplitterIndex > tokenEndIndex) {
                    throw new RuntimeException("Invalid sql statement. At least set the JdbcType for the property.");
                }
                propertyInfo.setName(sql.substring(tokenSearchStartIndex, nameSplitterIndex).trim());

                tokenSearchStartIndex = nameSplitterIndex + 1;
                int listSize = -1;
                while (true) {
                    otherSplitterIndex = sql.indexOf(SIGN_SPLITTER, tokenSearchStartIndex);
                    if (-1 == otherSplitterIndex || otherSplitterIndex > tokenEndIndex) {
                        equalSplitterIndex = sql.indexOf(SIGN_EQUAL, tokenSearchStartIndex);
                        if (-1 == equalSplitterIndex || equalSplitterIndex > tokenEndIndex) {
                            throw new RuntimeException("Invalid sql statement. No = for property expression.");
                        }
                        propValue = sql.substring(equalSplitterIndex + 1, tokenEndIndex).trim();
                        switch (sql.substring(tokenSearchStartIndex, equalSplitterIndex).trim()) {
                            case PROP_JDBC_TYPE: {
                                if (null != propertyInfo.getJdbcType()) {
                                    throw new RuntimeException("Duplicated JdbcType configuration for parameter : ".concat(propertyInfo.getName()));
                                }
                                propertyInfo.setJdbcType(JdbcType.nameOf(propValue));
                                if (null == propertyInfo.getJdbcType()) {
                                    throw new RuntimeException("Not a valid jdbc type : ".concat(propValue));
                                }
                                break;
                            }
                            case PROP_LIST_SIZE: {
                                if (-1 != listSize) {
                                    throw new RuntimeException("Duplicated ListSize configuration for parameter : ".concat(propertyInfo.getName()));
                                }
                                listSize = Integer.parseInt(propValue);
                                break;
                            }
                        }

                        break;
                    } else {
                        equalSplitterIndex = sql.indexOf(SIGN_EQUAL, tokenSearchStartIndex);
                        if (-1 == equalSplitterIndex || equalSplitterIndex > otherSplitterIndex) {
                            throw new RuntimeException("Invalid sql statement. No = for property expression.");
                        }
                        propValue = sql.substring(equalSplitterIndex + 1, otherSplitterIndex).trim();
                        switch (sql.substring(tokenSearchStartIndex, equalSplitterIndex).trim()) {
                            case PROP_JDBC_TYPE: {
                                if (null != propertyInfo.getJdbcType()) {
                                    throw new RuntimeException("Duplicated JdbcType configuration for parameter : ".concat(propertyInfo.getName()));
                                }
                                propertyInfo.setJdbcType(JdbcType.nameOf(propValue));
                                if (null == propertyInfo.getJdbcType()) {
                                    throw new RuntimeException("Not a valid jdbc type : ".concat(propValue));
                                }
                                break;
                            }
                            case PROP_LIST_SIZE: {
                                if (-1 != listSize) {
                                    throw new RuntimeException("Duplicated ListSize configuration for parameter : ".concat(propertyInfo.getName()));
                                }
                                listSize = Integer.parseInt(propValue);
                                break;
                            }
                        }

                        tokenSearchStartIndex = otherSplitterIndex + 1;
                    }
                }
            }
        }

        return null;
    }
}