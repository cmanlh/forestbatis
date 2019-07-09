package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;
import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.SelectSqlBuilder;
import com.lifeonwalden.forestbatis.constant.OrderEnum;
import com.lifeonwalden.forestbatis.example.DBConfig;
import com.lifeonwalden.forestbatis.example.bean.Book;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.bean.User_Book_Record;
import com.lifeonwalden.forestbatis.example.builder.BookBuilder;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.example.builder.User_Book_RecordBuilder;
import com.lifeonwalden.forestbatis.example.mapper.BookMapper;
import com.lifeonwalden.forestbatis.example.mapper.UserMapper;
import com.lifeonwalden.forestbatis.example.mapper.User_Book_RecordMapper;
import com.lifeonwalden.forestbatis.example.meta.BookMetaInfo;
import com.lifeonwalden.forestbatis.meta.*;
import com.lifeonwalden.forestbatis.parsing.PropertyParser;
import com.lifeonwalden.forestbatis.util.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserMapperTest {
    protected static final String CONNECTION_STRING = "jdbc:hsqldb:mem:testdb;shutdown=false";
    protected static final String USER_NAME = "SA";
    protected static final String PASSWORD = "";

    @Before
    public void setup() throws SQLException, IOException {
        importSql("/setup.sql");
    }

    @Test
    public void get_empty() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        Optional<User> _user = userMapper.get(new User().setId("Chandler"));
        Assert.assertTrue(!_user.isPresent());
    }

    @Test
    public void get_empty_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        Optional<User> _user = userMapper.getColumns(new User().setId("Chandler"), UserBuilder.Income);
        Assert.assertTrue(!_user.isPresent());
    }

    @Test
    public void get() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        Optional<User> _user = userMapper.get(new User().setId("Tom"));
        User user = _user.get();
        Assert.assertTrue("Not found Tom", _user.isPresent());
        Assert.assertTrue("Id : ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age : ".concat(String.valueOf(user.getAge())), 15 == user.getAge());
        Assert.assertTrue("Income : ".concat(String.valueOf(user.getIncome())), BigDecimal.valueOf(8888.88).compareTo(user.getIncome()) == 0);
        Assert.assertTrue("Birthday : ".concat(String.valueOf(user.getBirthday())), new Date("2000/12/12").getTime() == user.getBirthday().getTime());
        Assert.assertTrue("Sex : ".concat(String.valueOf(user.getSex())), 1 == user.getSex());
    }

    @Test
    public void get_with_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        Optional<User> _user = userMapper.getColumns(new User().setId("Tom"), UserBuilder.Income, UserBuilder.Id);
        User user = _user.get();
        Assert.assertTrue("Not found Tom", _user.isPresent());
        Assert.assertTrue("Id : ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertNull("Age is not null : ".concat(String.valueOf(user.getAge())), user.getAge());
        Assert.assertTrue("Income is : ".concat(String.valueOf(user.getIncome())), user.getIncome().compareTo(BigDecimal.valueOf(8888.88)) == 0);
        Assert.assertNull("Birthday is not null : ".concat(String.valueOf(user.getBirthday())), user.getBirthday());
        Assert.assertNull("Sex is not null : ".concat(String.valueOf(user.getSex())), user.getSex());
    }

    @Test
    public void get_without_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        Optional<User> _user = userMapper.getWithoutColumns(new User().setId("Tom"), UserBuilder.Income);
        User user = _user.get();
        Assert.assertTrue("Not found Tom", _user.isPresent());
        Assert.assertTrue("Id : ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age : ".concat(String.valueOf(user.getAge())), 15 == user.getAge());
        Assert.assertNull("Income is not null : ".concat(String.valueOf(user.getIncome())), user.getIncome());
        Assert.assertTrue("Birthday : ".concat(String.valueOf(user.getBirthday())), new Date("2000/12/12").getTime() == user.getBirthday().getTime());
        Assert.assertTrue("Sex : ".concat(String.valueOf(user.getSex())), 1 == user.getSex());
    }


    @Test
    public void update_without_id() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.update(new User().setIncome(BigDecimal.valueOf(1111.11)));
        List<User> userList = userMapper.select(new User().setIncome(BigDecimal.valueOf(1111.11)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 0);
    }

    @Test
    public void update() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.update(new User().setId("Tom").setIncome(newIncome));
        User user = userMapper.get(new User().setId("Tom")).get();
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertNull("Tom's age ".concat(String.valueOf(user.getAge())), user.getAge());
    }

    @Test
    public void update_without_null() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateWithoutNull(new User().setId("Tom").setIncome(newIncome));
        User user = userMapper.get(new User().setId("Tom")).get();
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertTrue("Tom's age ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
    }

    @Test
    public void update_with_special_column() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateColumns(new User().setId("Tom").setIncome(newIncome).setAge(32), UserBuilder.Income);
        User user = userMapper.get(new User().setId("Tom")).get();
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertTrue("Tom's age : ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertTrue("Birthday : ".concat(String.valueOf(user.getBirthday())), new Date("2000/12/12").getTime() == user.getBirthday().getTime());
    }

    @Test
    public void update_without_special_column() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateWithoutColumns(new User().setId("Tom").setIncome(newIncome).setAge(32), UserBuilder.Age);
        User user = userMapper.get(new User().setId("Tom")).get();
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertTrue("Tom's age : ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertNull("Tom's birthday ".concat(String.valueOf(user.getAge())), user.getBirthday());
    }

    @Test
    public void update_special_column_without_null() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateColumnsWithoutNull(new User().setId("Tom").setIncome(newIncome), UserBuilder.Age, UserBuilder.Income);
        User user = userMapper.get(new User().setId("Tom")).get();
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertTrue("Tom's age ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertTrue("Birthday : ".concat(String.valueOf(user.getBirthday())), new Date("2000/12/12").getTime() == user.getBirthday().getTime());
    }

    @Test
    public void update_without_null_and_special_column() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateWithoutColumnsNull(new User().setId("Tom").setIncome(newIncome).setSex(2), UserBuilder.Age, UserBuilder.Sex);
        User user = userMapper.get(new User().setId("Tom")).get();
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertTrue("Tom's age ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertTrue("Tom's sex ".concat(String.valueOf(user.getSex())), user.getSex() == 1);
        Assert.assertTrue("Birthday : ".concat(String.valueOf(user.getBirthday())), new Date("2000/12/12").getTime() == user.getBirthday().getTime());
    }

    @Test
    public void update_batch() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.update(Arrays.asList(new User().setId("Tom").setIncome(newIncome), new User().setId("Jerry").setIncome(newIncome)));
        List<User> userList = userMapper.select(new User().setIncome(newIncome));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void update_batch_with_special_column() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateColumns(Arrays.asList(new User().setId("Tom").setIncome(newIncome), new User().setId("Jerry").setIncome(newIncome)), UserBuilder.Age, UserBuilder.Income);
        List<User> userList = userMapper.select(new User().setIncome(newIncome), new Order(UserBuilder.Birthday, OrderEnum.DESC));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertNull("Tom's age ".concat(String.valueOf(user.getAge())), user.getAge());
        Assert.assertTrue("Tom's Birthday : ".concat(String.valueOf(user.getBirthday())), new Date("2000/12/12").getTime() == user.getBirthday().getTime());
    }

    @Test
    public void update_batch_without_special_column() {
        reset();
        BigDecimal newIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateWithoutColumns(Arrays.asList(new User().setId("Tom").setIncome(newIncome).setSex(2), new User().setId("Jerry").setIncome(newIncome).setSex(2)), UserBuilder.Age, UserBuilder.Sex);
        List<User> userList = userMapper.select(new User().setIncome(newIncome), new Order(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue("Tom's income : ".concat(String.valueOf(user.getIncome())), newIncome.compareTo(user.getIncome()) == 0);
        Assert.assertTrue("Tom's age ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertTrue("Tom's sex ".concat(String.valueOf(user.getSex())), user.getSex() == 1);
        Assert.assertNull("Tom's birthday ".concat(String.valueOf(user.getAge())), user.getBirthday());
    }

    @Test
    public void delete() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.delete(new User().setId("Tom"));
        Optional<User> _user = userMapper.get(new User().setId("Tom"));
        Assert.assertTrue(!_user.isPresent());
    }

    @Test
    public void delete_batch() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.delete(Arrays.asList(new User().setId("Tom"), new User().setId("Lucy")));
        List<User> userList = userMapper.select(new User());
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void selectAll() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectAll();
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
    }

    @Test
    public void selectAll_orderBy() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectAll(new Order(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Lucy's name : ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        });
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
    }

    @Test
    public void selectAll_stream_orderby() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        }, new Order(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Lucy's name : ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        }, 2);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
    }

    @Test
    public void selectAll_stream_orderby_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        }, 2, new Order(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Lucy's name : ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectAll(UserBuilder.Age, UserBuilder.Id);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_special_column_orderBy() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectAll(Arrays.asList(UserBuilder.Age, UserBuilder.Id), Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_special_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        }, UserBuilder.Id, UserBuilder.Age);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_special_columns_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        }, 2, UserBuilder.Id, UserBuilder.Age);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_special_columns_orderBy() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        }, Arrays.asList(UserBuilder.Id, UserBuilder.Age), Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_special_columns_orderBy_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAll(user -> {
            userList.add(user);
            return true;
        }, 2, Arrays.asList(UserBuilder.Id, UserBuilder.Age), Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_without_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectAllWithoutColumns(UserBuilder.Income);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_orderBy_without_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectAllWithoutColumns(Arrays.asList(UserBuilder.Income), Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAllWithoutColumns(user -> {
            userList.add(user);
            return true;
        }, UserBuilder.Income);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_fetchSize_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAllWithoutColumns(user -> {
            userList.add(user);
            return true;
        }, 2, UserBuilder.Income);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_orderBy_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAllWithoutColumns(user -> {
            userList.add(user);
            return true;
        }, Arrays.asList(UserBuilder.Income), Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void selectAll_stream_orderBy_fetchSize_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectAllWithoutColumns(user -> {
            userList.add(user);
            return true;
        }, 2, Arrays.asList(UserBuilder.Income), Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User());
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
    }

    @Test
    public void select_with_bt_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setAge(18), UserBuilder.SELECT.overrideQuery(new Bt(UserBuilder.Age)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_bt_date() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setBirthday(new Date("1990/12/12")), UserBuilder.SELECT.overrideQuery(new Bt(UserBuilder.Birthday)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_bte_start() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book().setPublishTimeStart(new Date("1980/12/12")));
        Assert.assertTrue("User size : ".concat(String.valueOf(bookList.size())), bookList.size() == 3);
    }

    @Test
    public void select_with_bte_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setAge(18), UserBuilder.SELECT.overrideQuery(new Bte(UserBuilder.Age)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }

    @Test
    public void select_with_bte_date() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setBirthday(new Date("1990/12/12")), UserBuilder.SELECT.overrideQuery(new Bte(UserBuilder.Birthday)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_compound() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> userList = bookMapper.select(new Book().setPublishTime(new Date("1980/12/12")).setName("Live").setPublisher("Amazon"),
                BookBuilder.SELECT.overrideQuery(new Bte(BookBuilder.PublishTime).and(new CompoundQuery(new Eq(BookBuilder.Name).or(new Eq(BookBuilder.Publisher))))));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_eq_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setSex(1));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_eq_varchar() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book().setPublisher("Free"));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 2);
    }

    @Test
    public void select_with_eq_datetime() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book().setPublishTime(new Date("1978/10/12 12:12:12")));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 2);
    }

    @Test
    public void select_with_eq_date() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setBirthday(new Date("2000/12/12")));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_eq_decimal() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setIncome(BigDecimal.valueOf(8888.88)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_exists() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User(), UserBuilder.SELECT.overrideQuery(
                new Exists(new TempTable("a", new SubSelect().setTableNode(new TableNode(User_Book_RecordBuilder.TABLE)).setQuery(new Eq(User_Book_RecordBuilder.USER_ID, UserBuilder.Id))))));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }

    @Test
    public void select_with_exists_eq() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setId("Tom"), UserBuilder.SELECT.overrideQuery(
                new Exists(new TempTable("a", new SubSelect().setTableNode(new TableNode(User_Book_RecordBuilder.TABLE)).setQuery(new Eq(User_Book_RecordBuilder.USER_ID, UserBuilder.Id))))
                        .and(new Eq(UserBuilder.Id))));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_in_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        String ageList = "ageList";
        User param = new User();
        param.put(ageList, Arrays.asList(15, 25));
        List<User> userList = userMapper.select(param, UserBuilder.SELECT.overrideQuery(new In(UserBuilder.Age, UserBuilder.age.overrideName(ageList))));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_in_varchar() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        String publisherList = "publisherList";
        Book param = new Book();
        param.put(publisherList, Arrays.asList("Amazon", "Free"));
        List<Book> bookList = bookMapper.select(param, BookBuilder.SELECT.overrideQuery(new In(BookBuilder.Publisher, BookBuilder.publisher.overrideName(publisherList))));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 4);
    }

    @Test
    public void select_with_in_date() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        String publishTimeList = "publishTimeList";
        Book param = new Book();
        param.put(publishTimeList, Arrays.asList(new Date("2019/10/12 12:12:12"), new Date("2013/10/12 12:12:12")));
        List<Book> bookList = bookMapper.select(param, BookBuilder.SELECT.overrideQuery(new In(BookBuilder.PublishTime, BookBuilder.publishTime.overrideName(publishTimeList))));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 1);
    }

    @Test
    public void select_with_is_not_null() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User(), UserBuilder.SELECT.overrideQuery(new IsNotNull(UserBuilder.Birthday)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }

    @Test
    public void select_with_is_null() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User(), UserBuilder.SELECT.overrideQuery(new IsNull(UserBuilder.Birthday)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_join() {
        reset();
        User_Book_RecordMapper recordMapper = new User_Book_RecordMapper(DBConfig.config, (Null) -> getConnection());
        SelectBuilder<User_Book_Record> joinSelect = new SelectBuilder<>(new TableNode(BookBuilder.TABLE)
                .rightJoin(new JoinNode(User_Book_RecordBuilder.TABLE, new JoinCondition(User_Book_RecordBuilder.Book_id, BookBuilder.Id))),
                Arrays.asList(User_Book_RecordBuilder.USER_ID, BookBuilder.Name, User_Book_RecordBuilder.BorrowDate, User_Book_RecordBuilder.ReturnDate));
        List<User_Book_Record> recordList = recordMapper.select(new User_Book_Record(), joinSelect);
        Assert.assertTrue("Record size : ".concat(String.valueOf(recordList.size())), recordList.size() == 4);
        User_Book_Record record = recordList.get(0);
        Assert.assertTrue("User Name : ".concat(record.getUSER_ID()), "Tom".equals(record.getUSER_ID()));
        Assert.assertTrue("Book Name : ".concat((String) record.get(BookMetaInfo.name)), "Live".equals(record.get(BookMetaInfo.name)));
        Assert.assertTrue("Borrow Date : ".concat(DateUtil.formatDate(record.getBorrowDate(), DateUtil.DATE)), "2019-04-12".equals(DateUtil.formatDate(record.getBorrowDate(), DateUtil.DATE)));
        Assert.assertTrue("Return Date : ".concat(DateUtil.formatDate(record.getReturnDate(), DateUtil.DATE)), "2019-04-28".equals(DateUtil.formatDate(record.getReturnDate(), DateUtil.DATE)));
    }

    @Test
    public void select_with_like() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book().setName("%o%"), BookBuilder.SELECT.overrideQuery(new Like(BookBuilder.Name)));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 2);
    }

    @Test
    public void select_with_lt() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setIncome(BigDecimal.valueOf(9999.99)), UserBuilder.SELECT.overrideQuery(new Lt(UserBuilder.Income)));

        Assert.assertTrue("User Size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_lte() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setIncome(BigDecimal.valueOf(9999.99)), UserBuilder.SELECT.overrideQuery(new Lte(UserBuilder.Income)));

        Assert.assertTrue("User Size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_neq_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setSex(1), UserBuilder.SELECT.overrideQuery(new Neq(UserBuilder.Sex)));

        Assert.assertTrue("User Size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_neq_varchar() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setId("Tom"), UserBuilder.SELECT.overrideQuery(new Neq(UserBuilder.Id)));

        Assert.assertTrue("User Size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }

    @Test
    public void select_with_neq_date() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setBirthday(new Date("1990/12/12")), UserBuilder.SELECT.overrideQuery(new Neq(UserBuilder.Birthday)));

        Assert.assertTrue("User Size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_neq_decimal() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setIncome(BigDecimal.valueOf(9999.99)), UserBuilder.SELECT.overrideQuery(new Neq(UserBuilder.Income)));

        Assert.assertTrue("User Size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_not_exists() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User(), UserBuilder.SELECT.overrideQuery(
                new NotExists(new TempTable("a", new SubSelect().setTableNode(new TableNode(User_Book_RecordBuilder.TABLE)).setQuery(new Eq(User_Book_RecordBuilder.USER_ID, UserBuilder.Id))))));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_not_in_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        String ageList = "ageList";
        User param = new User();
        param.put(ageList, Arrays.asList(15, 25));
        List<User> userList = userMapper.select(param, UserBuilder.SELECT.overrideQuery(new NotIn(UserBuilder.Age, UserBuilder.age.overrideName(ageList))));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_not_in_varchar() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        String publisherList = "publisherList";
        Book param = new Book();
        param.put(publisherList, Arrays.asList("Amazon", "Free"));
        List<Book> bookList = bookMapper.select(param, BookBuilder.SELECT.overrideQuery(new NotIn(BookBuilder.Publisher, BookBuilder.publisher.overrideName(publisherList))));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 1);
    }

    @Test
    public void select_with_not_in_date() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        String publishTimeList = "publishTimeList";
        Book param = new Book();
        param.put(publishTimeList, Arrays.asList(new Date("2019/10/12 12:12:12"), new Date("2013/10/12 12:12:12")));
        List<Book> bookList = bookMapper.select(param, BookBuilder.SELECT.overrideQuery(new NotIn(BookBuilder.PublishTime, BookBuilder.publishTime.overrideName(publishTimeList))));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 4);
    }

    @Test
    public void select_with_not_like() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book().setName("%o%"), BookBuilder.SELECT.overrideQuery(new NotLike(BookBuilder.Name)));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 3);
    }

    @Test
    public void select_with_order() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book(), BookBuilder.SELECT.overrideOrder(Arrays.asList(new Order(BookBuilder.PublishTime))));
        Assert.assertTrue("The last is : ".concat(bookList.get(4).getName()), "Mao".equals(bookList.get(4).getName()));
    }

    @Test
    public void select_with_order_multi() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book(), BookBuilder.SELECT.overrideOrder(Arrays.asList(new Order(BookBuilder.PublishTime), new Order(BookBuilder.Id))));
        Assert.assertTrue("The first is : ".concat(bookList.get(0).getName()), "中国".equals(bookList.get(0).getName()));
    }

    @Test
    public void select_with_order_desc() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book(), BookBuilder.SELECT.overrideOrder(Arrays.asList(new Order(BookBuilder.PublishTime, OrderEnum.DESC))));
        Assert.assertTrue("The first is : ".concat(bookList.get(0).getName()), "Mao".equals(bookList.get(0).getName()));
    }

    @Test
    public void select_orderBy() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, (Null) -> getConnection());
        List<Book> bookList = bookMapper.select(new Book(), Order.asc(BookBuilder.PublishTime), Order.asc(BookBuilder.Id));
        Assert.assertTrue("The first is : ".concat(bookList.get(0).getName()), "中国".equals(bookList.get(0).getName()));
    }

    @Test
    public void select_stream() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), user -> {
            userList.add(user);
            return true;
        });
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
    }

    @Test
    public void select_stream_with_break() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), UserBuilder.SELECT.overrideOrder(Arrays.asList(new Order(UserBuilder.Age))),
                user -> {
                    if (user.getAge() > 25) {
                        return false;
                    }
                    if (null != user.getIncome()) {
                        userList.add(user);
                    }
                    return true;
                });
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_stream_with_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), user -> {
            userList.add(user);
            return true;
        }, 2);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
    }

    @Test
    public void select_stream_with_break_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), UserBuilder.SELECT.overrideOrder(Arrays.asList(new Order(UserBuilder.Age))),
                user -> {
                    if (user.getAge() > 25) {
                        return false;
                    }
                    if (null != user.getIncome()) {
                        userList.add(user);
                    }
                    return true;
                }, 2);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_stream_orderBy() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), user -> {
            userList.add(user);
            return true;
        }, Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_fetchSize_orderBy() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), user -> {
            userList.add(user);
            return true;
        }, 2, Order.asc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Lucy".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 18);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setSex(1), UserBuilder.Age, UserBuilder.Id);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_special_column_orderBy() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setSex(1), Arrays.asList(UserBuilder.Age, UserBuilder.Id), Order.desc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_special_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, UserBuilder.Id, UserBuilder.Age);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_special_columns_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, 2, UserBuilder.Id, UserBuilder.Age);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_special_columns_orderBy() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, Arrays.asList(UserBuilder.Id, UserBuilder.Age), Order.desc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_special_columns_orderBy_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, 2, Arrays.asList(UserBuilder.Id, UserBuilder.Age), Order.desc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_without_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectWithoutColumns(new User().setSex(1), UserBuilder.Income);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_orderBy_without_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.selectWithoutColumns(new User().setSex(1), Arrays.asList(UserBuilder.Income), Order.desc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectWithoutColumns(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, UserBuilder.Income);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_fetchSize_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectWithoutColumns(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, 2, UserBuilder.Income);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(0);
        Assert.assertTrue(user.getId().length() > 0);
        Assert.assertTrue(user.getAge() > 0);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_orderBy_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectWithoutColumns(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, Arrays.asList(UserBuilder.Income), Order.desc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_stream_orderBy_fetchSize_without_columns() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.selectWithoutColumns(new User().setSex(1), user -> {
            userList.add(user);
            return true;
        }, 2, Arrays.asList(UserBuilder.Income), Order.desc(UserBuilder.Age));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
        User user = userList.get(1);
        Assert.assertTrue("Id is ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("Age is ".concat(String.valueOf(user.getAge())), user.getAge() == 15);
        Assert.assertNull(user.getIncome());
    }

    @Test
    public void select_recordHandler() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User(), (rs, columnList) -> {
            User user = new User();
            try {
                for (ColumnInfo columnInfo : columnList) {
                    int index = columnInfo.getIndex();
                    switch (columnInfo.getPropertyName()) {
                        case "age":
                            int age = rs.getInt(index);
                            if (!rs.wasNull()) {
                                user.setAge(age);
                            }
                            break;
                        case "income":
                            user.setIncome(rs.getBigDecimal(index));
                            break;
                        case "id":
                            user.setId(rs.getString(index));
                            break;
                        case "birthday":
                            user.setBirthday(rs.getDate(index));
                            break;
                        case "sex":
                            int sex = rs.getInt(index);
                            if (!rs.wasNull()) {
                                user.setSex(sex);
                            }
                            break;
                        default:
                            break;

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return user;

        });
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 4);
    }

    @Test
    public void select_recordHandler_stream() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), (rs, columnList) -> {
            User user = new User();
            try {
                for (ColumnInfo columnInfo : columnList) {
                    int index = columnInfo.getIndex();
                    switch (columnInfo.getPropertyName()) {
                        case "age":
                            int age = rs.getInt(index);
                            if (!rs.wasNull()) {
                                user.setAge(age);
                            }
                            break;
                        case "income":
                            user.setIncome(rs.getBigDecimal(index));
                            break;
                        case "id":
                            user.setId(rs.getString(index));
                            break;
                        case "birthday":
                            user.setBirthday(rs.getDate(index));
                            break;
                        case "sex":
                            int sex = rs.getInt(index);
                            if (!rs.wasNull()) {
                                user.setSex(sex);
                            }
                            break;
                        default:
                            break;

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return user;

        }, user -> {
            if (null != user.getIncome()) {
                userList.add(user);
            }
            return true;
        });
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }

    @Test
    public void select_recordHandler_stream_fetchSize() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), (rs, columnList) -> {
            User user = new User();
            try {
                for (ColumnInfo columnInfo : columnList) {
                    int index = columnInfo.getIndex();
                    switch (columnInfo.getPropertyName()) {
                        case "age":
                            int age = rs.getInt(index);
                            if (!rs.wasNull()) {
                                user.setAge(age);
                            }
                            break;
                        case "income":
                            user.setIncome(rs.getBigDecimal(index));
                            break;
                        case "id":
                            user.setId(rs.getString(index));
                            break;
                        case "birthday":
                            user.setBirthday(rs.getDate(index));
                            break;
                        case "sex":
                            int sex = rs.getInt(index);
                            if (!rs.wasNull()) {
                                user.setSex(sex);
                            }
                            break;
                        default:
                            break;

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return user;

        }, user -> {
            if (null != user.getIncome()) {
                userList.add(user);
            }
            return true;
        }, 2);
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }


    @Test
    public void select_recordHandler_stream_with_break() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = new ArrayList<>();
        userMapper.select(new User(), UserBuilder.SELECT.overrideOrder(Arrays.asList(new Order(UserBuilder.Age))), (rs, columnList) -> {
                    User user = new User();
                    try {
                        for (ColumnInfo columnInfo : columnList) {
                            int index = columnInfo.getIndex();
                            switch (columnInfo.getPropertyName()) {
                                case "age":
                                    int age = rs.getInt(index);
                                    if (!rs.wasNull()) {
                                        user.setAge(age);
                                    }
                                    break;
                                case "income":
                                    user.setIncome(rs.getBigDecimal(index));
                                    break;
                                case "id":
                                    user.setId(rs.getString(index));
                                    break;
                                case "birthday":
                                    user.setBirthday(rs.getDate(index));
                                    break;
                                case "sex":
                                    int sex = rs.getInt(index);
                                    if (!rs.wasNull()) {
                                        user.setSex(sex);
                                    }
                                    break;
                                default:
                                    break;

                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return user;

                },
                user -> {
                    if (user.getAge() > 25) {
                        return false;
                    }
                    if (null != user.getIncome()) {
                        userList.add(user);
                    }
                    return true;
                });
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void updateWithQuery() {
        reset();
        BigDecimal womanIncome = BigDecimal.valueOf(11111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateWithQuery(new User().setIncome(womanIncome).setSex(2),
                UserBuilder.UPDATE_QUERY.overrideQuery(new Eq(UserBuilder.Sex)).overrideUpdateColumn(Arrays.asList(UserBuilder.Income)));
        List<User> userList = userMapper.select(new User());
        userList.forEach(user -> {
                    switch (user.getSex()) {
                        case 1:
                            Assert.assertTrue(user.getId().concat("'s income : ").concat(user.getIncome().toString()), womanIncome.compareTo(user.getIncome()) != 0);
                            break;
                        default:
                            Assert.assertTrue(user.getId().concat("'s income : ").concat(user.getIncome().toString()), womanIncome.compareTo(user.getIncome()) == 0);
                            break;

                    }
                }
        );
    }

    @Test
    public void updateWithQuery_batch() {
        reset();
        BigDecimal womanIncome = BigDecimal.valueOf(11111.11);
        BigDecimal manIncome = BigDecimal.valueOf(1111.11);
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.updateWithQuery(Arrays.asList(new User().setIncome(womanIncome).setSex(2), new User().setSex(1).setIncome(manIncome)),
                UserBuilder.UPDATE_QUERY.overrideQuery(new Eq(UserBuilder.Sex)).overrideUpdateColumn(Arrays.asList(UserBuilder.Income)));
        List<User> userList = userMapper.select(new User());
        userList.forEach(user -> {
                    switch (user.getSex()) {
                        case 1:
                            Assert.assertTrue(user.getId().concat("'s income : ").concat(user.getIncome().toString()), manIncome.compareTo(user.getIncome()) == 0);
                            break;
                        default:
                            Assert.assertTrue(user.getId().concat("'s income : ").concat(user.getIncome().toString()), womanIncome.compareTo(user.getIncome()) == 0);
                            break;

                    }
                }
        );
    }

    @Test
    public void deleteWithQuery() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.deleteWithQuery(new User().setAge(15));
        List<User> userList = userMapper.selectAll();
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }

    @Test
    public void deleteWithQuery_builder() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.deleteWithQuery(new User().setAge(15), UserBuilder.DELETE.overrideQuery(new Bt(UserBuilder.Age)));
        List<User> userList = userMapper.select(new User());
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void deleteWithQuery_batch() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.delete(Arrays.asList(new User().setId("Tom"), new User().setId("Lucy")));
        List<User> userList = userMapper.select(new User());
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void insert() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.insert(new User().setId("Allan"));
        Optional<User> _user = userMapper.get(new User().setId("Allan"));
        Assert.assertTrue(_user.isPresent());
        User user = _user.get();
        Assert.assertNull(user.getAge());
        Assert.assertNull(user.getSex());
    }

    @Test
    public void insert_without_null() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.insertWithoutNull(new User().setId("Allan"));
        Optional<User> _user = userMapper.get(new User().setId("Allan"));
        Assert.assertTrue(_user.isPresent());
        User user = _user.get();
        Assert.assertNull(user.getAge());
        Assert.assertNotNull(user.getSex());
    }

    @Test
    public void insert_builder() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.insert(new User().setId("Allan"), UserBuilder.INSERT.excludeInsertColumn(Arrays.asList(UserBuilder.Sex)));
        Optional<User> _user = userMapper.get(new User().setId("Allan"));
        Assert.assertTrue(_user.isPresent());
        User user = _user.get();
        Assert.assertNull(user.getAge());
        Assert.assertNotNull(user.getSex());
    }

    @Test
    public void insert_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.insert(new User().setId("Allan").setIncome(BigDecimal.valueOf(112121)), UserBuilder.INSERT.overrideInsertColumn(Arrays.asList(UserBuilder.Id, UserBuilder.Income, UserBuilder.Age)));
        Optional<User> _user = userMapper.get(new User().setId("Allan"));
        Assert.assertTrue(_user.isPresent());
        User user = _user.get();
        Assert.assertNull(user.getAge());
        Assert.assertNotNull(user.getIncome());
        Assert.assertNotNull(user.getSex());
    }

    @Test
    public void insert_batch() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.insert(Arrays.asList(new User().setId("Allan"), new User().setId("Phoebe")));
        Optional<User> _user = userMapper.get(new User().setId("Allan"));
        Assert.assertTrue(_user.isPresent());
        User user = _user.get();
        Assert.assertNull(user.getAge());
        Assert.assertNull(user.getSex());
        Assert.assertTrue(userMapper.selectAll().size() == 6);
    }

    @Test
    public void insert_batch_special_column() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.insert(Arrays.asList(new User().setId("Allan").setIncome(BigDecimal.valueOf(112121)), new User().setId("Phoebe").setIncome(BigDecimal.valueOf(112121))), UserBuilder.INSERT.overrideInsertColumn(Arrays.asList(UserBuilder.Id, UserBuilder.Income, UserBuilder.Age)));
        Optional<User> _user = userMapper.get(new User().setId("Allan"));
        Assert.assertTrue(_user.isPresent());
        User user = _user.get();
        Assert.assertNull(user.getAge());
        Assert.assertNotNull(user.getIncome());
        Assert.assertNotNull(user.getSex());
        Assert.assertTrue(userMapper.selectAll().size() == 6);
    }

    @Test
    public void sql() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        List<User> userList = userMapper.select(new User().setSex(2), new SelectSqlBuilder<User>() {
            @Override
            public StatementInfo build(User value,Config config) {
                return PropertyParser.parse("select \"id\", \"sex\", 100 as \"score\" from \"User\" where \"sex\" = #{sex, JdbcType=INTEGER}");
            }

            @Override
            public StatementInfo build(Config config) {
                return build(null);
            }

            @Override
            public boolean isRuntimeChangeable() {
                return false;
            }
        });

        User user = userList.get(0);
        Assert.assertNotNull(user.getId());
        Assert.assertNotNull(user.getSex());
        Assert.assertTrue(user.getSex() == 2);
        Assert.assertTrue((int) user.get("score") == 100);
    }

    @Test
    public void truncate() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, (Null) -> getConnection());
        userMapper.truncate();
        Assert.assertTrue(userMapper.selectAll().size() == 0);
    }

    private void reset() {
        try {
            importSql("/init.sql");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            return DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void importSql(String sqlPath) throws SQLException, IOException {
        Connection connection = getConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(sqlPath)))) {

            Statement statement = connection.createStatement();
            do {
                String sql = reader.readLine();
                if (null == sql) {
                    break;
                }
                if (sql.trim().isEmpty()) {
                    continue;
                }
                statement.executeUpdate(sql);
            } while (true);

            statement.close();
            connection.close();
        }
    }

    @After
    public void clear() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbcDriver");

        Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
        Statement stmt = connection.createStatement();
        stmt.execute("DROP SCHEMA PUBLIC CASCADE");
        connection.close();
    }
}
