package com.lifeonwalden.forestbatis.example.mapper;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.example.bean.User_Book_Record;
import com.lifeonwalden.forestbatis.example.builder.BookBuilder;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.mapper.AbstractCommonMapper;
import com.lifeonwalden.forestbatis.meta.TableMeta;
import com.lifeonwalden.forestbatis.result.*;
import com.lifeonwalden.forestbatis.util.SingletonParameterHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonRecordHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonReturnColumnHandlerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public class User_Book_RecordMapper extends AbstractCommonMapper<User_Book_Record> {
    private Function<Void, Connection> connectionCreator;
    private Config config;

    public User_Book_RecordMapper(Config config, Function<Void, Connection> connectionCreator) {
        this.config = config;
        this.connectionCreator = connectionCreator;
    }

    @Override
    protected Config getConfig() {
        return this.config;
    }

    @Override
    protected Connection getConnection() {
        return this.connectionCreator.apply(null);
    }

    @Override
    protected void releaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected SelectBuilder<User_Book_Record> getFullSelectBuilder() {
        return UserBuilder.FULL_SELECT;
    }

    @Override
    protected InsertBuilder<User_Book_Record> getBaseInsertBuilder() {
        return BookBuilder.INSERT;
    }

    @Override
    protected DeleteBuilder<User_Book_Record> getBaseDeleteBuilder() {
        return BookBuilder.DELETE;
    }

    @Override
    protected SelectBuilder<User_Book_Record> getBaseSelectBuilder() {
        return BookBuilder.SELECT;
    }

    @Override
    protected RecordHandler getBaseRecordHandler() {
        return SingletonRecordHandlerFactory.<User_Book_RecordMapper, AbstractRecordHandler<User_Book_Record>>getOrCreate(User_Book_RecordMapper.class, handler -> new AbstractRecordHandler<User_Book_Record>() {
            @Override
            public User_Book_Record newBeanInstance() {
                return new User_Book_Record();
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
        return BookBuilder.TABLE;
    }
}
