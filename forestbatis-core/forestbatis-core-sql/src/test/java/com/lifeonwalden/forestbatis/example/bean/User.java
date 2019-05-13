package com.lifeonwalden.forestbatis.example.bean;

import com.lifeonwalden.forestbatis.bean.AbstractMapBean;
import com.lifeonwalden.forestbatis.example.meta.UserTableInfo;

import java.util.Date;

public class User extends AbstractMapBean {
    public String getId() {
        return (String) this.dataMap.get(UserTableInfo.id.getName());
    }

    public User setId(String id) {
        this.dataMap.put(UserTableInfo.id.getName(), id);

        return this;
    }

    public String getName() {
        return (String) this.dataMap.get(UserTableInfo.name.getName());
    }

    public User setName(String name) {
        this.dataMap.put(UserTableInfo.name.getName(), name);

        return this;
    }

    public Integer getAge() {
        return (Integer) this.dataMap.get(UserTableInfo.age.getName());
    }

    public User setAge(Integer age) {
        this.dataMap.put(UserTableInfo.age.getName(), age);

        return this;
    }

    public Date getBirthday() {
        return (Date) this.dataMap.get(UserTableInfo.birthday.getName());
    }

    public User setBirthday(Date birthday) {
        this.dataMap.put(UserTableInfo.birthday.getName(), birthday);

        return this;
    }

    public Integer getSex() {
        return (Integer) this.dataMap.get(UserTableInfo.sex.getName());
    }

    public User setSex(Integer sex) {
        this.dataMap.put(UserTableInfo.sex.getName(), sex);

        return this;
    }
}
