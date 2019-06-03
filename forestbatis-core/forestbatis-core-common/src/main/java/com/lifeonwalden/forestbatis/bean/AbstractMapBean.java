package com.lifeonwalden.forestbatis.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapBean implements Map<String, Object>, BaseBean {
    private static final long serialVersionUID = 6260584915304293927L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractMapBean.class);

    protected Map<String, Object> dataMap = new HashMap<>();

    @Override
    public int size() {
        return this.dataMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.dataMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.dataMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.dataMap.containsKey(value);
    }

    @Override
    public Object get(Object key) {
        return this.dataMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return this.dataMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.dataMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.dataMap.putAll(m);
    }

    @Override
    public void clear() {
        this.dataMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.dataMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.dataMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.dataMap.entrySet();
    }

    public <T extends Map<String, Object>> T copyTo(Class<T> clazz) {
        T target;
        try {
            target = clazz.newInstance();
            for (Entry<String, Object> entry : this.entrySet()) {
                target.put(entry.getKey(), entry.getValue());
            }
        } catch (InstantiationException e) {
            logger.error("Failed to instance a new object.", e);

            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error("Unable to access object property.", e);

            throw new RuntimeException(e);
        }

        return target;
    }
}
