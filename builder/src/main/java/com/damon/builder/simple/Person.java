package com.damon.builder.simple;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-29 14:53
 */
public class Person {

    private String name;

    private int age;

    private List<String> children;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", children=" + children +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
