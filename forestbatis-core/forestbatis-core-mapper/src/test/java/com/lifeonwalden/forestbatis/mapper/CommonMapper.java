package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.result.AbstractRecordHandler;
import com.lifeonwalden.forestbatis.util.IndependentBeanSingletonFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonMapper {
    protected static final String CONNECTION_STRING = "jdbc:hsqldb:mem:testdb;shutdown=false";
    protected static final String USER_NAME = "SA";
    protected static final String PASSWORD = "";

    @Before
    public void setup() throws SQLException, IOException, ClassNotFoundException {
        importSql("/setup.sql");
    }

    @Test
    public void select() {
        reset();

        Connection connection = getConnection();

        System.out.println(new AbstractRecordHandler<User>() {
            @Override
            public User newBeanInstance() {
                return new User();
            }
        }.getClass().getTypeName());

        System.out.println(IndependentBeanSingletonFactory.class.getName());
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
