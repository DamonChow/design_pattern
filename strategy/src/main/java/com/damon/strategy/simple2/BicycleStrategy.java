package com.damon.strategy.simple2;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：具体策略-单车出行
 *
 * @author Damon
 * @since 2018-12-20 10:54
 */
@Slf4j
public class BicycleStrategy implements Strategy {

    @Override
    public void travel(String name) {
        log.info("{}选择单车出行。", name);
    }
}
