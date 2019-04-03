package com.damon.builder.normal;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-29 15:03
 */
public class Director {

    private AbstractPersonBuilder builder;

    public Director(AbstractPersonBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(AbstractPersonBuilder builder) {
        this.builder = builder;
    }

    public Person construct() {
        builder.buildName();
        builder.buildAge();
        builder.buildChildren();
        return builder.build();
    }
}
