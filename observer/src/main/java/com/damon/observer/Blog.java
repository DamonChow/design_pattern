package com.damon.observer;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：博客主题
 *
 * @author Damon
 * @since 2019-01-24 15:03
 */
@Slf4j
public class Blog extends Subject {

    @Override
    public void publish(String msg) {
        log.info("发布msg:{}", msg);
        //通知订阅者
        getObserverList().forEach(observer -> observer.update(this, msg));
    }
}
