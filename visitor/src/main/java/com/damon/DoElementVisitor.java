package com.damon;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：具体的一个访问者，纯打印
 *
 * @author Damon
 * @since 2019-01-07 16:27
 */
@Slf4j
public class DoElementVisitor implements ElementVisitor {

    @Override
    public void visit(Body body) {
        log.info("Moving my body");
    }

    @Override
    public void visit(Engine engine) {
        log.info("Starting my engine");
    }

    @Override
    public void visit(Wheel wheel) {
        log.info("Kicking my " + wheel.getName() + " wheel");
    }

    @Override
    public void visit(Car car) {
        log.info("Starting my car");
    }
}
