package com.damon.observer;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：普通用户
 *
 * @author Damon
 * @since 2019-01-24 14:53
 */
@Slf4j
public class NormalObserver extends Observer {

    public NormalObserver(String name) {
        super(name);
    }

    @Override
    public void update(Object subject, Object args) {
        super.update(subject, args);
        log.info("{}获取到变更通知：普通用户可以不缓存", getName());
    }
}
