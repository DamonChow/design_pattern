package com.damon.observer;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-24 14:58
 */
@Data
public abstract class Subject {

    //主题订阅者们
    private List<Observer> observerList = Lists.newArrayList();

    //订阅
    public void register(Observer observer) {
        observerList.add(observer);
    }

    //取消订阅
    public void remove(Observer observer) {
        observerList.remove(observer);
    }

    //发布东西
    public abstract void publish(String msg);
}
