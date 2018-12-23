package com.damon.travel2;

/**
 * 功能：抽象处理者
 *
 * @author zhoujiwei
 * @since 2018-12-23 21:54
 */
public interface Handler {

    void handleRequest(String name, Double wallet, HandlerChain chain);
}
