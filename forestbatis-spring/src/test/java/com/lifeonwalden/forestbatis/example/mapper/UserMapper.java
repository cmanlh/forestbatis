package com.lifeonwalden.forestbatis.example.mapper;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.mapper.AbstractSpringKeyMapper;
import com.lifeonwalden.forestbatis.meta.TableMeta;
import com.lifeonwalden.forestbatis.result.*;
import com.lifeonwalden.forestbatis.util.SingletonParameterHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonRecordHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonReturnColumnHandlerFactory;

public class UserMapper extends AbstractSpringKeyMapper<User> {
    @Override
    protected SelectBuilder<User> getFullSelectBuilder() {
        return UserBuilder.FULL_SELECT;
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
    protected InsertBuilder<User> getBaseInsertBuilder() {
        return UserBuilder.INSERT;
    }

    @Override
    protected DeleteBuilder<User> getBaseDeleteBuilder() {
        return UserBuilder.DELETE_QUERY;
    }

    @Override
    protected SelectBuilder<User> getBaseSelectBuilder() {
        return UserBuilder.SELECT;
    }

    @Override
    protected RecordHandler getBaseRecordHandler() {
        return SingletonRecordHandlerFactory.<UserMapper, AbstractRecordHandler<User>>getOrCreate(UserMapper.class, handler -> new AbstractRecordHandler<User>() {
            @Override
            public User newBeanInstance() {
                return new User();
            }
        });
    }

    @Override
    protected ParameterHandler getParameterHandler() {
        return SingletonParameterHandlerFactory.getOrCreate(DefaultParameterhandler.class);
    }

    @Override
    protected ReturnColumnHanlder getReturnColumnHandler() {
        return SingletonReturnColumnHandlerFactory.getOrCreate(DefaultReturnColumnHanlder.class);
    }

    @Override
    protected TableMeta getTable() {
        return UserBuilder.TABLE;
    }
}
