package com.damon.simple1;

/**
 * 功能：具体策略-乘法策略
 *
 * @author Damon
 * @since 2018-12-20 10:55
 */
public class MultiplyStrategy implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}
