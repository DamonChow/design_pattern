package com.damon.strategy.simple1;

/**
 * 功能：抽象操作策略
 *
 * @author Damon
 * @since 2018-12-20 10:52
 */
public interface Strategy {

    /**
     * 处理2个int数字
     *
     * @param num1
     * @param num2
     * @return
     */
    int doOperation(int num1, int num2);
}
