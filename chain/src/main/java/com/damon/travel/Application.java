package com.damon.travel;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-12-23 21:42
 */
public class Application {

    public static void main(String[] args) {
        Handler planeHandler = new PlaneHandler();
        Handler trainHandler = new TrainHandler();
        Handler busHandler = new BusHandler();
        planeHandler.setNext(trainHandler);
        trainHandler.setNext(busHandler);

        planeHandler.handleRequest("老王", 40d);
        planeHandler.handleRequest("张三", 140d);
        planeHandler.handleRequest("李四", 240d);
        planeHandler.handleRequest("吴老五", 340d);
    }
}
