package com.damon.builder.simple;

import com.google.common.collect.Lists;
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
        Person wang = PersonBuilder.builder()
                .withAge(40)
                .withName("老王")
                .withChildren(Lists.newArrayList("李一一", "吴老三"))
                .build();
        log.info("老王的信息：{}", wang);
    }
}
