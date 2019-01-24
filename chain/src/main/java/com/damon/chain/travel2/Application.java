package com.damon.chain.travel2;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-12-23 21:42
 */
public class Application {

    public static void main(String[] args) {
        HandlerChain chain = new HandlerChain();
        Handler planeHandler = new PlaneHandler();
        Handler trainHandler = new TrainHandler();
        Handler busHandler = new BusHandler();
        chain.addHandler(planeHandler);
        chain.addHandler(trainHandler);
        chain.addHandler(busHandler);

        chain.handle("老王", 40d);
        chain.handle("张三", 140d);
        chain.handle("李四", 240d);
        chain.handle("吴老五", 340d);
    }
}
