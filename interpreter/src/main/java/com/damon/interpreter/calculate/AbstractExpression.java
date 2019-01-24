package com.damon.interpreter.calculate;

/**
 * 功能：抽象解释器
 *
 * @author Damon
 * @since 2019-01-10 14:38
 */
public abstract class AbstractExpression {

    public abstract int interpreter(Context context);
}
