package com.damon.interpreter.calculate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能：终结表达式：变量
 *
 * @author Damon
 * @since 2019-01-10 14:39
 */
@Data
@AllArgsConstructor
public class Variable extends AbstractExpression {

    private final String key;

    @Override
    public int interpreter(Context context) {
        return context.getValue(key);
    }
}
