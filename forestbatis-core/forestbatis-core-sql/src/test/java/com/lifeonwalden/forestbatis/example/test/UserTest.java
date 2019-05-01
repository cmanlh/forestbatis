package com.lifeonwalden.forestbatis.example.test;

import com.lifeonwalden.forestbatis.constant.OrderEnum;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.example.meta.UserTableInfo;
import com.lifeonwalden.forestbatis.meta.Bt;
import com.lifeonwalden.forestbatis.meta.Eq;
import com.lifeonwalden.forestbatis.meta.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

public class UserTest {
    @Test
    public void base() {
        String sql = UserBuilder.BASE_SELECT.build();
        Assert.assertTrue(sql, "select id, name, age, birthday from user".equals(sql));
    }

    @Test
    public void base_withEmptyObject() {
        String sql = UserBuilder.BASE_SELECT.build(new User());
        Assert.assertTrue(sql, "select id, name, age, birthday from user".equals(sql));
    }

    @Test
    public void base_withFirstProperty() {
        String sql = UserBuilder.BASE_SELECT.build(new User().setId("Tom"));
        Assert.assertTrue(sql, "select id, name, age, birthday from user where id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withSecondProperty() {
        String sql = UserBuilder.BASE_SELECT.build(new User().setName("Tom"));
        Assert.assertTrue(sql, "select id, name, age, birthday from user where name = #{name, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withOr() {
        String sql = UserBuilder.BASE_SELECT.overrideQuery(new Eq(UserTableInfo.Name).or(new Eq(UserTableInfo.Id))).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from user where name = #{name, JdbcType=VARCHAR} or id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withTwoProperty() {
        String sql = UserBuilder.BASE_SELECT.build(new User().setId("Tom").setBirthday(new Date()));
        Assert.assertTrue(sql, "select id, name, age, birthday from user where id = #{id, JdbcType=VARCHAR} and birthday = #{birthday, JdbcType=DATE}".equals(sql));
    }

    @Test
    public void base_withPickedColumn() {
        String sql = UserBuilder.BASE_SELECT.overrideReturnColumn(Arrays.asList(UserTableInfo.Id, UserTableInfo.Birthday)).build();
        Assert.assertTrue(sql, "select id, birthday from user".equals(sql));
    }

    @Test
    public void base_withExcludeColumn() {
        String sql = UserBuilder.BASE_SELECT.overrideReturnColumnExclude(Arrays.asList(UserTableInfo.Name)).build();
        Assert.assertTrue(sql, "select id, age, birthday from user".equals(sql));
    }

    @Test
    public void base_withOrder() {
        String sql = UserBuilder.BASE_SELECT.overrideOrder(Arrays.asList(new Order(UserTableInfo.Birthday))).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from user order by birthday asc".equals(sql));
    }

    @Test
    public void base_withDoubleOrder() {
        String sql = UserBuilder.BASE_SELECT.overrideOrder(Arrays.asList(new Order(UserTableInfo.Birthday), new Order(UserTableInfo.Age, OrderEnum.DESC))).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from user order by birthday asc, age desc".equals(sql));
    }

    @Test
    public void base_withBt() {
        String sql = UserBuilder.BASE_SELECT.overrideQuery(new Bt(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from user where age > #{age, JdbcType=INTEGER}".equals(sql));
    }
}
