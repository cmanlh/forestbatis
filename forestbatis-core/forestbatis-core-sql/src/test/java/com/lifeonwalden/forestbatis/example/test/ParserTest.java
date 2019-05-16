package com.lifeonwalden.forestbatis.example.test;

import com.lifeonwalden.forestbatis.bean.StatementInfo;
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
        StatementInfo statementInfo = PropertyParser.parse("select * from User");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getSql(), "select * from User".equals(statementInfo.getDebugSql())
                    && statementInfo.getProps().isPresent() == false
                    && "select * from User".equals(statementInfo.getSql()));
        } else {
            Assert.assertTrue(statementInfo.getSql(), statementInfo.getDebugSql() == null
                    && statementInfo.getProps().isPresent() == false
                    && "select * from User".equals(statementInfo.getSql()));
        }
    }

    @Test
    public void noParameterWithColumn() {
        StatementInfo statementInfo = PropertyParser.parse("select id, name from User");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getSql(), "select id, name from User".equals(statementInfo.getDebugSql())
                    && statementInfo.getProps().isPresent() == false
                    && "select id, name from User".equals(statementInfo.getSql()));
        } else {
            Assert.assertTrue(statementInfo.getSql(), statementInfo.getDebugSql() == null
                    && statementInfo.getProps().isPresent() == false
                    && "select id, name from User".equals(statementInfo.getSql()));
        }
    }

    @Test
    public void oneParameter() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=VARCHAR}");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "select * from User where id = '#0'".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter", statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 1);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "select * from User where id = ?".equals(statementInfo.getSql()));
    }

    @Test
    public void oneIntegerParameter() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=INTEGER}");

        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "select * from User where id = #0".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter", statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 1);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "select * from User where id = ?".equals(statementInfo.getSql()));
    }

    @Test(expected = RuntimeException.class)
    public void oneParameterWithInvalidParam() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id}");
    }

    @Test(expected = RuntimeException.class)
    public void oneParameterWithWrongExpression() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id, jdbcType=VARCHAR}");
    }

    @Test(expected = RuntimeException.class)
    public void oneParameterWithJdbcType() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=VARCHAr}");
    }

    @Test
    public void twoParameter() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType=VARCHAR} and name like #{name, JdbcType=VARCHAR}");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "select * from User where id = '#0' and name like '#1'".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter", statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 2);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = statementInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "select * from User where id = ? and name like ?".equals(statementInfo.getSql()));
    }

    @Test
    public void twoParameterWithList() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType= VARCHAR } and name like #{ name, JdbcType= VARCHAR} and sex in (#{sex, JdbcType=INTEGER, ListSize = 2 })");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "select * from User where id = '#0' and name like '#1' and sex in (#2, #3)".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(statementInfo.getProps().get().size())), statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 4);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = statementInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        PropertyInfo sex0PropertyInfo = statementInfo.getProps().get().get(2);
        Assert.assertTrue("sex0 jdbc type is not right : ".concat(sex0PropertyInfo.getJdbcType().getName()), sex0PropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("sex0 name is not right : ".concat(sex0PropertyInfo.getName()), sex0PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex0 index is not right : ".concat(String.valueOf(sex0PropertyInfo.getIndex())), sex0PropertyInfo.getIndex() == 2);
        Assert.assertTrue("sex0 listProperty is not right : ".concat(String.valueOf(sex0PropertyInfo.isListProperty())), sex0PropertyInfo.isListProperty());
        Assert.assertTrue("sex0 listIndex is not right : ".concat(String.valueOf(sex0PropertyInfo.getListIndex())), sex0PropertyInfo.getListIndex() == 0);

        PropertyInfo sex1PropertyInfo = statementInfo.getProps().get().get(3);
        Assert.assertTrue("sex1 jdbc type is not right : ".concat(sex1PropertyInfo.getJdbcType().getName()), sex1PropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("sex1 name is not right : ".concat(sex1PropertyInfo.getName()), sex1PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex1 index is not right : ".concat(String.valueOf(sex1PropertyInfo.getIndex())), sex1PropertyInfo.getIndex() == 3);
        Assert.assertTrue("sex1 listProperty is not right : ".concat(String.valueOf(sex1PropertyInfo.isListProperty())), sex1PropertyInfo.isListProperty());
        Assert.assertTrue("sex1 listIndex is not right : ".concat(String.valueOf(sex1PropertyInfo.getListIndex())), sex1PropertyInfo.getListIndex() == 1);

        Assert.assertTrue(statementInfo.getSql(), "select * from User where id = ? and name like ? and sex in (?, ?)".equals(statementInfo.getSql()));
    }

    @Test
    public void twoParameterWithTextList() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User where id = #{id, JdbcType= INTEGER } and name like #{ name, JdbcType= VARCHAR} and sex in (#{sex, JdbcType=VARCHAR, ListSize = 2 })");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "select * from User where id = #0 and name like '#1' and sex in ('#2', '#3')".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(statementInfo.getProps().get().size())), statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 4);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = statementInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        PropertyInfo sex0PropertyInfo = statementInfo.getProps().get().get(2);
        Assert.assertTrue("sex0 jdbc type is not right : ".concat(sex0PropertyInfo.getJdbcType().getName()), sex0PropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("sex0 name is not right : ".concat(sex0PropertyInfo.getName()), sex0PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex0 index is not right : ".concat(String.valueOf(sex0PropertyInfo.getIndex())), sex0PropertyInfo.getIndex() == 2);
        Assert.assertTrue("sex0 listProperty is not right : ".concat(String.valueOf(sex0PropertyInfo.isListProperty())), sex0PropertyInfo.isListProperty());
        Assert.assertTrue("sex0 listIndex is not right : ".concat(String.valueOf(sex0PropertyInfo.getListIndex())), sex0PropertyInfo.getListIndex() == 0);

        PropertyInfo sex1PropertyInfo = statementInfo.getProps().get().get(3);
        Assert.assertTrue("sex1 jdbc type is not right : ".concat(sex1PropertyInfo.getJdbcType().getName()), sex1PropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("sex1 name is not right : ".concat(sex1PropertyInfo.getName()), sex1PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex1 index is not right : ".concat(String.valueOf(sex1PropertyInfo.getIndex())), sex1PropertyInfo.getIndex() == 3);
        Assert.assertTrue("sex1 listProperty is not right : ".concat(String.valueOf(sex1PropertyInfo.isListProperty())), sex1PropertyInfo.isListProperty());
        Assert.assertTrue("sex1 listIndex is not right : ".concat(String.valueOf(sex1PropertyInfo.getListIndex())), sex1PropertyInfo.getListIndex() == 1);

        Assert.assertTrue(statementInfo.getSql(), "select * from User where id = ? and name like ? and sex in (?, ?)".equals(statementInfo.getSql()));
    }

    @Test
    public void update() {
        StatementInfo statementInfo = PropertyParser.parse("Update User set name = #{ name, JdbcType= VARCHAR}, sex = #{sex, JdbcType=VARCHAR} where id = #{id, JdbcType= INTEGER }");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "Update User set name = '#0', sex = '#1' where id = #2".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(statementInfo.getProps().get().size())), statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 3);

        PropertyInfo namePropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 0);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        PropertyInfo sex0PropertyInfo = statementInfo.getProps().get().get(1);
        Assert.assertTrue("sex0 jdbc type is not right : ".concat(sex0PropertyInfo.getJdbcType().getName()), sex0PropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("sex0 name is not right : ".concat(sex0PropertyInfo.getName()), sex0PropertyInfo.getName().equals("sex"));
        Assert.assertTrue("sex0 index is not right : ".concat(String.valueOf(sex0PropertyInfo.getIndex())), sex0PropertyInfo.getIndex() == 1);
        Assert.assertTrue("sex0 listProperty is not right : ".concat(String.valueOf(sex0PropertyInfo.isListProperty())), sex0PropertyInfo.isListProperty() == false);
        Assert.assertTrue("sex0 listIndex is not right : ".concat(String.valueOf(sex0PropertyInfo.getListIndex())), sex0PropertyInfo.getListIndex() == 0);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(2);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 2);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "Update User set name = ?, sex = ? where id = ?".equals(statementInfo.getSql()));
    }

    @Test
    public void delete() {
        StatementInfo statementInfo = PropertyParser.parse("delete from User where id = #{id, JdbcType= INTEGER }");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "delete from User where id = #0".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(statementInfo.getProps().get().size())), statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 1);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "delete from User where id = ?".equals(statementInfo.getSql()));
    }

    @Test
    public void insert() {
        StatementInfo statementInfo = PropertyParser.parse("insert into User(id, name, sex) values(#{id, JdbcType= INTEGER }, #{ name, JdbcType= VARCHAR}, #{sex, JdbcType=VARCHAR})");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "insert into User(id, name, sex) values(#0, '#1', '#2')".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(statementInfo.getProps().get().size())), statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 3);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("id jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("id name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("id"));
        Assert.assertTrue("id index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("id listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "insert into User(id, name, sex) values(?, ?, ?)".equals(statementInfo.getSql()));
    }

    @Test
    public void subSelect() {
        StatementInfo statementInfo = PropertyParser.parse("select * from User u where exists (select 1 from Item i where u.id=i.userId) and name like #{ name, JdbcType= VARCHAR}");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "select * from User u where exists (select 1 from Item i where u.id=i.userId) and name like '#0'".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(statementInfo.getProps().get().size())), statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 1);

        PropertyInfo namePropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 0);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "select * from User u where exists (select 1 from Item i where u.id=i.userId) and name like ?".equals(statementInfo.getSql()));
    }

    @Test
    public void join() {
        StatementInfo statementInfo = PropertyParser.parse("select u.id, u.name, i.name from User u left join Item i on (u.id = i.userId and i.type = #{type, JdbcType=INTEGER}) where u.name like #{name, JdbcType=VARCHAR}");
        if (logger.isDebugEnabled()) {
            Assert.assertTrue(statementInfo.getDebugSql(), "select u.id, u.name, i.name from User u left join Item i on (u.id = i.userId and i.type = #0) where u.name like '#1'".equals(statementInfo.getDebugSql()));
        }

        Assert.assertTrue("No parameter : ".concat(String.valueOf(statementInfo.getProps().get().size())), statementInfo.getProps().isPresent() && statementInfo.getProps().get().size() == 2);

        PropertyInfo idPropertyInfo = statementInfo.getProps().get().get(0);
        Assert.assertTrue("type jdbc type is not right : ".concat(idPropertyInfo.getJdbcType().getName()), idPropertyInfo.getJdbcType() == JdbcType.INTEGER);
        Assert.assertTrue("type name is not right : ".concat(idPropertyInfo.getName()), idPropertyInfo.getName().equals("type"));
        Assert.assertTrue("type index is not right : ".concat(String.valueOf(idPropertyInfo.getIndex())), idPropertyInfo.getIndex() == 0);
        Assert.assertTrue("type listProperty is not right : ".concat(String.valueOf(idPropertyInfo.isListProperty())), idPropertyInfo.isListProperty() == false);

        PropertyInfo namePropertyInfo = statementInfo.getProps().get().get(1);
        Assert.assertTrue("name jdbc type is not right : ".concat(namePropertyInfo.getJdbcType().getName()), namePropertyInfo.getJdbcType() == JdbcType.VARCHAR);
        Assert.assertTrue("name name is not right : ".concat(namePropertyInfo.getName()), namePropertyInfo.getName().equals("name"));
        Assert.assertTrue("name index is not right : ".concat(String.valueOf(namePropertyInfo.getIndex())), namePropertyInfo.getIndex() == 1);
        Assert.assertTrue("name listProperty is not right : ".concat(String.valueOf(namePropertyInfo.isListProperty())), namePropertyInfo.isListProperty() == false);

        Assert.assertTrue(statementInfo.getSql(), "select u.id, u.name, i.name from User u left join Item i on (u.id = i.userId and i.type = ?) where u.name like ?".equals(statementInfo.getSql()));
    }
}
