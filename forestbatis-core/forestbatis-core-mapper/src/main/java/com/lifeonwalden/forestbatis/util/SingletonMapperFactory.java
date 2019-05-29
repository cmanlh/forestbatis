package com.lifeonwalden.forestbatis.util;

import java.util.Optional;

public class SingletonMapperFactory {
    private static IndependentBeanSingletonFactory factory = new IndependentBeanSingletonFactory();

    /**
     * 针对类T的调用环境，返回类P的对应类T的唯一实例，如不存在，则将instance作为该唯一实例
     *
     * @param clazz
     * @param instance
     * @param <T>
     * @param <P>
     * @return
     */
    public static <T, P> P getOrCreate(Class<T> clazz, P instance) {
        return factory.getOrCreate(clazz, instance);
    }

    /**
     * 针对类T的调用环境，返回类P的对应类T的唯一实例,如不存在则返回Optional.Empty
     *
     * @param clazz
     * @param <T>
     * @param <P>
     * @return
     */
    public static <T, P> Optional<P> get(Class<T> clazz) {
        return factory.get(clazz);
    }
}
