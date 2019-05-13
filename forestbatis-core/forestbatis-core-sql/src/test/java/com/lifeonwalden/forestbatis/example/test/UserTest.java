package com.lifeonwalden.forestbatis.example.test;

import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.constant.OrderEnum;
import com.lifeonwalden.forestbatis.example.bean.User;
import com.lifeonwalden.forestbatis.example.builder.UserBuilder;
import com.lifeonwalden.forestbatis.example.meta.AccountTableInfo;
import com.lifeonwalden.forestbatis.example.meta.UserCreditTableInfo;
import com.lifeonwalden.forestbatis.example.meta.UserTableInfo;
import com.lifeonwalden.forestbatis.meta.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserTest {
    @Test
    public void base() {
        String sql = UserBuilder.SELECT.build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User".equals(sql));
    }

    @Test
    public void base_withEmptyObject() {
        String sql = UserBuilder.SELECT.build(new User());
        Assert.assertTrue(sql, "select id, name, age, birthday from User".equals(sql));
    }

    @Test
    public void base_withFirstProperty() {
        String sql = UserBuilder.SELECT.build(new User().setId("Tom"));
        Assert.assertTrue(sql, "select id, name, age, birthday from User where id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withSecondProperty() {
        String sql = UserBuilder.SELECT.build(new User().setName("Tom"));
        Assert.assertTrue(sql, "select id, name, age, birthday from User where name = #{name, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withOr() {
        String sql = UserBuilder.SELECT.overrideQuery(new Eq(UserTableInfo.Name).or(new Eq(UserTableInfo.Id))).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where name = #{name, JdbcType=VARCHAR} or id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withTwoProperty() {
        String sql = UserBuilder.SELECT.build(new User().setId("Tom").setBirthday(new Date()));
        Assert.assertTrue(sql, "select id, name, age, birthday from User where id = #{id, JdbcType=VARCHAR} and birthday = #{birthday, JdbcType=DATE}".equals(sql));
    }

    @Test
    public void base_withPickedColumn() {
        String sql = UserBuilder.SELECT.overrideReturnColumn(Arrays.asList(UserTableInfo.Id, UserTableInfo.Birthday)).build();
        Assert.assertTrue(sql, "select id, birthday from User".equals(sql));
    }

    @Test
    public void base_withExcludeColumn() {
        String sql = UserBuilder.SELECT.excludeReturnColumn(Arrays.asList(UserTableInfo.Name)).build();
        Assert.assertTrue(sql, "select id, age, birthday from User".equals(sql));
    }

    @Test
    public void base_withAddColumn() {
        String sql = UserBuilder.SELECT.excludeReturnColumn(Arrays.asList(UserTableInfo.Name, UserTableInfo.Age, UserTableInfo.Birthday))
                .addReturnColumn(Arrays.asList(UserTableInfo.Birthday)).build();
        Assert.assertTrue(sql, "select id, birthday from User".equals(sql));
    }

    @Test
    public void base_withOrder() {
        String sql = UserBuilder.SELECT.overrideOrder(Arrays.asList(new Order(UserTableInfo.Birthday))).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User order by birthday asc".equals(sql));
    }

    @Test
    public void base_withDoubleOrder() {
        String sql = UserBuilder.SELECT.overrideOrder(Arrays.asList(new Order(UserTableInfo.Birthday), new Order(UserTableInfo.Age, OrderEnum.DESC))).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User order by birthday asc, age desc".equals(sql));
    }

    @Test
    public void base_withIsNull() {
        String sql = UserBuilder.SELECT.overrideQuery(new IsNull(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where age is null ".equals(sql));
    }

    @Test
    public void base_withIsNotNull() {
        String sql = UserBuilder.SELECT.overrideQuery(new IsNotNull(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where age is not null ".equals(sql));
    }

    @Test
    public void base_withLike() {
        String sql = UserBuilder.SELECT.overrideQuery(new Like(UserTableInfo.Name)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where name like #{name, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withNotLike() {
        String sql = UserBuilder.SELECT.overrideQuery(new NotLike(UserTableInfo.Name)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where name not like #{name, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withBt() {
        String sql = UserBuilder.SELECT.overrideQuery(new Bt(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where age > #{age, JdbcType=INTEGER}".equals(sql));
    }

    @Test
    public void base_withBte() {
        String sql = UserBuilder.SELECT.overrideQuery(new Bte(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where age >= #{age, JdbcType=INTEGER}".equals(sql));
    }

    @Test
    public void base_withLt() {
        String sql = UserBuilder.SELECT.overrideQuery(new Lt(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where age < #{age, JdbcType=INTEGER}".equals(sql));
    }

    @Test
    public void base_withLte() {
        String sql = UserBuilder.SELECT.overrideQuery(new Lte(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where age <= #{age, JdbcType=INTEGER}".equals(sql));
    }

    @Test
    public void base_withNeq() {
        String sql = UserBuilder.SELECT.overrideQuery(new Neq(UserTableInfo.Age)).build();
        Assert.assertTrue(sql, "select id, name, age, birthday from User where age <> #{age, JdbcType=INTEGER}".equals(sql));
    }

    @Test
    public void base_withCompound() {
        String sql = UserBuilder.SELECT.overrideQuery(new CompoundQuery(new Bt(UserTableInfo.Age).or(new Eq(UserTableInfo.Name))).or(new Eq(UserTableInfo.Birthday))).build();
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User where (age > #{age, JdbcType=INTEGER} or name = #{name, JdbcType=VARCHAR}) or birthday = #{birthday, JdbcType=DATE}".equals(sql));
    }

    @Test
    public void base_withCompoundDisableOne() {
        String sql = UserBuilder.SELECT.overrideQuery(
                new CompoundQuery(new Bt(UserTableInfo.Age).or(new Eq<User>(UserTableInfo.Name, user -> user.isPresent() && null != user.get().getName())))
                        .or(new Eq(UserTableInfo.Birthday))).build();
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User where (age > #{age, JdbcType=INTEGER}) or birthday = #{birthday, JdbcType=DATE}".equals(sql));
    }

    @Test
    public void base_withExists() {
        String sql = UserBuilder.SELECT.overrideQuery(
                new Exists(new TempTable("account", new SubSelect()
                        .setTableNode(new TableNode(AccountTableInfo.TABLE))
                        .setQuery(new Eq(UserTableInfo.Id, AccountTableInfo.UserId))))).build();
        Assert.assertTrue(sql,
                "select u.id, u.name, u.age, u.birthday from User u where  exists (select 1 from Account a where u.id = a.userId)".equals(sql));
    }

    @Test
    public void base_withNotExists() {
        String sql = UserBuilder.SELECT.overrideQuery(
                new NotExists(new TempTable("account", new SubSelect()
                        .setTableNode(new TableNode(AccountTableInfo.TABLE))
                        .setQuery(new Eq(UserTableInfo.Id, AccountTableInfo.UserId))))).build();
        Assert.assertTrue(sql,
                "select u.id, u.name, u.age, u.birthday from User u where  not exists (select 1 from Account a where u.id = a.userId)".equals(sql));
    }

    @Test
    public void base_withLeftJoin() {
        List<ColumnMeta> toReturnColumnList = new ArrayList<>();
        toReturnColumnList.addAll(UserTableInfo.TABLE.getColumn().get());
        toReturnColumnList.add(AccountTableInfo.Balance);
        String sql = new SelectBuilder<User>(
                new TableNode(UserTableInfo.TABLE).leftJoin(new JoinNode(AccountTableInfo.TABLE, new JoinCondition(UserTableInfo.Id, AccountTableInfo.UserId))),
                toReturnColumnList,
                new Eq(UserTableInfo.Id)).build();
        Assert.assertTrue(sql,
                "select u.id, u.name, u.age, u.birthday, a.balance from User u left join Account a on u.id = a.userId where u.id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withRightJoin() {
        List<ColumnMeta> toReturnColumnList = new ArrayList<>();
        toReturnColumnList.addAll(UserTableInfo.TABLE.getColumn().get());
        toReturnColumnList.add(AccountTableInfo.Balance);
        String sql = new SelectBuilder<User>(
                new TableNode(UserTableInfo.TABLE).rightJoin(new JoinNode(AccountTableInfo.TABLE, new JoinCondition(UserTableInfo.Id, AccountTableInfo.UserId))),
                toReturnColumnList,
                new Eq(UserTableInfo.Id)).build();
        Assert.assertTrue(sql,
                "select u.id, u.name, u.age, u.birthday, a.balance from User u right join Account a on u.id = a.userId where u.id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withInnerJoin() {
        List<ColumnMeta> toReturnColumnList = new ArrayList<>();
        toReturnColumnList.addAll(UserTableInfo.TABLE.getColumn().get());
        toReturnColumnList.add(AccountTableInfo.Balance);
        String sql = new SelectBuilder<User>(
                new TableNode(UserTableInfo.TABLE).innerJoin(new JoinNode(AccountTableInfo.TABLE, new JoinCondition(UserTableInfo.Id, AccountTableInfo.UserId))),
                toReturnColumnList,
                new Eq(UserTableInfo.Id)).build();
        Assert.assertTrue(sql,
                "select u.id, u.name, u.age, u.birthday, a.balance from User u inner join Account a on u.id = a.userId where u.id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withMultipleJoin() {
        List<ColumnMeta> toReturnColumnList = new ArrayList<>();
        toReturnColumnList.addAll(UserTableInfo.TABLE.getColumn().get());
        toReturnColumnList.add(AccountTableInfo.Balance);
        toReturnColumnList.add(UserCreditTableInfo.Level);
        String sql = new SelectBuilder<User>(
                new TableNode(UserTableInfo.TABLE)
                        .leftJoin(new JoinNode(AccountTableInfo.TABLE, new JoinCondition(UserTableInfo.Id, AccountTableInfo.UserId)))
                        .rightJoin(new JoinNode(UserCreditTableInfo.TABLE, new JoinCondition(UserTableInfo.Id, UserCreditTableInfo.UserId))),
                toReturnColumnList,
                new Eq(UserTableInfo.Id)).build();
        Assert.assertTrue(sql,
                "select u.id, u.name, u.age, u.birthday, a.balance, uc.level from User u left join Account a on u.id = a.userId right join UserCredit uc on u.id = uc.userId where u.id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void base_withInnerFirstDisabled() {
        String sql = UserBuilder.SELECT
                .overrideQuery(new Bt<User>(UserTableInfo.Age, user -> user.isPresent() && null != user.get().getAge()).and(new Eq(UserTableInfo.Name).or(new Eq(UserTableInfo.Birthday)))).build();
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User where name = #{name, JdbcType=VARCHAR} or birthday = #{birthday, JdbcType=DATE}".equals(sql));
    }

    @Test
    public void base_withInnerFirstTwoDisabled() {
        String sql = UserBuilder.SELECT
                .overrideQuery(new Bt<User>(UserTableInfo.Age, user -> user.isPresent() && null != user.get().getAge()).and(new Eq<User>(UserTableInfo.Name, user -> user.isPresent() && null != user.get().getName()).or(new Eq(UserTableInfo.Birthday)))).build();
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User where birthday = #{birthday, JdbcType=DATE}".equals(sql));
    }

    @Test
    public void base_withInnerSecondDisabled() {
        String sql = UserBuilder.SELECT
                .overrideQuery(new Bt(UserTableInfo.Age).and(new Eq<User>(UserTableInfo.Name, user -> user.isPresent() && null != user.get().getName()).or(new Eq(UserTableInfo.Birthday)))).build();
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User where age > #{age, JdbcType=INTEGER} or birthday = #{birthday, JdbcType=DATE}".equals(sql));
    }

    @Test
    public void base_withInNull() {
        String sql = UserBuilder.SELECT.overrideQuery(new In(UserTableInfo.Age)).build();
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User".equals(sql));
    }

    @Test
    public void base_withInEmpty() {
        User user = new User();
        user.put(UserTableInfo.sex.getName(), Arrays.asList());
        String sql = UserBuilder.SELECT.overrideQuery(new In(UserTableInfo.Sex)).build(user);
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User".equals(sql));
    }

    @Test
    public void base_withIn() {
        User user = new User();
        user.put(UserTableInfo.sex.getName(), Arrays.asList(1, 2));
        String sql = UserBuilder.SELECT.overrideQuery(new In(UserTableInfo.Sex)).build(user);
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User where sex in (#{sex, JdbcType=INTEGER, ListSize=2})".equals(sql));
    }

    @Test
    public void base_withNotIn() {
        User user = new User();
        user.put(UserTableInfo.sex.getName(), Arrays.asList(1, 2));
        String sql = UserBuilder.SELECT.overrideQuery(new NotIn(UserTableInfo.Sex)).build(user);
        Assert.assertTrue(sql,
                "select id, name, age, birthday from User where sex not in (#{sex, JdbcType=INTEGER, ListSize=2})".equals(sql));
    }

    @Test
    public void insert() {
        String sql = UserBuilder.INSERT.build();
        Assert.assertTrue(sql,
                "insert into User (id, name, age, birthday) values(#{id, JdbcType=VARCHAR}, #{name, JdbcType=VARCHAR}, #{age, JdbcType=INTEGER}, #{birthday, JdbcType=DATE})".equals(sql));
    }

    @Test
    public void insert_withNull() {
        String sql = UserBuilder.INSERT.build(new User());
        Assert.assertTrue(sql,
                "insert into User (id, name, age, birthday) values(#{id, JdbcType=VARCHAR}, #{name, JdbcType=VARCHAR}, #{age, JdbcType=INTEGER}, #{birthday, JdbcType=DATE})".equals(sql));
    }

    @Test
    public void insert_withNull_notInsertNull() {
        String sql = UserBuilder.INSERT.addInsertColumn(Arrays.asList(), false).build(new User().setId("id").setName("Tom"));
        Assert.assertTrue(sql,
                "insert into User (id, name) values(#{id, JdbcType=VARCHAR}, #{name, JdbcType=VARCHAR})".equals(sql));
    }

    @Test
    public void insert_withExcludeColumn() {
        String sql = UserBuilder.INSERT.excludeInsertColumn(Arrays.asList(UserTableInfo.Name)).build(new User().setId("id").setName("Tom"));
        Assert.assertTrue(sql,
                "insert into User (id, age, birthday) values(#{id, JdbcType=VARCHAR}, #{age, JdbcType=INTEGER}, #{birthday, JdbcType=DATE})".equals(sql));
    }

    @Test
    public void delete() {
        String sql = UserBuilder.DELETE.build();
        Assert.assertTrue(sql,
                "delete from User where id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void delete_withQuery() {
        String sql = UserBuilder.DELETE.overrideQuery(new Eq(UserTableInfo.Name)).build();
        Assert.assertTrue(sql,
                "delete from User where name = #{name, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void update() {
        String sql = UserBuilder.UPDATE.build();
        Assert.assertTrue(sql,
                "update User set name = #{name, JdbcType=VARCHAR}, age = #{age, JdbcType=INTEGER}, birthday = #{birthday, JdbcType=DATE} where id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void update_withNull() {
        String sql = UserBuilder.UPDATE.build(new User());
        Assert.assertTrue(sql,
                "update User set name = #{name, JdbcType=VARCHAR}, age = #{age, JdbcType=INTEGER}, birthday = #{birthday, JdbcType=DATE} where id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void update_withNull_NotUpdateNull() {
        String sql = UserBuilder.UPDATE.addUpdateColumn(Arrays.asList(), false).build(new User().setId("id").setName("Tom"));
        Assert.assertTrue(sql,
                "update User set name = #{name, JdbcType=VARCHAR} where id = #{id, JdbcType=VARCHAR}".equals(sql));
    }

    @Test
    public void update_withQuery() {
        String sql = UserBuilder.UPDATE.overrideQuery(new Like(UserTableInfo.Name)).build(new User().setId("id").setName("Tom"));
        Assert.assertTrue(sql,
                "update User set name = #{name, JdbcType=VARCHAR}, age = #{age, JdbcType=INTEGER}, birthday = #{birthday, JdbcType=DATE} where name like #{name, JdbcType=VARCHAR}".equals(sql));
    }
}