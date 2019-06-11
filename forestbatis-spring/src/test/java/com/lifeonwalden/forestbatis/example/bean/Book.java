package com.lifeonwalden.forestbatis.example.bean;

import com.lifeonwalden.forestbatis.bean.AbstractDTOMapBean;
import com.lifeonwalden.forestbatis.example.meta.BookMetaInfo;
import com.lifeonwalden.forestbatis.example.meta.BookMetaInfo;

import java.math.BigDecimal;
import java.util.Date;

public class Book extends AbstractDTOMapBean {

    public String getId() {
        return (String) this.dataMap.get(BookMetaInfo.id);
    }

    public Book setId(String id) {
        this.dataMap.put(BookMetaInfo.id, id);

        return this;
    }

    public String getName() {
        return (String) this.dataMap.get(BookMetaInfo.name);
    }

    public Book setName(String name) {
        this.dataMap.put(BookMetaInfo.name, name);

        return this;
    }

    public String getPublisher() {
        return (String) this.dataMap.get(BookMetaInfo.publisher);
    }

    public Book setPublisher(String publisher) {
        this.dataMap.put(BookMetaInfo.publisher, publisher);

        return this;
    }

    public Date getPublishTime() {
        return (Date) this.dataMap.get(BookMetaInfo.publishTime);
    }

    public Book setPublishTime(Date publishTime) {
        this.dataMap.put(BookMetaInfo.publishTime, publishTime);

        return this;
    }
}
