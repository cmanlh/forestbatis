package com.lifeonwalden.forestbatis.example.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.meta.*;

import java.util.ArrayList;
import java.util.List;

public class UserCreditTableInfo {
    private final static String TABLE_NAME = "UserCredit";
    private final static String ALIAS_NAME = "uc";
    private final static String _id = "id";
    public final static PropertyMeta id = new AbstractPropertyMeta(_id, JdbcType.VARCHAR) {
    };
    private final static String _userId = "userId";
    public final static PropertyMeta userId = new AbstractPropertyMeta(_userId, JdbcType.VARCHAR) {
    };
    private final static String _level = "level";
    public final static PropertyMeta level = new AbstractPropertyMeta(_level, JdbcType.INTEGER) {
    };
    private final static String _balance = "balance";
    public final static PropertyMeta balance = new AbstractPropertyMeta(_balance, JdbcType.DECIMAL) {
    };
    private final static String _total = "total";
    public final static PropertyMeta total = new AbstractPropertyMeta(_total, JdbcType.DECIMAL) {
    };
    private final static List<ColumnMeta> columnList = new ArrayList<>();
    public final static TableMeta TABLE = new AbstractTableMeta(TABLE_NAME, ALIAS_NAME, columnList) {
    };
    public final static ColumnMeta Id = new AbstractColumnMeta(_id, JdbcType.VARCHAR, TABLE, id) {
    };
    public final static ColumnMeta Level = new AbstractColumnMeta(_level, JdbcType.INTEGER, TABLE, level) {
    };
    public final static ColumnMeta UserId = new AbstractColumnMeta(_userId, JdbcType.VARCHAR, TABLE, userId) {
    };

    public final static ColumnMeta Balance = new AbstractColumnMeta(_balance, JdbcType.DECIMAL, TABLE, balance) {
    };

    public final static ColumnMeta Total = new AbstractColumnMeta(_total, JdbcType.DECIMAL, TABLE, total) {
    };

    static {
        columnList.add(Id);
        columnList.add(Level);
        columnList.add(UserId);
        columnList.add(Balance);
        columnList.add(Total);
    }
}
