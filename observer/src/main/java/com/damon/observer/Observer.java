package com.damon.observer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能：抽象观察者
 *
 * @author Damon
 * @since 2019-01-24 14:50
 */
//抽象观察者
@Slf4j
@Data
@AllArgsConstructor
public abstract class Observer {

    //观察者名称
    private String name;

    //更新状态，由主题调度
    public void update(Object subject, Object args) {
        log.info("{}获取到变更通知：{}", name, args);
    }
}
