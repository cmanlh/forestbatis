package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;

import java.sql.ResultSet;
import java.util.List;

public interface ReturnColumnHanlder {
    List<ColumnInfo> setupReturnColumn(ResultSet rs);
}
