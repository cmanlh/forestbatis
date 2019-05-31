package com.lifeonwalden.forestbatis.example.bean;

import com.lifeonwalden.forestbatis.bean.AbstractDTOMapBean;
import com.lifeonwalden.forestbatis.example.meta.User_Book_RecordMetaInfo;
import com.lifeonwalden.forestbatis.example.meta.User_Book_RecordMetaInfo;

import java.util.Date;

public class User_Book_Record extends AbstractDTOMapBean {

    public String getUSER_ID() {
        return (String) this.dataMap.get(User_Book_RecordMetaInfo.USER_ID);
    }

    public User_Book_Record setUSER_ID(String USER_ID) {
        this.dataMap.put(User_Book_RecordMetaInfo.USER_ID, USER_ID);

        return this;
    }

    public String getBook_id() {
        return (String) this.dataMap.get(User_Book_RecordMetaInfo.book_id);
    }

    public User_Book_Record setBook_id(String book_id) {
        this.dataMap.put(User_Book_RecordMetaInfo.book_id, book_id);

        return this;
    }

    public Date getBorrowDate() {
        return (Date) this.dataMap.get(User_Book_RecordMetaInfo.borrowDate);
    }

    public User_Book_Record setBorrowDate(Date borrowDate) {
        this.dataMap.put(User_Book_RecordMetaInfo.borrowDate, borrowDate);

        return this;
    }

    public Date getReturnDate() {
        return (Date) this.dataMap.get(User_Book_RecordMetaInfo.returnDate);
    }

    public User_Book_Record setReturnDate(Date returnDate) {
        this.dataMap.put(User_Book_RecordMetaInfo.returnDate, returnDate);

        return this;
    }
}
