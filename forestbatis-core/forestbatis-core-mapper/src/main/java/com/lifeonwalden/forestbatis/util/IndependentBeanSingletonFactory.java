package com.lifeonwalden.forestbatis.util;

import java.util.concurrent.ConcurrentHashMap;

public class IndependentBeanSingletonFactory {
    private static ConcurrentHashMap<Class, Object> instanceCache = new ConcurrentHashMap<>();

    public static <T> T getSingleton(Class<T> clazz) {
        try {
            Object object = instanceCache.get(clazz);

            if (null != object) {
                return (T) object;
            }

            instanceCache.putIfAbsent(clazz, clazz.newInstance());
            return (T) instanceCache.get(clazz);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
