package com.lifeonwalden.forestbatis.example.builder;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.example.DBConfig;
import com.lifeonwalden.forestbatis.example.bean.Book;
import com.lifeonwalden.forestbatis.example.meta.BookMetaInfo;
import com.lifeonwalden.forestbatis.meta.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookBuilder {
    private final static String TABLE_NAME = "Book";
    private final static String ALIAS_NAME = "b";
    public final static PropertyMeta id = new AbstractPropertyMeta(BookMetaInfo.id, JdbcType.VARCHAR) {
    };
    public final static PropertyMeta name = new AbstractPropertyMeta(BookMetaInfo.name, JdbcType.VARCHAR) {
    };
    public final static PropertyMeta publisher = new AbstractPropertyMeta(BookMetaInfo.publisher, JdbcType.VARCHAR) {
    };
    public final static PropertyMeta publishTime = new AbstractPropertyMeta(BookMetaInfo.publishTime, JdbcType.DATETIME) {
    };
    private final static List<ColumnMeta> columnList = new ArrayList<>();
    public final static TableMeta TABLE = new AbstractTableMeta(TABLE_NAME, ALIAS_NAME, columnList) {
    };
    public final static ColumnMeta Id = new AbstractColumnMeta(BookMetaInfo.id, JdbcType.VARCHAR, TABLE, id) {
    };
    public final static ColumnMeta Name = new AbstractColumnMeta(BookMetaInfo.name, JdbcType.VARCHAR, TABLE, name) {
    };
    public final static ColumnMeta Publisher = new AbstractColumnMeta(BookMetaInfo.publisher, JdbcType.VARCHAR, TABLE, publisher) {
    };
    public final static ColumnMeta PublishTime = new AbstractColumnMeta(BookMetaInfo.publishTime, JdbcType.DATETIME, TABLE, publishTime) {
    };

    static {
        columnList.add(Id);
        columnList.add(Name);
        columnList.add(Publisher);
        columnList.add(PublishTime);
    }

    public final static QueryNode<Book> FULL_COLUMN_WITHOUT_NULL_QUERY =
            new Eq<Book>(Name, user -> user.isPresent() && null != user.get().getName())
                    .and(new Eq<Book>(Publisher, user -> user.isPresent() && null != user.get().getPublisher()))
                    .and(new Eq<Book>(PublishTime, user -> user.isPresent() && null != user.get().getPublishTime()));

    public final static SelectBuilder FULL_SELECT = new SelectBuilder<Book>(
            new TableNode(TABLE),
            TABLE.getColumn().get()
    );
    public final static SelectBuilder SELECT = FULL_SELECT.overrideQuery(FULL_COLUMN_WITHOUT_NULL_QUERY);

    public final static SelectBuilder GET = FULL_SELECT.overrideQuery(new Eq(Id));

    public final static DeleteBuilder DELETE_QUERY = new DeleteBuilder<Book>(
            new TableNode(TABLE),
            FULL_COLUMN_WITHOUT_NULL_QUERY
    );

    public final static DeleteBuilder DELETE = DELETE_QUERY.overrideQuery(new Eq(Id));

    public final static UpdateBuilder UPDATE_QUERY = new UpdateBuilder<Book>(
            new TableNode(TABLE),
            TABLE.getColumn().get(),
            FULL_COLUMN_WITHOUT_NULL_QUERY
    ).excludeUpdateColumn(Arrays.asList(Id));

    public final static UpdateBuilder UPDATE = UPDATE_QUERY.overrideQuery(new Eq(Id));

    public final static UpdateBuilder UPDATE_WITHOUT_NULL = UPDATE_QUERY.overrideUpdateColumn(false);

    public final static InsertBuilder INSERT = new InsertBuilder<Book>(
            new TableNode(TABLE),
            TABLE.getColumn().get()
    );
}
