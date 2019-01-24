package com.damon.strategy.simple1;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：一档
 *
 * @author Damon
 * @since 2018-12-26 10:12
 */
@Slf4j
public class OneLevelState implements LevelState {

    @Override
    public void left(Context context) {
        LevelState levelState = new CloseLevelState();
        context.setLevelState(levelState);
        log.info("风扇左转到{}", levelState.info());
    }

    @Override
    public void right(Context context) {
        LevelState levelState = new TwoLevelState();
        context.setLevelState(levelState);
        log.info("风扇右转到{}", levelState.info());
    }

    @Override
    public String info() {
        return "1档";
    }
}
