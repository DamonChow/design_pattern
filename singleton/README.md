---
title: 折腾Java设计模式之单例模式
copyright: true
comment: true
date: 2019-04-02 17:22:03
categories: 设计模式
tags: 设计模式
---

## 单例模式

>Ensure a class has only one instance, and provide a global point of access to it.
>
>一个类仅仅只有一个实例，并且提供全局的接入点。简洁点理解就是涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问它自己唯一的对象的方式，可以直接访问，不需要实例化该类的对象。


## 饿汉式单例模式

```java
public final class EagerSingleton {

    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
```

基于 classloader 机制避免了多线程的同步问题，不过`INSTANCE`在类装载时就实例化，虽然导致类装载的原因有很多种，在单例模式中大多数都是调用 `getInstance` 方法。类在加载时就初始化了，会浪费空间，因为不管你用还是不用，它都创建出来了，但是因为没有加锁，执行效率较高。



## 懒汉式单例模式

```java
/**
 * 懒汉式的单例模式-线程不安全的
 */
public final class LazyThreadNotSafeSingleton {

    private static LazyThreadNotSafeSingleton INSTANCE;

    private LazyThreadNotSafeSingleton() {
    }

    public static LazyThreadNotSafeSingleton getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new LazyThreadNotSafeSingleton();
        }
        return INSTANCE;
    }
}
```

```java
/**
 * 懒汉式的单例模式-线程安全的
 */
public final class LazyThreadSafeSingleton {

    private static LazyThreadSafeSingleton INSTANCE;

    private LazyThreadSafeSingleton() {
    }

    public static synchronized LazyThreadSafeSingleton getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new LazyThreadSafeSingleton();
        }
        return INSTANCE;
    }
}
```

有上述两种懒汉式单例模式，区别在与静态工厂方法`getInstance`是否加了`synchronized`修饰来进行同步，用于支持线程安全。懒汉式，在其加载对象的时候是不会创建对象实例的，只有等它真正使用的时候才会创建，如果一直没有使用则一直不会创建，能够避免内存浪费，也就是只有第一次调用的时候才会创建。但是加锁`synchronized`就影响了性能和效率，导致`getInstance`方法的性能受影响，此种方式也不推荐。寻找一种既能线程安全又可以延迟加载的方式。



## 双检查锁的单例模式

```java
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
```

双检查加锁的方式，能实现线程安全，又能减少性能的影响。双检查加锁，旨在每次调用`getInstance`方法都需要同步，但是先不会同步，在第一次判断实例是否存在后，如果不存在才进入同步块，进入同步块后，第二次检查实例是否存在，如果不存在，在同步块内创建实例。如此只有首次才会同步，从而减少了多次在同步情况下进行判断所浪费的时间。双检查加锁机制的实现会使用关键字volatile，它的意思是：被volatile修饰的变量的值，将不会被本地线程缓存，所有对该变量的读写都是直接操作共享内存，从而确保多个线程能正确的处理该变量。但是实现过程稍微复杂点。



## 静态内部类Holder式单例模式

```java
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
```

当`getInstance`方法第一次被调用的时候，它第一次读取`InstanceHolder.INSTANCE`，导致`InstanceHolder`类得到初始化；而这个类在装载并被初始化的时候，会初始化它的静态域，从而创建Singleton的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，并由虚拟机来保证它的线程安全性。这个模式的优势在于，`getInstance`方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本。其中使用到类的静态内部类和多线程缺省同步锁。

**静态内部类**

> 静态内部类指，有static修饰的成员式内部类。如果没有static修饰的成员式内部类被称为对象级内部类。类级内部类相当于其外部类的static成分，它的对象与外部类对象间不存在依赖关系，因此可直接创建。而对象级内部类的实例，是绑定在外部对象实例中的。静态内部类中，可以定义静态的方法。在静态方法中只能够引用外部类中的静态成员方法或者成员变量。静态内部类相当于其外部类的成员，只有在第一次被使用的时候才被会装载。

**多线程缺省同步锁**

> 在多线程开发中，为解决并发问题，主要是通过使用synchronized来加互斥锁进行同步控制。但是在某些情况中，JVM已经隐含地为您执行了同步，这些情况下就不用自己再来进行同步控制了。这些情况包括：
>
> 1.由静态初始化器（在静态字段上或static{}块中的初始化器）初始化数据时；
>
> 2.访问final字段时；
>
> 3.在创建线程之前创建对象时；
>
> 4.线程可以看见它将要处理的对象时



## 枚举类型的单例模式

```java
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
```

简洁，自动支持序列化机制，绝对防止多次实例化。《高效Java 第二版》中的说法：单元素的枚举类型已经成为实现Singleton的最佳方法。用枚举来实现单例非常简单，只需要编写一个包含单个元素的枚举类型即可。



## 总结

不建议使用懒汉式，简单的阔以使用饿汉式。涉及到反序列化创建对象时阔以使用枚举方式。如果考虑到延迟加载 的话，阔以采用静态内部类Holder的模式。如果对业务需求有特殊要求的时候阔以采用双检查锁的单例。



## 参考

[源码地址](https://github.com/DamonChow/design_pattern/tree/master/singleton)

[单例模式|菜鸟教程](http://www.runoob.com/design-pattern/singleton-pattern.html)

[《JAVA与模式》之单例模式](https://www.cnblogs.com/java-my-life/archive/2012/03/31/2425631.html)