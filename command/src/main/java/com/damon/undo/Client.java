package com.damon.undo;


import lombok.extern.slf4j.Slf4j;

/**
 * 功能：客户端
 *
 * @author Damon
 * @since 2019-01-02 11:43
 */
@Slf4j
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
        invoker.undo();
        invoker.undo();

        invoker.setCommand(leftCommand);
        invoker.execute();
        invoker.undo();
    }
}
