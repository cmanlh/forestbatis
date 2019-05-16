package com.lifeonwalden.forestbatis.example.test;

import com.lifeonwalden.forestbatis.bean.ParameterInfo;
import com.lifeonwalden.forestbatis.bean.PropertyInfo;
import com.lifeonwalden.forestbatis.constant.JdbcType;
import com.lifeonwalden.forestbatis.parsing.PropertyParser;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserTest {
    private static Logger logger = LoggerFactory.getLogger(ParserTest.class);

    @Test
    public void noParameter() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getSql(), "select * from User".equals(parameterInfo.getDebugSql())
                    && parameterInfo.getProps().isPresent() == false
                    && "select * from User".equals(parameterInfo.getSql()));
        } else {
            Assert.assertTrue(parameterInfo.getSql(), parameterInfo.getDebugSql() == null
                    && parameterInfo.getProps().isPresent() == false
                    && "select * from User".equals(parameterInfo.getSql()));
        }
    }

    @Test
    public void noParameterWithColumn() {
        ParameterInfo parameterInfo = PropertyParser.parse("select id, name from User");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getSql(), "select id, name from User".equals(parameterInfo.getDebugSql())
                    && parameterInfo.getProps().isPresent() == false
                    && "select id, name from User".equals(parameterInfo.getSql()));
        } else {
            Assert.assertTrue(parameterInfo.getSql(), parameterInfo.getDebugSql() == null
                    && parameterInfo.getProps().isPresent() == false
                    && "select id, name from User".equals(parameterInfo.getSql()));
        }
    }

    @Test
    public void oneParameter() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=VARCHAR}");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "select * from User where id = '#0'".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter", parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 1);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "select * from User where id = ?".equals(parameterInfo.getSql()));
    }

    @Test
    public void oneIntegerParameter() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=INTEGER}");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "select * from User where id = #0".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter", parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 1);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "select * from User where id = ?".equals(parameterInfo.getSql()));
    }

    @Test(expected = RuntimeException.class)
    public void oneParameterWithInvalidParam() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id}");
    }

    @Test(expected = RuntimeException.class)
    public void oneParameterWithWrongExpression() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id, jdbcType=VARCHAR}");
    }

    @Test(expected = RuntimeException.class)
    public void oneParameterWithJdbcType() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=VARCHAr}");
    }

    @Test
    public void twoParameter() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=VARCHAR} and name like #{name, JdbcType=VARCHAR}");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "select * from User where id = '#0' and name like '#1'".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter", parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 2);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = parameterInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "select * from User where id = ? and name like ?".equals(parameterInfo.getSql()));
    }

    @Test
    public void twoParameterWithList() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType= VARCHAR } and name like #{ name, JdbcType= VARCHAR} and sex in (#{sex, JdbcType=INTEGER, ListSize = 2 })");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "select * from User where id = '#0' and name like '#1' and sex in (#2, #3)".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(parameterInfo.getProps().get().size())), parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 4);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = parameterInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        PropertyInfo sex0PropertyInfo = parameterInfo.getProps().get().get(2);
        Assert.assertTrue("sex0 jdbc type is not right : ".concat(sex0PropertyInfo.getJdbcType().getName()), sex0PropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("sex0 name is not right : ".concat(sex0PropertyInfo.getName()), sex0PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex0 index is not right : ".concat(String.valueOf(sex0PropertyInfo.getIndex())), sex0PropertyInfo.getIndex() == 2);
        Assert.assertTrue("sex0 listProperty is not right : ".concat(String.valueOf(sex0PropertyInfo.isListProperty())), sex0PropertyInfo.isListProperty());
        Assert.assertTrue("sex0 listIndex is not right : ".concat(String.valueOf(sex0PropertyInfo.getListIndex())), sex0PropertyInfo.getListIndex() == 0);

        PropertyInfo sex1PropertyInfo = parameterInfo.getProps().get().get(3);
        Assert.assertTrue("sex1 jdbc type is not right : ".concat(sex1PropertyInfo.getJdbcType().getName()), sex1PropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("sex1 name is not right : ".concat(sex1PropertyInfo.getName()), sex1PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex1 index is not right : ".concat(String.valueOf(sex1PropertyInfo.getIndex())), sex1PropertyInfo.getIndex() == 3);
        Assert.assertTrue("sex1 listProperty is not right : ".concat(String.valueOf(sex1PropertyInfo.isListProperty())), sex1PropertyInfo.isListProperty());
        Assert.assertTrue("sex1 listIndex is not right : ".concat(String.valueOf(sex1PropertyInfo.getListIndex())), sex1PropertyInfo.getListIndex() == 1);

        Assert.assertTrue(parameterInfo.getSql(), "select * from User where id = ? and name like ? and sex in (?, ?)".equals(parameterInfo.getSql()));
    }

    @Test
    public void twoParameterWithTextList() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType= INTEGER } and name like #{ name, JdbcType= VARCHAR} and sex in (#{sex, JdbcType=VARCHAR, ListSize = 2 })");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "select * from User where id = #0 and name like '#1' and sex in ('#2', '#3')".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(parameterInfo.getProps().get().size())), parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 4);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = parameterInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        PropertyInfo sex0PropertyInfo = parameterInfo.getProps().get().get(2);
        Assert.assertTrue("sex0 jdbc type is not right : ".concat(sex0PropertyInfo.getJdbcType().getName()), sex0PropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("sex0 name is not right : ".concat(sex0PropertyInfo.getName()), sex0PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex0 index is not right : ".concat(String.valueOf(sex0PropertyInfo.getIndex())), sex0PropertyInfo.getIndex() == 2);
        Assert.assertTrue("sex0 listProperty is not right : ".concat(String.valueOf(sex0PropertyInfo.isListProperty())), sex0PropertyInfo.isListProperty());
        Assert.assertTrue("sex0 listIndex is not right : ".concat(String.valueOf(sex0PropertyInfo.getListIndex())), sex0PropertyInfo.getListIndex() == 0);

        PropertyInfo sex1PropertyInfo = parameterInfo.getProps().get().get(3);
        Assert.assertTrue("sex1 jdbc type is not right : ".concat(sex1PropertyInfo.getJdbcType().getName()), sex1PropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("sex1 name is not right : ".concat(sex1PropertyInfo.getName()), sex1PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex1 index is not right : ".concat(String.valueOf(sex1PropertyInfo.getIndex())), sex1PropertyInfo.getIndex() == 3);
        Assert.assertTrue("sex1 listProperty is not right : ".concat(String.valueOf(sex1PropertyInfo.isListProperty())), sex1PropertyInfo.isListProperty());
        Assert.assertTrue("sex1 listIndex is not right : ".concat(String.valueOf(sex1PropertyInfo.getListIndex())), sex1PropertyInfo.getListIndex() == 1);

        Assert.assertTrue(parameterInfo.getSql(), "select * from User where id = ? and name like ? and sex in (?, ?)".equals(parameterInfo.getSql()));
    }

    @Test
    public void update() {
        ParameterInfo parameterInfo = PropertyParser.parse("Update User set name = #{ name, JdbcType= VARCHAR}, sex = #{sex, JdbcType=VARCHAR} where id = #{id, JdbcType= INTEGER }");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "Update User set name = '#0', sex = '#1' where id = #2".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(parameterInfo.getProps().get().size())), parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 3);

        PropertyInfo namePropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 0);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        PropertyInfo sex0PropertyInfo = parameterInfo.getProps().get().get(1);
        Assert.assertTrue("sex0 jdbc type is not right : ".concat(sex0PropertyInfo.getJdbcType().getName()), sex0PropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("sex0 name is not right : ".concat(sex0PropertyInfo.getName()), sex0PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex0 index is not right : ".concat(String.valueOf(sex0PropertyInfo.getIndex())), sex0PropertyInfo.getIndex() == 1);
        Assert.assertTrue("sex0 listProperty is not right : ".concat(String.valueOf(sex0PropertyInfo.isListProperty())), sex0PropertyInfo.isListProperty() == false);
        Assert.assertTrue("sex0 listIndex is not right : ".concat(String.valueOf(sex0PropertyInfo.getListIndex())), sex0PropertyInfo.getListIndex() == 0);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(2);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 2);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "Update User set name = ?, sex = ? where id = ?".equals(parameterInfo.getSql()));
    }

    @Test
    public void delete() {
        ParameterInfo parameterInfo = PropertyParser.parse("delete from User where id = #{id, JdbcType= INTEGER }");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "delete from User where id = #0".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(parameterInfo.getProps().get().size())), parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 1);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "delete from User where id = ?".equals(parameterInfo.getSql()));
    }

    @Test
    public void insert() {
        ParameterInfo parameterInfo = PropertyParser.parse("insert into User(id, name, sex) values(#{id, JdbcType= INTEGER }, #{ name, JdbcType= VARCHAR}, #{sex, JdbcType=VARCHAR})");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "insert into User(id, name, sex) values(#0, '#1', '#2')".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(parameterInfo.getProps().get().size())), parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 3);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "insert into User(id, name, sex) values(?, ?, ?)".equals(parameterInfo.getSql()));
    }

    @Test
    public void subSelect() {
        ParameterInfo parameterInfo = PropertyParser.parse("select * from User u where exists (select 1 from Item i where u.id=i.userId) and name like #{ name, JdbcType= VARCHAR}");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "select * from User u where exists (select 1 from Item i where u.id=i.userId) and name like '#0'".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(parameterInfo.getProps().get().size())), parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 1);

        PropertyInfo namePropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 0);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "select * from User u where exists (select 1 from Item i where u.id=i.userId) and name like ?".equals(parameterInfo.getSql()));
    }

    @Test
    public void join() {
        ParameterInfo parameterInfo = PropertyParser.parse("select u.id, u.name, i.name from User u left join Item i on (u.id = i.userId and i.type = #{type, JdbcType=INTEGER}) where u.name like #{name, JdbcType=VARCHAR}");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(parameterInfo.getDebugSql(), "select u.id, u.name, i.name from User u left join Item i on (u.id = i.userId and i.type = #0) where u.name like '#1'".equals(parameterInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(parameterInfo.getProps().get().size())), parameterInfo.getProps().isPresent() && parameterInfo.getProps().get().size() == 2);

        PropertyInfo idPropertyInfo = parameterInfo.getProps().get().get(0);
        Assert.assertTrue("type jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("type name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("type"));
        Assert.assertTrue("type index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("type listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = parameterInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        Assert.assertTrue(parameterInfo.getSql(), "select u.id, u.name, i.name from User u left join Item i on (u.id = i.userId and i.type = ?) where u.name like ?".equals(parameterInfo.getSql()));
    }
}
