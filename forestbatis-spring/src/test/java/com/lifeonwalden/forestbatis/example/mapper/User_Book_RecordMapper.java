package com.lifeonwalden.forestbatis.example.mapper;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.example.bean.User_Book_Record;
import com.lifeonwalden.forestbatis.example.builder.User_Book_RecordBuilder;
import com.lifeonwalden.forestbatis.mapper.AbstractSpringCommonMapper;
import com.lifeonwalden.forestbatis.meta.TableMeta;
import com.lifeonwalden.forestbatis.result.*;
import com.lifeonwalden.forestbatis.util.SingletonParameterHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonRecordHandlerFactory;
import com.lifeonwalden.forestbatis.util.SingletonReturnColumnHandlerFactory;

public class User_Book_RecordMapper extends AbstractSpringCommonMapper<User_Book_Record> {
    @Override
    protected SelectBuilder<User_Book_Record> getFullSelectBuilder() {
        return User_Book_RecordBuilder.FULL_SELECT;
    }

    @Override
    protected InsertBuilder<User_Book_Record> getBaseInsertBuilder() {
        return User_Book_RecordBuilder.INSERT;
    }

    @Override
    protected DeleteBuilder<User_Book_Record> getBaseDeleteBuilder() {
        return User_Book_RecordBuilder.DELETE_QUERY;
    }

    @Override
    protected SelectBuilder<User_Book_Record> getBaseSelectBuilder() {
        return User_Book_RecordBuilder.SELECT;
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
        return User_Book_RecordBuilder.TABLE;
    }
}
