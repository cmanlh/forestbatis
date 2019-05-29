package com.lifeonwalden.forestbatis.util;

import java.util.function.Function;

public class SingletonRecordHandlerFactory {
    private static IndependentBeanSingletonFactory factory = new IndependentBeanSingletonFactory();

    /**
     * 针对类T的调用环境，返回类P的对应类T的唯一实例，如不存在，则通过调用creator创建一个并将其作为唯一实例
     *
     * @param clazz
     * @param creator
     * @param <T>
     * @param <P>
     * @return
     */
    public static <T, P> P getOrCreate(Class<T> clazz, Function<P, P> creator) {
        return factory.getOrCreate(clazz, creator);
    }
}