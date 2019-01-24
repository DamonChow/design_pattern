package com.damon.interpreter.calculate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能：非终结表达式：除法
 *
 * @author Damon
 * @since 2019-01-10 14:39
 */
@Data
@AllArgsConstructor
public class Division extends AbstractExpression {

    private final AbstractExpression left;

    private final AbstractExpression right;

    @Override
    public int interpreter(Context context) {
        int right = this.right.interpreter(context);
        if (right != 0) {
            return left.interpreter(context) / right;
        }

        return -1;
    }
}
