package com.lifeonwalden.forestbatis.biz.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AbstractParamMapBean extends AbstractMapBean {
    private static final long serialVersionUID = 2825637947183339992L;

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
        Class<?> valueClazz = value.getClass();
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
        } else if (valueClazz.equals(Long.class)) {
            if (Date.class.equals(clazz)) {
                _value = new Date((long) value);
            } else if (BigDecimal.class.equals(clazz)) {
                _value = BigDecimal.valueOf((long) value);
            } else {
                _value = value;
            }
        } else if (valueClazz.equals(Double.class)) {
            if (BigDecimal.class.equals(clazz)) {
                _value = BigDecimal.valueOf((double) value);
            } else {
                _value = value;
            }
        } else if (valueClazz.equals(Float.class)) {
            if (BigDecimal.class.equals(clazz)) {
                _value = BigDecimal.valueOf((float) value);
            } else {
                _value = value;
            }
        } else if (valueClazz.equals(Integer.class)) {
            if (BigDecimal.class.equals(clazz)) {
                _value = BigDecimal.valueOf((int) value);
            } else {
                _value = value;
            }
        } else if (List.class.isInstance(value)) {
            if (AbstractParamMapBean.class.equals(clazz.getSuperclass())) {
                List _list = (List) value;
                List list = new ArrayList();
                _list.forEach(item -> list.add(_move((Map<String, Object>) item, clazz)));
                _value = list;
            }
        } else {
            throw new RuntimeException("Invalid data format : " + key);
        }
        return dataMap.put(key, _value);
    }

    private Object _move(Map<String, Object> value, Class<?> clazz) {
        Map<String, Object> target;
        try {
            target = (Map<String, Object>) clazz.newInstance();
            for (Entry<String, Object> entry : value.entrySet()) {
                target.put(entry.getKey(), entry.getValue());
            }
        } catch (InstantiationException e) {

            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);
        }

        return target;
    }
}
