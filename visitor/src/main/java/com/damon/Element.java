package com.damon;

/**
 * 功能：抽象元素-汽车元素
 *
 * @author Damon
 * @since 2019-01-07 16:09
 */
public interface Element {

    void accept(ElementVisitor visitor);
}
