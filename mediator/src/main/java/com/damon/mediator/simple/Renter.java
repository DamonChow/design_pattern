package com.damon.mediator.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：租户（具体同事实现）
 *
 * @author Damon
 * @since 2019-01-16 14:14
 */
@Slf4j
public class Renter extends People {

    public Renter(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    protected void sendMessage(Message message) {
        getMediator().operation(this, message);
    }

    @Override
    protected void getMessage(Message message) {
        log.info("租户|{}|从中介获取到房东的信息：{}", getName(), message);
    }
}
