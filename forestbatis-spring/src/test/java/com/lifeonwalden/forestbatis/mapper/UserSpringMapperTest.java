package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.bean.UserVO;
import com.lifeonwalden.forestbatis.example.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:app.xml")
public class UserSpringMapperTest extends AbstractJUnit4SpringContextTests {
    Logger logger = LoggerFactory.getLogger(UserSpringMapperTest.class);
    protected static final String CONNECTION_STRING = "jdbc:hsqldb:mem:testdb;shutdown=false";
    protected static final String USER_NAME = "SA";
    protected static final String PASSWORD = "";

    @Autowired
    private UserService userService;

    @Before
    public void setup() throws SQLException, IOException {
        importSql("/setup.sql");
    }

    @Test
    public void get() {
        reset();
        User user = userService.get(new UserVO().setId("Tom")).get();
        Assert.assertTrue("User name is : ".concat(user.getId()), "Tom".equals(user.getId()));
    }

    @Test
    public void query() {
        reset();
        List<User> userList = userService.query(new UserVO().setSex(2));
        Assert.assertTrue("User size is : ".concat(String.valueOf(userList.size())), userList.size() == 2);
    }

    @Test
    public void delete() {
        reset();
        userService.delete(new UserVO().setId("Tom"));
        Optional<User> user = userService.get(new UserVO().setId("Tom"));
        Assert.assertFalse(user.isPresent());
    }

    @Test
    public void update() {
        reset();
        userService.update(new UserVO().setId("Tom").setAge(35));
        User user = userService.get(new UserVO().setId("Tom")).get();
        Assert.assertTrue("User name is : ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("User age is : ".concat(String.valueOf(user.getAge())), 35 == user.getAge());
    }

    @Test
    public void insert() {
        reset();
        userService.insert(new UserVO().setId("Chandler").setAge(35));
        User user = userService.get(new UserVO().setId("Chandler")).get();
        Assert.assertTrue("User name is : ".concat(user.getId()), "Chandler".equals(user.getId()));
        Assert.assertTrue("User age is : ".concat(String.valueOf(user.getAge())), 35 == user.getAge());
    }

    @Test
    public void exceptionInsertWithoutTransaction() {
        reset();
        try {
            userService.exceptionInsertWithoutTransaction(new UserVO().setId("Chandler").setAge(35));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        User user = userService.get(new UserVO().setId("Chandler")).get();
        Assert.assertTrue("User name is : ".concat(user.getId()), "Chandler".equals(user.getId()));
        Assert.assertTrue("User age is : ".concat(String.valueOf(user.getAge())), 35 == user.getAge());

        Assert.assertTrue(userService.queryUserBookRecord().size() == 4);
    }

    @Test
    public void exceptionInsertWithTransaction() {
        reset();
        try {
            userService.exceptionInsertWithTransaction(new UserVO().setId("Chandler").setAge(35));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        Optional<User> user = userService.get(new UserVO().setId("Chandler"));
        Assert.assertFalse(user.isPresent());
        Assert.assertTrue(userService.queryUserBookRecord().size() == 4);
    }

    @Test
    public void exceptionUpdateWithoutTransaction() {
        reset();
        try {
            userService.exceptionUpdateWithoutTransaction(new UserVO().setId("Tom").setAge(35));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        User user = userService.get(new UserVO().setId("Tom")).get();
        Assert.assertTrue("User name is : ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("User age is : ".concat(String.valueOf(user.getAge())), 35 == user.getAge());

        Assert.assertTrue(userService.queryUserBookRecord().size() == 4);
    }

    @Test
    public void exceptionUpdateWithTransaction() {
        reset();
        try {
            userService.exceptionUpdateWithTransaction(new UserVO().setId("Tom").setAge(35));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        User user = userService.get(new UserVO().setId("Tom")).get();
        Assert.assertTrue("User name is : ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("User age is : ".concat(String.valueOf(user.getAge())), 15 == user.getAge());
        Assert.assertTrue(userService.queryUserBookRecord().size() == 4);
    }

    @Test
    public void exceptionDeleteWithoutTransaction() {
        reset();
        try {
            userService.exceptionDeleteWithoutTransaction(new UserVO().setId("Tom").setAge(35));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        Optional<User> user = userService.get(new UserVO().setId("Tom"));
        Assert.assertFalse(user.isPresent());

        Assert.assertTrue(userService.queryUserBookRecord().size() == 4);
    }

    @Test
    public void exceptionDeleteWithTransaction() {
        reset();
        try {
            userService.exceptionDeleteWithTransaction(new UserVO().setId("Tom").setAge(35));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        User user = userService.get(new UserVO().setId("Tom")).get();
        Assert.assertTrue("User name is : ".concat(user.getId()), "Tom".equals(user.getId()));
        Assert.assertTrue("User age is : ".concat(String.valueOf(user.getAge())), 15 == user.getAge());
        Assert.assertTrue(userService.queryUserBookRecord().size() == 4);
    }

    @Test
    public void batchInsertWithoutTransaction() {
        reset();
        try {
            userService.batchInsertWithoutTransaction(Arrays.asList(new User().setId("Chandler").setAge(35), new User().setId("Jack").setAge(35), new User().setAge(35), new User().setId("Phoebe").setAge(35)));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        Assert.assertTrue(userService.get(new UserVO().setId("Chandler")).isPresent());
        Assert.assertTrue(userService.get(new UserVO().setId("Jack")).isPresent());
        Assert.assertFalse(userService.get(new UserVO().setId("Phoebe")).isPresent());
    }

    @Test
    public void batchInsertWithTransaction() {
        reset();
        try {
            userService.batchInsertWithTransaction(Arrays.asList(new User().setId("Chandler").setAge(35), new User().setId("Jack").setAge(35), new User().setAge(35), new User().setId("Phoebe").setAge(35)));
        } catch (Exception e) {
            logger.error("Transaction exception catched");
        }
        Assert.assertFalse(userService.get(new UserVO().setId("Chandler")).isPresent());
        Assert.assertFalse(userService.get(new UserVO().setId("Jack")).isPresent());
        Assert.assertFalse(userService.get(new UserVO().setId("Phoebe")).isPresent());
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
