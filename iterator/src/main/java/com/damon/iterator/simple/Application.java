package com.damon.iterator.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-02 17:31
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Aggregate<Integer> aggregate = new ConcreteAggregate<>();
        aggregate.add(1);
        aggregate.add(2);
        aggregate.add(3);
        aggregate.add(4);

        Iterator<Integer> iterator = aggregate.iterator();
        while (iterator.hasNext()) {
            log.info("循环数据{}", iterator.next());
        }
    }
}
