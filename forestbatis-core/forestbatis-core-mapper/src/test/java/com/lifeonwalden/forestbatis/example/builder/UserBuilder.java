package com.lifeonwalden.forestbatis.example.builder;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.example.DBConfig;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.meta.UserMetaInfo;
import com.lifeonwalden.forestbatis.meta.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserBuilder {
    private final static String TABLE_NAME = "User";
    private final static String ALIAS_NAME = "u";
    public final static PropertyMeta id = new AbstractPropertyMeta(UserMetaInfo.id, JdbcType.VARCHAR) {
    };
    public final static PropertyMeta age = new AbstractPropertyMeta(UserMetaInfo.age, JdbcType.INTEGER) {
    };
    public final static PropertyMeta income = new AbstractPropertyMeta(UserMetaInfo.income, JdbcType.DECIMAL) {
    };
    public final static PropertyMeta birthday = new AbstractPropertyMeta(UserMetaInfo.birthday, JdbcType.DATE) {
    };
    public final static PropertyMeta sex = new AbstractPropertyMeta(UserMetaInfo.sex, JdbcType.INTEGER) {
    };
    private final static List<ColumnMeta> columnList = new ArrayList<>();
    public final static TableMeta TABLE = new AbstractTableMeta(TABLE_NAME, ALIAS_NAME, columnList) {
    };
    public final static ColumnMeta Id = new AbstractColumnMeta(UserMetaInfo.id, JdbcType.VARCHAR, TABLE, id) {
    };
    public final static ColumnMeta Age = new AbstractColumnMeta(UserMetaInfo.age, JdbcType.INTEGER, TABLE, age) {
    };
    public final static ColumnMeta Income = new AbstractColumnMeta(UserMetaInfo.income, JdbcType.DECIMAL, TABLE, income) {
    };
    public final static ColumnMeta Birthday = new AbstractColumnMeta(UserMetaInfo.birthday, JdbcType.DATE, TABLE, birthday) {
    };
    public final static ColumnMeta Sex = new AbstractColumnMeta(UserMetaInfo.sex, JdbcType.INTEGER, TABLE, sex) {
    };

    static {
        columnList.add(Id);
        columnList.add(Age);
        columnList.add(Birthday);
        columnList.add(Income);
        columnList.add(Sex);
    }

    public final static QueryNode<User> FULL_COLUMN_WITHOUT_NULL_QUERY =
            new Eq<User>(Age, user -> user.isPresent() && null != user.get().getAge())
                    .and(new Eq<User>(Birthday, user -> user.isPresent() && null != user.get().getBirthday()))
                    .and(new Eq<User>(Income, user -> user.isPresent() && null != user.get().getIncome()))
                    .and(new Eq<User>(Sex, user -> user.isPresent() && null != user.get().getSex()));

    public final static SelectBuilder FULL_SELECT = new SelectBuilder<User>(
            new TableNode(TABLE),
            DBConfig.config,
            TABLE.getColumn().get()
    );

    public final static SelectBuilder SELECT = FULL_SELECT.overrideQuery(FULL_COLUMN_WITHOUT_NULL_QUERY);

    public final static SelectBuilder GET = FULL_SELECT.overrideQuery(new Eq(Id));

    public final static DeleteBuilder REMOVE = new DeleteBuilder<User>(
            new TableNode(TABLE),
            DBConfig.config,
            FULL_COLUMN_WITHOUT_NULL_QUERY
    );

    public final static DeleteBuilder DELETE = REMOVE.overrideQuery(new Eq(Id));

    public final static UpdateBuilder UPDATE_QUERY = new UpdateBuilder<User>(
            new TableNode(TABLE),
            DBConfig.config,
            TABLE.getColumn().get(),
            FULL_COLUMN_WITHOUT_NULL_QUERY
    ).excludeUpdateColumn(Arrays.asList(Id));

    public final static UpdateBuilder UPDATE = UPDATE_QUERY.overrideQuery(new Eq(Id));

    public final static UpdateBuilder UPDATE_WITHOUT_NULL = UPDATE.overrideUpdateColumn(false);

    public final static InsertBuilder INSERT = new InsertBuilder<User>(
            new TableNode(TABLE),
            DBConfig.config,
            TABLE.getColumn().get()
    );
}
