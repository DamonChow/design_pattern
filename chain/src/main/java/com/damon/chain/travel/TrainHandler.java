package com.damon.chain.travel;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 功能：火车
 *
 * @author Damon
 * @since 2018-12-23 15:42
 */
@Slf4j
public class TrainHandler extends Handler {

    private double price = 149.99d;

    @Override
    public void handleRequest(String name, Double wallet) {
        if (price <= wallet) {
           log.info("{}身上的钱只能坐火车。", name);
           return;
        }
        if (Objects.nonNull(next)) {
            next.handleRequest(name, wallet);
            return;
        }
        log.info("{}钱不够，只能徒步啦", name);
    }
}