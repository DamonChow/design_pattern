package com.damon.calculate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能：非终结表达式：减法
 *
 * @author Damon
 * @since 2019-01-10 14:39
 */
@Data
@AllArgsConstructor
public class Subtract extends AbstractExpression {

    private final AbstractExpression left;

    private final AbstractExpression right;

    @Override
    public int interpreter(Context context) {
        return left.interpreter(context) - right.interpreter(context);
    }
}
