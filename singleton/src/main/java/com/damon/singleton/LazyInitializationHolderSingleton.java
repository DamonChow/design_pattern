package com.damon.singleton;

/**
 * 静态内部类Holder式单例
 *
 * 延迟加载和线程安全
 */
public final class LazyInitializationHolderSingleton {

    private LazyInitializationHolderSingleton() {
    }

    public static LazyInitializationHolderSingleton getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 延迟加载
     */
    private static class InstanceHolder {

        private static final LazyInitializationHolderSingleton INSTANCE = new LazyInitializationHolderSingleton();
    }
}
