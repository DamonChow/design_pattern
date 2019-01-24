package com.damon.visitor;

/**
 * 功能：具体元素-车身
 *
 * @author Damon
 * @since 2019-01-07 16:16
 */
public class Body implements Element {

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visit(this);
    }
}
