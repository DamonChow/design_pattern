package com.damon.visitor;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能：具体元素-车轮
 *
 * @author Damon
 * @since 2019-01-07 16:16
 */
@Data
@AllArgsConstructor
public class Wheel implements Element {

    private String name;

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visit(this);
    }
}
