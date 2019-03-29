package com.damon.normal;

import com.google.common.collect.Lists;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-29 14:59
 */
public class PersonOneBuilder extends AbstractPersonBuilder {

    public void buildName() {
        product.setName("老one");
    }

    public void buildAge() {
        product.setAge(44);
    }

    public void buildChildren() {
        product.setChildren(Lists.newArrayList("小one"));
    }

}