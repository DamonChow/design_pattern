package com.damon.simple;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-02 17:11
 */
public interface Aggregate<T> {

    Iterator<T> iterator();

    void add(T t);

}