package com.lifeonwalden.forestbatis.example.mapper;

import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.example.bean.Book;
import com.lifeonwalden.forestbatis.example.builder.BookBuilder;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.mapper.AbstractKeyMapper;
import com.lifeonwalden.forestbatis.meta.TableMeta;
import com.lifeonwalden.forestbatis.result.*;
import com.lifeonwalden.forestbatis.util.SingletonParameterHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonRecordHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonReturnColumnHandlerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public class BookMapper extends AbstractKeyMapper<Book> {
    private Function<Void, Connection> connectionCreator;
    private Config config;

    public BookMapper(Config config, Function<Void, Connection> connectionCreator) {
        this.config = config;
        this.connectionCreator = connectionCreator;
    }

    @Override
    protected DeleteBuilder<Book> getKeyDeleteBuilder() {
        return BookBuilder.DELETE;
    }

    @Override
    protected UpdateBuilder<Book> getKeyUpdateBuilder(boolean withNull) {
        return withNull ? BookBuilder.UPDATE : BookBuilder.UPDATE_WITHOUT_NULL;
    }

    @Override
    protected SelectBuilder<Book> getGetBuilder() {
        return BookBuilder.GET;
    }

    @Override
    protected Config getConfig() {
        return this.config;
    }

    @Override
    protected Connection getConnection() {
        return connectionCreator.apply(null);
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
    protected SelectBuilder<Book> getFullSelectBuilder() {
        return UserBuilder.FULL_SELECT;
    }

    @Override
    protected InsertBuilder<Book> getBaseInsertBuilder() {
        return BookBuilder.INSERT;
    }

    @Override
    protected DeleteBuilder<Book> getBaseDeleteBuilder() {
        return BookBuilder.DELETE;
    }

    @Override
    protected SelectBuilder<Book> getBaseSelectBuilder() {
        return BookBuilder.SELECT;
    }

    @Override
    protected RecordHandler getBaseRecordHandler() {
        return SingletonRecordHandlerFactory.<BookMapper, AbstractRecordHandler<Book>>getOrCreate(BookMapper.class, handler -> new AbstractRecordHandler<Book>() {
            @Override
            public Book newBeanInstance() {
                return new Book();
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
