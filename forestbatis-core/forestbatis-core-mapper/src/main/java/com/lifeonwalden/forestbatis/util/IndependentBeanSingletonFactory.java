package com.lifeonwalden.forestbatis.util;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 单列工厂类
 */
public class IndependentBeanSingletonFactory {
    private ConcurrentHashMap<Class, Object> instanceCache = new ConcurrentHashMap<>();

    /**
     * 返回类T的单例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getOrCreate(Class<T> clazz) {
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

    /**
     * 针对类T的调用环境，返回类P的对应类T的唯一实例，如不存在，则通过调用creator创建一个并将其作为唯一实例
     *
     * @param clazz
     * @param creator
     * @param <T>
     * @param <P>
     * @return
     */
    public <T, P> P getOrCreate(Class<T> clazz, Function<P, P> creator) {
        Object object = instanceCache.get(clazz);
        if (null != object) {
            return (P) object;
        }
        instanceCache.putIfAbsent(clazz, creator.apply(null));

        return (P) instanceCache.get(clazz);
    }

    /**
     * 针对类T的调用环境，返回类P的对应类T的唯一实例，如不存在，则将instance作为该唯一实例
     *
     * @param clazz
     * @param newInstance
     * @param <T>
     * @param <P>
     * @return
     */
    public <T, P> P getOrCreate(Class<T> clazz, P newInstance) {
        Object object = instanceCache.get(clazz);
        if (null != object) {
            return (P) object;
        }
        instanceCache.putIfAbsent(clazz, newInstance);

        return (P) instanceCache.get(clazz);
    }

    /**
     * 针对类T的调用环境，返回类P的对应类T的唯一实例,如不存在则返回Optional.Empty
     *
     * @param clazz
     * @param <T>
     * @param <P>
     * @return
     */
    public <T, P> Optional<P> get(Class<T> clazz) {
        Object object = instanceCache.get(clazz);
        if (null != object) {
            return Optional.of((P) object);
        } else {
            return Optional.empty();
        }
    }
}
