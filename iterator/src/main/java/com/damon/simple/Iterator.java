package com.damon.simple;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-02 17:10
 */
public interface Iterator<E> {

    boolean hasNext();

    E next();

}