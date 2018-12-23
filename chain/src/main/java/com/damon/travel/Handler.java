package com.damon.travel;

import lombok.Data;

/**
 * 功能：责任链-链节点
 *
 * @author Damon
 * @since 2018-12-22 19:29
 */
@Data
public abstract class Handler {

    /**
     * 下一个链节点
     */
    protected Handler next;

    public abstract void handleRequest(String name, Double wallet);
}
