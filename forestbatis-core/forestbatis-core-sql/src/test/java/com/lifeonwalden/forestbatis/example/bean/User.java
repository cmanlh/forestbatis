package com.lifeonwalden.forestbatis.example.bean;

import java.util.Date;
import java.util.HashMap;

public class User extends HashMap {
    private String id;

    private String name;

    private Integer age;

    private Date birthday;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;

        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }
}
