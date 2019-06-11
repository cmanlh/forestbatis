package com.lifeonwalden.forestbatis.example.service.impl;

import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.bean.UserVO;
import com.lifeonwalden.forestbatis.example.bean.User_Book_Record;
import com.lifeonwalden.forestbatis.example.mapper.UserMapper;
import com.lifeonwalden.forestbatis.example.mapper.User_Book_RecordMapper;
import com.lifeonwalden.forestbatis.example.service.UserService;
import com.lifeonwalden.forestbatis.spring.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MapperFactory mapperFactory;

    @Override
    public Optional<User> get(UserVO param) {
        return mapperFactory.getMapper(UserMapper.class).get(param.copyTo(User.class));
    }

    @Override
    public List<User> query(UserVO param) {
        return mapperFactory.getMapper(UserMapper.class).select(param.copyTo(User.class));
    }

    @Override
    public List<User_Book_Record> queryUserBookRecord() {
        return mapperFactory.getMapper(User_Book_RecordMapper.class).selectAll();
    }

    @Override
    public void update(UserVO param) {
        mapperFactory.getMapper(UserMapper.class).update(param.copyTo(User.class));
    }

    @Override
    public void delete(UserVO param) {
        mapperFactory.getMapper(UserMapper.class).delete(param.copyTo(User.class));
    }

    @Override
    public void insert(UserVO param) {
        mapperFactory.getMapper(UserMapper.class).insert(param.copyTo(User.class));
    }

    private void exceptionInsert(UserVO param) {
        mapperFactory.getMapper(UserMapper.class).insert(param.copyTo(User.class));
        throw new RuntimeException("Transaction Test Exception");
    }

    @Override
    public void exceptionInsertWithoutTransaction(UserVO param) {
        exceptionInsert(param);
        mapperFactory.getMapper(User_Book_RecordMapper.class)
                .insert(new User_Book_Record().setBook_id("1").setUSER_ID(param.getId()).setBorrowDate(new Date()));
    }

    @Override
    @Transactional
    public void exceptionInsertWithTransaction(UserVO param) {
        exceptionInsertWithoutTransaction(param);
    }

    private void exceptionUpdate(UserVO param) {
        mapperFactory.getMapper(UserMapper.class).update(param.copyTo(User.class));
        throw new RuntimeException("Transaction Test Exception");
    }

    @Override
    public void exceptionUpdateWithoutTransaction(UserVO param) {
        exceptionUpdate(param);
        mapperFactory.getMapper(User_Book_RecordMapper.class)
                .insert(new User_Book_Record().setBook_id("1").setUSER_ID(param.getId()).setBorrowDate(new Date()));
    }

    @Override
    @Transactional
    public void exceptionUpdateWithTransaction(UserVO param) {
        exceptionUpdateWithoutTransaction(param);
    }

    private void exceptionDelete(UserVO param) {
        mapperFactory.getMapper(UserMapper.class).delete(param.copyTo(User.class));
        throw new RuntimeException("Transaction Test Exception");
    }

    @Override
    public void exceptionDeleteWithoutTransaction(UserVO param) {
        exceptionDelete(param);
        mapperFactory.getMapper(User_Book_RecordMapper.class)
                .deleteWithQuery(new User_Book_Record().setUSER_ID(param.getId()));
    }

    @Override
    @Transactional
    public void exceptionDeleteWithTransaction(UserVO param) {
        exceptionDeleteWithoutTransaction(param);
    }

    @Override
    public void batchInsertWithoutTransaction(List<User> userList) {
        mapperFactory.getMapper(UserMapper.class).insert(userList);
    }

    @Override
    @Transactional
    public void batchInsertWithTransaction(List<User> userList) {
        batchInsertWithoutTransaction(userList);
    }
}
