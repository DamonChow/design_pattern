package com.damon.interpreter.calculate;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-10 14:44
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Context context = new Context();
        context.addValue("a", 6);
        context.addValue("b", 9);
        context.addValue("c", 1);

        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Variable c = new Variable("c");

        AbstractExpression multiplyValue = new Multiply(a, b);
        AbstractExpression subtractValue = new Subtract(a, b);
        AbstractExpression addValue = new Add(subtractValue, c);
        AbstractExpression divisionValue = new Division(multiplyValue, addValue);

        log.info("{}", context.getValueMap());
        log.info("(a*b)/(a-b+c) = {}", divisionValue.interpreter(context));
    }
}
