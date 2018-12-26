package com.damon.simple1;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：2档
 *
 * @author Damon
 * @since 2018-12-26 10:12
 */
@Slf4j
public class TwoLevelState implements LevelState {

    @Override
    public void left(Context context) {
        LevelState levelState = new OneLevelState();
        context.setLevelState(levelState);
        log.info("风扇左转到{}", levelState.info());
    }

    @Override
    public void right(Context context) {
        LevelState levelState = new ThreeLevelState();
        context.setLevelState(levelState);
        log.info("风扇右转到{}", levelState.info());
    }

    @Override
    public String info() {
        return "2档";
    }
}
