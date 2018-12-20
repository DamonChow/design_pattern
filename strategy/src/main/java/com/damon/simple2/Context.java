package com.damon.simple2;

/**
 * 功能：策略上下文
 *
 * @author Damon
 * @since 2018-12-20 10:57
 */
public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 出行
     *
     * @return
     */
    public void executeStrategy(String name) {
        strategy.travel(name);
    }

}