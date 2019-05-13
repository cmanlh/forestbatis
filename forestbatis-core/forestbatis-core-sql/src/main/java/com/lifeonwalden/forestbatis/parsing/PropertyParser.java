package com.lifeonwalden.forestbatis.parsing;

import com.lifeonwalden.forestbatis.bean.ParameterInfo;
import com.lifeonwalden.forestbatis.bean.PropertyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        if (logger.isDebugEnabled()) {
            if (-1 == tokenStartIndex) {
                return new ParameterInfo().setProps(Optional.empty()).setSql(sql).setDebugSql(sql);
            }

            StringBuilder sqlBuilder = new StringBuilder();
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
                if (-1 == nameSplitterIndex) {
                    throw new RuntimeException("Invalid sql statement. At least set the JdbcType for the property.");
                }
                propertyInfo.setName(sql.substring(tokenSearchStartIndex, nameSplitterIndex).trim());

                tokenSearchStartIndex = nameSplitterIndex + 1;
                otherSplitterIndex = sql.indexOf(SIGN_SPLITTER, tokenSearchStartIndex);
                if (-1 == otherSplitterIndex) {
                    equalSplitterIndex = sql.indexOf(SIGN_EQUAL, tokenSearchStartIndex);
                    if (-1 == equalSplitterIndex) {
                        throw new RuntimeException("Invalid sql statement. No = for property expression.");
                    }
                    switch (sql.substring(tokenSearchStartIndex, equalSplitterIndex)) {
                        case PROP_JDBC_TYPE:
                            break;
                        case PROP_LIST_SIZE:
                            break;
                    }
                }
            }
        }

        return null;
    }
}