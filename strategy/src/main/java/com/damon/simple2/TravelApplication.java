package com.damon.simple2;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：出行
 *
 * @author Damon
 * @since 2018-12-20 11:01
 */
@Slf4j
public class TravelApplication {

    public static void main(String[] args) {
        Context context = new Context(new BusStrategy());
        context.executeStrategy("老王");

        context.setStrategy(new BicycleStrategy());
        context.executeStrategy("老张");

        context.setStrategy(new WalkStrategy());
        context.executeStrategy("老李");
    }
}