package com.damon.memento;


import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-26 15:22
 */
@Slf4j
public class Originator {

    private String state;

    //状态更改
    public void set(String state) {
        this.state = state;
        log.info("Originator: Setting state to {}", state);
    }

    //将状态保存到备忘录里
    public Memento saveToMemento() {
        log.info("Originator: Saving to Memento.");
        return new Memento(this.state);
    }

    //从备忘录里取出状态并回滚
    public void restoreFromMemento(Memento memento) {
        this.state = memento.getState();
        log.info("Originator: State after restoring from Memento: {}", state);
    }
}
