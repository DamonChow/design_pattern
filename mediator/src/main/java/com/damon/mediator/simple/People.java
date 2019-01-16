package com.damon.mediator.simple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：人(抽象同事)
 *
 * @author Damon
 * @since 2019-01-16 11:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class People {

    private Mediator mediator;

    private String name;

    /**
     * 向中介发送消息
     */
    protected abstract void sendMessage(Message message);

    /**
     * 从中介获取消息
     */
    protected abstract void getMessage(Message message);
}
