package com.lifeonwalden.forestbatis.example.builder;

import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.example.DBConfig;
import com.lifeonwalden.forestbatis.example.bean.User_Book_Record;
import com.lifeonwalden.forestbatis.example.meta.User_Book_RecordMetaInfo;
import com.lifeonwalden.forestbatis.meta.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User_Book_RecordBuilder {
    private final static String TABLE_NAME = "User_Book_Record";
    private final static String ALIAS_NAME = "ub";
    public final static PropertyMeta uSER_ID = new AbstractPropertyMeta(User_Book_RecordMetaInfo.USER_ID, JdbcType.VARCHAR) {
    };
    public final static PropertyMeta book_id = new AbstractPropertyMeta(User_Book_RecordMetaInfo.book_id, JdbcType.VARCHAR) {
    };
    public final static PropertyMeta borrowDate = new AbstractPropertyMeta(User_Book_RecordMetaInfo.borrowDate, JdbcType.DATE) {
    };
    public final static PropertyMeta returnDate = new AbstractPropertyMeta(User_Book_RecordMetaInfo.returnDate, JdbcType.DATE) {
    };
    private final static List<ColumnMeta> columnList = new ArrayList<>();
    public final static TableMeta TABLE = new AbstractTableMeta(TABLE_NAME, ALIAS_NAME, columnList) {
    };
    public final static ColumnMeta USER_ID = new AbstractColumnMeta(User_Book_RecordMetaInfo.USER_ID, JdbcType.VARCHAR, TABLE, uSER_ID) {
    };
    public final static ColumnMeta Book_id = new AbstractColumnMeta(User_Book_RecordMetaInfo.book_id, JdbcType.VARCHAR, TABLE, book_id) {
    };
    public final static ColumnMeta BorrowDate = new AbstractColumnMeta(User_Book_RecordMetaInfo.borrowDate, JdbcType.DATE, TABLE, borrowDate) {
    };
    public final static ColumnMeta ReturnDate = new AbstractColumnMeta(User_Book_RecordMetaInfo.returnDate, JdbcType.DATE, TABLE, returnDate) {
    };

    static {
        columnList.add(USER_ID);
        columnList.add(Book_id);
        columnList.add(BorrowDate);
        columnList.add(ReturnDate);
    }

    public final static QueryNode<User_Book_Record> FULL_WITHOUT_NULL_QUERY =
            new Eq<User_Book_Record>(USER_ID, userBookRecord -> userBookRecord.isPresent() && null != userBookRecord.get().getUSER_ID())
                    .and(new Eq<User_Book_Record>(Book_id, userBookRecord -> userBookRecord.isPresent() && null != userBookRecord.get().getBook_id()))
                    .and(new Eq<User_Book_Record>(BorrowDate, userBookRecord -> userBookRecord.isPresent() && null != userBookRecord.get().getBorrowDate()))
                    .and(new Eq<User_Book_Record>(ReturnDate, userBookRecord -> userBookRecord.isPresent() && null != userBookRecord.get().getReturnDate()));

    public final static SelectBuilder SELECT = new SelectBuilder<User_Book_Record>(
            new TableNode(TABLE),
            DBConfig.config,
            TABLE.getColumn().get(),
            FULL_WITHOUT_NULL_QUERY
    );

    public final static DeleteBuilder REMOVE = new DeleteBuilder<User_Book_Record>(
            new TableNode(TABLE),
            DBConfig.config,
            FULL_WITHOUT_NULL_QUERY
    );

    public final static UpdateBuilder UPDATE_QUERY = new UpdateBuilder<User_Book_Record>(
            new TableNode(TABLE),
            DBConfig.config,
            TABLE.getColumn().get(),
            FULL_WITHOUT_NULL_QUERY
    );

    public final static UpdateBuilder UPDATE_QUERY_WITHOUT_NULL = UPDATE_QUERY.overrideUpdateColumn(false);

    public final static UpdateBuilder UPDATE_WITHOUT_NULL = UPDATE_QUERY.overrideUpdateColumn(false);

    public final static InsertBuilder INSERT = new InsertBuilder<User_Book_Record>(
            new TableNode(TABLE),
            DBConfig.config,
            TABLE.getColumn().get()
    );

    public final static InsertBuilder INSERT_WITHOUT_NULL = INSERT.overrideInsertColumn(false);
}
