package com.damon.macro;

/**
 * 功能：抽象命令
 *
 * @author Damon
 * @since 2019-01-02 11:24
 */
@FunctionalInterface
public interface Command {

    void execute();
}
