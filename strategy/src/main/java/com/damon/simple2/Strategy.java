package com.damon.simple2;

/**
 * 功能：抽象出现策略
 *
 * @author Damon
 * @since 2018-12-20 10:52
 */
public interface Strategy {

    /**
     * 出现方法
     *
     * @return
     */
    void travel(String name);
}
