package com.damon.normal;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-29 14:59
 */
public abstract class AbstractPersonBuilder {

    protected Person product = new Person();

    public abstract void buildName();

    public abstract void buildAge();

    public abstract void buildChildren();

    public Person build() {

        return product;
    }
}
