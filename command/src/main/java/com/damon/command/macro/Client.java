package com.damon.command.macro;


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
        log.info("初始化ABC3个命令");
        Command aCommand = () -> log.info("A处理这个请求");
        invoker.addCommand(aCommand);
        invoker.addCommand(() -> log.info("B处理这个请求"));
        invoker.addCommand(() -> log.info("C处理这个请求"));
        invoker.execute();

        log.info("---------------------------");
        log.info("加入新命令D");
        invoker.addCommand(() -> log.info("D处理这个请求"));
        invoker.execute();

        log.info("---------------------------");
        log.info("加入新命令E");
        invoker.addCommand(() -> log.info("E处理这个请求"));
        invoker.execute();

        log.info("---------------------------");
        log.info("移除命令A");
        invoker.removeCommand(aCommand);
        invoker.execute();
    }
}
