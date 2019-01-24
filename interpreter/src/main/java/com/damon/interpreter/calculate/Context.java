package com.damon.interpreter.calculate;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * 功能：环境上下文
 *
 * @author Damon
 * @since 2019-01-10 14:38
 */
@Getter
public class Context {

    private final Map<String, Integer> valueMap = Maps.newHashMap();

    public void addValue(final String key, final int value) {
        valueMap.put(key, Integer.valueOf(value));
    }

    public int getValue(final String key) {
        return valueMap.get(key).intValue();
    }
}
