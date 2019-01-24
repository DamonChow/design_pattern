package com.damon.strategy.simple2;

import lombok.Data;

/**
 * 功能：策略上下文
 *
 * @author Damon
 * @since 2018-12-20 10:57
 */
@Data
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