package com.damon.command.simple;

import com.damon.strategy.simple1.CloseLevelState;
import com.damon.strategy.simple1.Context;

/**
 * 功能：接收方
 *
 * @author Damon
 * @since 2019-01-02 11:30
 */
public class Receiver {

    private Context context = new Context(new CloseLevelState());

    public void left() {
        context.left();
    }

    public void right() {
        context.right();
    }
}
