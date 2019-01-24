package com.damon.strategy.simple1;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-12-26 10:06
 */
public interface LevelState {

    /**
     * 左转
     *
     * @param context
     */
    void left(Context context);

    /**
     * 右转
     *
     * @param context
     */
    void right(Context context);

    /**
     * 当前档位
     * @return
     */
    String info();
}
