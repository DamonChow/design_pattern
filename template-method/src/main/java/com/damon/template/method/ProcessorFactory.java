package com.damon.template.method;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：实现工厂工具
 *
 * @author Damon
 * @since 2019-03-25 17:53
 */
public class ProcessorFactory {

    private static Map<String, AbstractProcessor> factories = new HashMap();

    public static void putProcess(String key, AbstractProcessor process) {
        factories.put(key, process);
    }

    public static AbstractProcessor selectProcess(String key) {
        return factories.get(key);
    }

}
