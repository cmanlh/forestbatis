package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.example.DBConfig;
import com.lifeonwalden.forestbatis.example.bean.Book;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.example.mapper.BookMapper;
import com.lifeonwalden.forestbatis.example.mapper.UserMapper;
import com.lifeonwalden.forestbatis.meta.*;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserMapperTest {
    protected static final String CONNECTION_STRING = "jdbc:hsqldb:mem:testdb;shutdown=false";
    protected static final String USER_NAME = "SA";
    protected static final String PASSWORD = "";

    @Before
    public void setup() throws SQLException, IOException, ClassNotFoundException {
        importSql("/setup.sql");
    }

    @Test
    public void get() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
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
    public void select() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        List<User> userList = userMapper.select(new User());
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 3);
    }

    @Test
    public void select_with_eq_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        List<User> userList = userMapper.select(new User().setSex(1));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_eq_varchar() {
        reset();
        BookMapper bookMapper = new BookMapper(DBConfig.config, getConnection());
        List<Book> bookList = bookMapper.select(new Book().setPublisher("Free"));
        Assert.assertTrue("Book size : ".concat(String.valueOf(bookList.size())), bookList.size() == 2);
    }

    @Test
    public void select_with_eq_date() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        List<User> userList = userMapper.select(new User().setBirthday(new Date("2000/12/12")));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_bt_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        List<User> userList = userMapper.select(new User().setAge(18), UserBuilder.SELECT.overrideQuery(new Bt(UserBuilder.Age)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_bte_int() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        List<User> userList = userMapper.select(new User().setAge(18), UserBuilder.SELECT.overrideQuery(new Bte(UserBuilder.Age)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_is_null() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        List<User> userList = userMapper.select(new User(), UserBuilder.SELECT.overrideQuery(new IsNull(UserBuilder.Birthday)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 1);
    }

    @Test
    public void select_with_is_not_null() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        List<User> userList = userMapper.select(new User(), UserBuilder.SELECT.overrideQuery(new IsNotNull(UserBuilder.Birthday)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void select_with_in() {
        reset();
        UserMapper userMapper = new UserMapper(DBConfig.config, getConnection());
        String ageList = "ageList";
        User param = new User();
        param.put(ageList, Arrays.asList(15,25));
        List<User> userList = userMapper.select(param, UserBuilder.SELECT.overrideQuery(new In(UserBuilder.Age, UserBuilder.age)));
        Assert.assertTrue("User size : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    private void reset() {
        try {
            importSql("/init.sql");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    private void importSql(String sqlPath) throws ClassNotFoundException, SQLException, IOException {
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
