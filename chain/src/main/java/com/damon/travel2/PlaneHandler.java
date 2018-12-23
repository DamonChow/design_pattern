package com.damon.travel2;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：飞机
 *
 * @author Damon
 * @since 2018-12-23 15:42
 */
@Slf4j
public class PlaneHandler implements Handler {

    private double price = 280d;

    @Override
    public void handleRequest(String name, Double wallet, HandlerChain chain) {
        if (price <= wallet) {
            log.info("{}身上的钱可以坐飞机。", name);
            chain.reuse();
            return;
        }
        chain.handle(name, wallet);
    }
}