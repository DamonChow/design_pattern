package com.damon.mediator.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：房东（具体同事实现）
 *
 * @author Damon
 * @since 2019-01-16 14:12
 */
@Slf4j
public class Landlord extends People {

    public Landlord(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    protected void sendMessage(Message message) {
        getMediator().operation(this, message);
    }

    @Override
    protected void getMessage(Message message) {
        log.info("房东|{}|从中介获取到租户的信息：{}", getName(), message);
    }
}
