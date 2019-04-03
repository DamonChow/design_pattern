package com.damon.builder.normal;

import com.google.common.collect.Lists;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-29 14:59
 */
public class PersonTwoBuilder extends AbstractPersonBuilder {

    public void buildName() {
        product.setName("老two");
    }

    public void buildAge() {
        product.setAge(55);
    }

    public void buildChildren() {
        product.setChildren(Lists.newArrayList("小two"));
    }

}