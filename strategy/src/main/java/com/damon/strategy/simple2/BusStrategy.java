package com.damon.strategy.simple2;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：具体策略-公交出行
 *
 * @author Damon
 * @since 2018-12-20 10:54
 */
@Slf4j
public class BusStrategy implements Strategy {

    @Override
    public void travel(String name) {
        log.info("{}选择公交出行。", name);
    }
}
