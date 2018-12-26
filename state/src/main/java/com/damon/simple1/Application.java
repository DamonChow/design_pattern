package com.damon.simple1;

/**
 * 功能：开关管理器
 *
 * @author Damon
 * @since 2018-12-26 10:22
 */
public class Application {

    public static void main(String[] args) {
        Context context = new Context(new CloseLevelState());
        context.right();
        context.right();
        context.right();
        context.left();
        context.right();
        context.right();
    }
}
