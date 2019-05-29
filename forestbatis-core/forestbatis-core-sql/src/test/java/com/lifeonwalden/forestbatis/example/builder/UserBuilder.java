package com.lifeonwalden.forestbatis.example.builder;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.meta.UserTableInfo;
import com.lifeonwalden.forestbatis.example.test.DBConfig;
import com.lifeonwalden.forestbatis.meta.Eq;
import com.lifeonwalden.forestbatis.meta.TableNode;

import java.util.Arrays;

public class UserBuilder {
    public final static SelectBuilder SELECT = new SelectBuilder<User>(
            new TableNode(UserTableInfo.TABLE),
            DBConfig.config,
            UserTableInfo.TABLE.getColumn().get(),
            new Eq<User>(UserTableInfo.Id, user -> user.isPresent() && null != user.get().getId())
                    .and(new Eq<User>(UserTableInfo.Name, user -> user.isPresent() && null != user.get().getName()))
                    .and(new Eq<User>(UserTableInfo.Age, user -> user.isPresent() && null != user.get().getAge()))
                    .and(new Eq<User>(UserTableInfo.Birthday, user -> user.isPresent() && null != user.get().getBirthday()))
    );

    public final static DeleteBuilder DELETE = new DeleteBuilder<User>(
            new TableNode(UserTableInfo.TABLE),
            DBConfig.config,
            new Eq(UserTableInfo.Id)
    );

    public final static UpdateBuilder UPDATE = new UpdateBuilder<User>(
            new TableNode(UserTableInfo.TABLE),
            DBConfig.config,
            UserTableInfo.TABLE.getColumn().get(),
            new Eq(UserTableInfo.Id)
    ).excludeUpdateColumn(Arrays.asList(UserTableInfo.Id));

    public final static InsertBuilder INSERT = new InsertBuilder<User>(
            new TableNode(UserTableInfo.TABLE),
            DBConfig.config,
            UserTableInfo.TABLE.getColumn().get()
    );
}
