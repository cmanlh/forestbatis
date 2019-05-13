package com.lifeonwalden.forestbatis.result;

/**
 * 针对查询返回数据量特别大时，使用该类对结果进行流式处理。即一条一条处理数据，而不是一次性将所有结果装载进内存
 */
public interface StreamResultSetCallback<P> {
    /**
     * 如返回false, 则中止处理
     * @param value
     * @return
     */
    boolean process(P value);
}
