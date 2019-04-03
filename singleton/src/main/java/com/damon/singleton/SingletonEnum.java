package com.damon.singleton;

/**
 * 采用枚举类型的单例模式
 */
public enum SingletonEnum {

    INSTANCE;

    @Override
    public String toString() {
        return getDeclaringClass().getCanonicalName() + "@" + hashCode();
    }

    public void something(){
        //do something...
    }

}
