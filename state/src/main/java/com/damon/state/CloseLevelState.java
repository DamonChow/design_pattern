package com.damon.state;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：关闭
 *
 * @author Damon
 * @since 2018-12-26 10:12
 */
@Slf4j
public class CloseLevelState implements LevelState {

    @Override
    public void left(Context context) {
        LevelState levelState = new ForeLevelState();
        context.setLevelState(levelState);
        log.info("风扇左转到{}", levelState.info());
    }

    @Override
    public void right(Context context) {
        LevelState levelState = new OneLevelState();
        context.setLevelState(levelState);
        log.info("风扇右转到{}", levelState.info());
    }

    @Override
    public String info() {
        return "0档";
    }

}
