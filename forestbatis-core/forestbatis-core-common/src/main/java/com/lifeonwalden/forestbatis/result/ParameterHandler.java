package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.StatementInfo;

import java.sql.PreparedStatement;

public interface ParameterHandler<T> {
    void set(StatementInfo statementInfo, PreparedStatement preparedStatement, T param);
}
