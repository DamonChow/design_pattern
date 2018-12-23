package com.damon.travel;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 功能：公交车
 *
 * @author Damon
 * @since 2018-12-23 15:42
 */
@Slf4j
public class BusHandler extends Handler {

    private double price = 59.99d;

    @Override
    public void handleRequest(String name, Double wallet) {
        if (price <= wallet) {
            log.info("{}身上的钱只能坐大巴。", name);
            return;
        }
        if (Objects.nonNull(next)) {
            next.handleRequest(name, wallet);
            return;
        }
        log.info("{}钱不够，只能徒步啦", name);
    }
}