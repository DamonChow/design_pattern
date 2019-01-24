package com.damon.visitor;

/**
 * 功能：抽象访问者
 *
 * @author Damon
 * @since 2019-01-07 16:10
 */
public interface ElementVisitor {

    void visit(Body body);

    void visit(Engine engine);

    void visit(Wheel wheel);

    void visit(Car car);
}
