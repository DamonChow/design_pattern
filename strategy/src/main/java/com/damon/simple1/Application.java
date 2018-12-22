package com.damon.simple1;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-12-20 11:01
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Context context = new Context(new AddStrategy());
        log.info("10 + 5 = {}", context.executeStrategy(10, 5));

        context.setStrategy(new SubstractStrategy());
        log.info("10 - 5 = {}", context.executeStrategy(10, 5));

        context.setStrategy(new MultiplyStrategy());
        log.info("10 * 5 = {}", context.executeStrategy(10, 5));

        context.setStrategy(new DivideStrategy());
        log.info("10 / 5 = {}", context.executeStrategy(10, 5));
    }
}
