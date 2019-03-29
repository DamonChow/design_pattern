package com.damon.simple;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-29 14:53
 */
public final class PersonBuilder {
    private String name;
    private int age;
    private List<String> children;

    private PersonBuilder() {
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public PersonBuilder withChildren(List<String> children) {
        this.children = children;
        return this;
    }

    public Person build() {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setChildren(children);
        return person;
    }
}
