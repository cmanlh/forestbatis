package com.lifeonwalden.forestbatis.bean;

import java.math.BigDecimal;
import java.util.Date;

public abstract class AbstractParamMapBean extends AbstractMapBean {
    private static final long serialVersionUID = -1955185676552771905L;

    protected Class<?> _getType(String key) {
        return null;
    }

    @Override
    public Object put(String key, Object value) {
        if (null == value) {
            return dataMap.remove(key);
        }
        Class<?> clazz = _getType(key);
        Object _value = null;
        if (null == clazz || clazz.isInstance(value)) {
            _value = value;
        } else if (String.class.isInstance(value)) {
            if (Integer.class.equals(clazz)) {
                _value = Integer.parseInt((String) value);
            } else if (BigDecimal.class.equals(clazz)) {
                _value = new BigDecimal((String) value);
            } else if (Boolean.class.equals(clazz)) {
                _value = new Boolean((String) value);
            } else if (Date.class.equals(clazz)) {
                _value = new Date(Long.parseLong((String) value));
            } else if (Long.class.equals(clazz)) {
                _value = Long.parseLong((String) value);
            } else {
                throw new RuntimeException("Invalid data format : " + key);
            }
        } else {
            throw new RuntimeException("Invalid data format : " + key);
        }
        return dataMap.put(key, _value);
    }
}
