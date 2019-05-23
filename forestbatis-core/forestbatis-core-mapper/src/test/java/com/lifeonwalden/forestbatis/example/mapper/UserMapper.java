package com.lifeonwalden.forestbatis.example.mapper;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.mapper.AbstractKeyMapper;
import com.lifeonwalden.forestbatis.meta.TableMeta;
import com.lifeonwalden.forestbatis.result.*;
import com.lifeonwalden.forestbatis.sql.InsertBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class UserMapper extends AbstractKeyMapper<User> {
    private Connection connection;

    public UserMapper(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected DeleteBuilder<User> getKeyDeleteBuilder() {
        return UserBuilder.DELETE;
    }

    @Override
    protected UpdateBuilder<User> getKeyUpdateBuilder(boolean withNull) {
        return withNull ? UserBuilder.UPDATE : UserBuilder.UPDATE_WITHOUT_NULL;
    }

    @Override
    protected SelectBuilder<User> getGetBuilder() {
        return UserBuilder.GET;
    }

    @Override
    protected Connection getConnection() {
        return connection;
    }

    @Override
    protected void releaseConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected InsertBuilder<User> getBaseInsertBuilder() {
        return UserBuilder.INSERT;
    }

    @Override
    protected DeleteBuilder<User> getBaseDeleteBuilder() {
        return UserBuilder.DELETE;
    }

    @Override
    protected UpdateBuilder<User> getBaseUpdateBuilder() {
        return UserBuilder.UPDATE_QUERY;
    }

    @Override
    protected SelectBuilder<User> getBaseSelectBuilder() {
        return UserBuilder.SELECT;
    }

    @Override
    protected RecordHandler getBaseRecordHandler() {
        return new AbstractRecordHandler() {
            @Override
            public Object newBeanInstance() {
                return new User();
            }
        };
    }

    @Override
    protected ParameterHandler getParameterHandler() {
        return DefaultParameterhandler.getInstance();
    }

    @Override
    protected ReturnColumnHanlder getReturnColumnHandler() {
        return null;
    }

    @Override
    protected TableMeta getTable() {
        return UserBuilder.TABLE;
    }
}
