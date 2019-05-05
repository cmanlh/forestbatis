package com.lifeonwalden.forestbatis.example.builder;

import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.meta.UserTableInfo;
import com.lifeonwalden.forestbatis.meta.Eq;
import com.lifeonwalden.forestbatis.meta.TableNode;

public class UserBuilder {
    public final static SelectBuilder SELECT = new SelectBuilder<User>(
            UserTableInfo.TABLE.getColumn().get(),
            new TableNode(UserTableInfo.TABLE),
            new Eq<User>(UserTableInfo.Id, user -> user.isPresent() && null != user.get().getId())
                    .and(new Eq<User>(UserTableInfo.Name, user -> user.isPresent() && null != user.get().getName()))
                    .and(new Eq<User>(UserTableInfo.Age, user -> user.isPresent() && null != user.get().getAge()))
                    .and(new Eq<User>(UserTableInfo.Birthday, user -> user.isPresent() && null != user.get().getBirthday()))
    );
}
