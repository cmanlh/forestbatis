package com.lifeonwalden.forestbatis.example.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.meta.*;

import java.util.ArrayList;
import java.util.List;

public class AccountTableInfo {
    private final static String TABLE_NAME = "Account";
    private final static String ALIAS_NAME = "a";
    private final static String _id = "id";
    public final static PropertyMeta id = new AbstractPropertyMeta(_id, JdbcType.VARCHAR) {
    };
    private final static String _name = "name";
    public final static PropertyMeta name = new AbstractPropertyMeta(_name, JdbcType.VARCHAR) {
    };
    private final static String _userId = "userId";
    public final static PropertyMeta userId = new AbstractPropertyMeta(_userId, JdbcType.VARCHAR) {
    };
    private final static String _balance = "balance";
    public final static PropertyMeta balance = new AbstractPropertyMeta(_balance, JdbcType.DECIMAL) {
    };
    private final static List<ColumnMeta> columnList = new ArrayList<>();
    public final static TableMeta TABLE = new AbstractTableMeta(TABLE_NAME, ALIAS_NAME, columnList) {
    };
    public final static ColumnMeta Id = new AbstractColumnMeta(_id, JdbcType.VARCHAR, TABLE, id) {
    };
    public final static ColumnMeta Name = new AbstractColumnMeta(_name, JdbcType.VARCHAR, TABLE, name) {
    };
    public final static ColumnMeta UserId = new AbstractColumnMeta(_userId, JdbcType.VARCHAR, TABLE, userId) {
    };

    public final static ColumnMeta Balance = new AbstractColumnMeta(_balance, JdbcType.DECIMAL, TABLE, balance) {
    };

    static {
        columnList.add(Id);
        columnList.add(Name);
        columnList.add(UserId);
        columnList.add(Balance);
    }
}
