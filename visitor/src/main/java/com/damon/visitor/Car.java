package com.damon.visitor;

/**
 * 功能：具体元素-整车
 *
 * @author Damon
 * @since 2019-01-07 16:16
 */
public class Car implements Element {

    public void accept(final ElementVisitor visitor) {
        visitor.visit(this);
    }

}