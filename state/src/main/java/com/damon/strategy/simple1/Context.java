package com.damon.strategy.simple1;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能：开关
 *
 * @author Damon
 * @since 2018-12-26 10:09
 */
@Data
@AllArgsConstructor
public class Context {

    private LevelState levelState;

    public void left() {
        levelState.left(this);
    }

    public void right() {
        levelState.right(this);
    }

    public String info() {
        return levelState.info();
    }
}
