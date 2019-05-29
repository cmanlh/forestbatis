package com.lifeonwalden.forestbatis.util;

public class SingletonParameterHandlerFactory {
    private static IndependentBeanSingletonFactory factory = new IndependentBeanSingletonFactory();

    /**
     * 返回类T的单例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getOrCreate(Class<T> clazz) {
        return factory.getOrCreate(clazz);
    }
}
