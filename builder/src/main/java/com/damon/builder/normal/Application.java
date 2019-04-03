package com.damon.builder.normal;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-29 14:50
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Director director = new Director(new PersonOneBuilder());
        Person person = director.construct();
        log.info("person的信息：{}", person);

        director.setBuilder(new PersonTwoBuilder());
        person = director.construct();
        log.info("person的信息：{}", person);
    }
}
