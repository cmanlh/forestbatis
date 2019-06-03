package com.lifeonwalden.forestbatis.result;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.exception.DataAccessException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultReturnColumnHanlder implements ReturnColumnHanlder {

    @Override
    public List<ColumnInfo> setupReturnColumn(ResultSet rs) {
        List<ColumnInfo> columnInfoList = new ArrayList<>();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setIndex(i).setPropertyName(rsmd.getColumnLabel(i)).setJdbcType(JdbcType.valueOf(rsmd.getColumnType(i)));

                columnInfoList.add(columnInfo);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch meta information of result set.", e);
        }

        return columnInfoList;
    }
}
