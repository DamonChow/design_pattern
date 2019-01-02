package com.damon.simple;

/**
 * 功能：客户端
 *
 * @author Damon
 * @since 2019-01-02 11:43
 */
public class Client {

    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver();
        Command leftCommand = new LeftCommand(receiver);
        Command rightCommand = new RightCommand(receiver);

        invoker.setCommand(rightCommand);
        invoker.execute();
        invoker.execute();
        invoker.execute();

        invoker.setCommand(leftCommand);
        invoker.execute();
        invoker.execute();
    }
}
