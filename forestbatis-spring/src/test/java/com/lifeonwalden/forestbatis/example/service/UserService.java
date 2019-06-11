package com.lifeonwalden.forestbatis.example.service;

import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.bean.UserVO;
import com.lifeonwalden.forestbatis.example.bean.User_Book_Record;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> get(UserVO param);

    List<User> query(UserVO param);

    List<User_Book_Record> queryUserBookRecord();

    void update(UserVO param);

    void delete(UserVO param);

    void insert(UserVO param);

    void exceptionInsertWithoutTransaction(UserVO param);

    void exceptionInsertWithTransaction(UserVO param);

    void exceptionUpdateWithoutTransaction(UserVO param);

    void exceptionUpdateWithTransaction(UserVO param);

    void exceptionDeleteWithoutTransaction(UserVO param);

    void exceptionDeleteWithTransaction(UserVO param);

    void batchInsertWithoutTransaction(List<User> userList);

    void batchInsertWithTransaction(List<User> userList);
}
