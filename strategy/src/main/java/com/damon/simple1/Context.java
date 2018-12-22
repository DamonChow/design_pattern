package com.damon.simple1;

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
     * 执行策略
     *
     * @param num1
     * @param num2
     * @return
     */
    public int executeStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }

}