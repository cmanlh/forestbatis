package com.lifeonwalden.forestbatis.biz.bean;

import com.lifeonwalden.forestbatis.biz.support.OrderBean;

import java.util.ArrayList;
import java.util.List;

public class AbstractDTOMapBean<T> extends AbstractMapBean {
    private static final long serialVersionUID = -6237098895338779800L;

    private List<OrderBean> orderList;
    private List<String> pickedColumnList;

    public T addOrderBy(OrderBean order) {
        if (null == orderList) {
            this.orderList = new ArrayList<>();
            dataMap.put("orderBy", this.orderList);
        }

        this.orderList.add(order);

        return (T) this;
    }

    public List<OrderBean> getOrderBy() {
        return this.orderList;
    }
    
    public T addPickedColumn(String columnName) {
        if (null == pickedColumnList) {
            this.pickedColumnList = new ArrayList<>();
            dataMap.put("pickedColumnList", this.pickedColumnList);
        }

        this.pickedColumnList.add(columnName);

        return (T) this;
    }

    public List<String> getPickedColumnList() {
        return this.pickedColumnList;
    }
}
