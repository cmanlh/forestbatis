package com.lifeonwalden.forestbatis.example.bean;

import com.lifeonwalden.forestbatis.bean.AbstractParamMapBean;
import com.lifeonwalden.forestbatis.example.meta.UserMetaInfo;

import java.math.BigDecimal;
import java.util.Date;

public class UserVO extends AbstractParamMapBean {

    public String getId() {
        return (String) this.dataMap.get(UserMetaInfo.id);
    }

    public UserVO setId(String id) {
        this.dataMap.put(UserMetaInfo.id, id);

        return this;
    }

    public BigDecimal getIncome() {
        return (BigDecimal) this.dataMap.get(UserMetaInfo.income);
    }

    public UserVO setIncome(BigDecimal income) {
        this.dataMap.put(UserMetaInfo.income, income);

        return this;
    }

    public Integer getAge() {
        return (Integer) this.dataMap.get(UserMetaInfo.age);
    }

    public UserVO setAge(Integer age) {
        this.dataMap.put(UserMetaInfo.age, age);

        return this;
    }

    public Date getBirthday() {
        return (Date) this.dataMap.get(UserMetaInfo.birthday);
    }

    public UserVO setBirthday(Date birthday) {
        this.dataMap.put(UserMetaInfo.birthday, birthday);

        return this;
    }

    public Integer getSex() {
        return (Integer) this.dataMap.get(UserMetaInfo.sex);
    }

    public UserVO setSex(Integer sex) {
        this.dataMap.put(UserMetaInfo.sex, sex);

        return this;
    }
}
