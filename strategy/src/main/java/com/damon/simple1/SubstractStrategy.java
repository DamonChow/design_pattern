package com.damon.simple1;

/**
 * 功能：具体策略-减法策略
 *
 * @author Damon
 * @since 2018-12-20 10:54
 */
public class SubstractStrategy implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}
