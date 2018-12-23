package com.damon.travel2;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：大巴
 *
 * @author Damon
 * @since 2018-12-23 15:42
 */
@Slf4j
public class BusHandler implements Handler {

    private double price = 59.99d;

    @Override
    public void handleRequest(String name, Double wallet, HandlerChain chain) {
        if (price <= wallet) {
            log.info("{}身上的钱只能坐大巴。", name);
            chain.reuse();
            return;
        }
        chain.handle(name, wallet);
    }
}