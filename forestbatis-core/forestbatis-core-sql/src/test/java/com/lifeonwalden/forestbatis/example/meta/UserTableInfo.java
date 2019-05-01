package com.lifeonwalden.forestbatis.example.meta;

import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.meta.*;

import java.util.ArrayList;
import java.util.List;

public class UserTableInfo {
    private final static String TABLE_NAME = "user";
    private final static String ALIAS_NAME = "u";
    private final static String _id = "id";
    public final static PropertyMeta id = new AbstractPropertyMeta(_id, JdbcType.VARCHAR) {
    };
    private final static String _name = "name";
    public final static PropertyMeta name = new AbstractPropertyMeta(_name, JdbcType.VARCHAR) {
    };
    private final static String _age = "age";
    public final static PropertyMeta age = new AbstractPropertyMeta(_age, JdbcType.INTEGER) {
    };
    private final static String _birthday = "birthday";
    public final static PropertyMeta birthday = new AbstractPropertyMeta(_birthday, JdbcType.DATE) {
    };
    private final static List<ColumnMeta> columnList = new ArrayList<>();
    public final static TableMeta TABLE = new AbstractTableMeta(TABLE_NAME, ALIAS_NAME, columnList) {
    };
    public final static ColumnMeta Id = new AbstractColumnMeta(_id, JdbcType.VARCHAR, TABLE, id) {
    };
    public final static ColumnMeta Name = new AbstractColumnMeta(_name, JdbcType.VARCHAR, TABLE, name) {
    };
    public final static ColumnMeta Age = new AbstractColumnMeta(_age, JdbcType.INTEGER, TABLE, age) {
    };

    public final static ColumnMeta Birthday = new AbstractColumnMeta(_birthday, JdbcType.DATE, TABLE, birthday) {
    };

    static {
        columnList.add(Id);
        columnList.add(Name);
        columnList.add(Age);
        columnList.add(Birthday);
    }
}
