package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;

import java.sql.ResultSet;
import java.util.Set;

public interface ReturnColumnHanlder {
    Set<ColumnInfo> setupReturnColumn(ResultSet rs);
}
