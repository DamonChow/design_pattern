package com.damon;

/**
 * 功能：具体元素-引擎
 *
 * @author Damon
 * @since 2019-01-07 16:16
 */
public class Engine implements Element {

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visit(this);
    }
}
