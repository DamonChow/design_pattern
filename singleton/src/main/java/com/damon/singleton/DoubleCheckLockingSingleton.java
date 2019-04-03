package com.damon.singleton;

/**
 * 双检查锁的单例模式-线程安全
 */
public final class DoubleCheckLockingSingleton {

    private static volatile DoubleCheckLockingSingleton INSTANCE;

    private DoubleCheckLockingSingleton() {
    }

    public static DoubleCheckLockingSingleton getInstance() {
        // 第一次检查实例是否存在，如果存在即可返回，不存在则进入同步块
        if (null == INSTANCE) {
            // 同步块，线程安全
            synchronized (DoubleCheckLockingSingleton.class) {
                // 第二次检查实例是否存在，如果还不存在则会真正的创建实例
                if (null == INSTANCE) {
                    INSTANCE = new DoubleCheckLockingSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
