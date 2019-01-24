

## 观察者模式

> Define a one-to-many dependency between objects where a state change in one object results in all its dependents being notified and updated automatically.

> 直译过来就是，定义对象间的一对多依赖关系，当一个对象的状态变更会自动通知和更新所有依赖项。像发布/订阅模式，事件通知模式，数据源/监听模式等都是性质一样。


## 观察者模式UML

### UML类和时序图

类图：Subject类不会直接更新从属对象的状态。相反，Subject引用了用于更新状态的观察者接口（update()），这使得Subject独立于依赖对象的状态更新方式。Observer1和Observer2类通过将状态与Subject的状态同步来实现Observer接口。

时序图：Observer1和Observer2对象调用Subject1上的attach(this)来注册自己。假如Subject1的状态发生变更，Subject1本身调用notify()。notify()对已注册的Observer1和Observer2对象调用update()，后者从Subject1请求已更改的数据（getState()）以更新（同步）其状态。

![img](https://ws4.sinaimg.cn/large/006tNc79gy1fzhh1ma2gjj30go06o0tv.jpg)



### UML类图



![img](https://ws3.sinaimg.cn/large/006tNc79gy1fzhh2k6pqzj320m0u0766.jpg)



## 观察者模式角色

 Subject（目标）：目标又称为主题，它是指被观察的对象。在目标中定义了一个观察者集合，一个观察目标可以接受任意数量的观察者来观察，它提供一系列方法来增加和删除观察者对象，同时它定义了通知方法notify()。目标类可以是接口，也可以是抽象类或具体类。 

 ConcreteSubject（具体目标）：具体目标是目标类的子类，通常它包含有经常发生改变的数据，当它的状态发生改变时，向它的各个观察者发出通知；同时它还实现了在目标类中定义的抽象业务逻辑方法（如果有的话）。如果无须扩展目标类，则具体目标类可以省略。 

 Observer（观察者）：观察者将对观察目标的改变做出反应，观察者一般定义为接口，该接口声明了更新数据的方法update()，因此又称为抽象观察者。 

 ConcreteObserver（具体观察者）：在具体观察者中维护一个指向具体目标对象的引用，它存储具体观察者的有关状态，这些状态需要和具体目标的状态保持一致；它实现了在抽象观察者Observer中定义的update()方法。

观察者模式描述了如何建立对象与对象之间的依赖关系，以及如何构造满足这种需求的系统。观察者模式包含观察目标和观察者两类对象，一个目标可以有任意数目的与之相依赖的观察者，一旦观察目标的状态发生改变，所有的观察者都将得到通知。作为对这个通知的响应，每个观察者都将监视观察目标的状态以使其状态与目标状态同步，这种交互也称为发布-订阅(Publish-Subscribe)。观察目标是通知的发布者，它发出通知时并不需要知道谁是它的观察者，可以有任意数目的观察者订阅它并接收通知。



## 干货源码解析

[源码地址](https://github.com/DamonChow/design_pattern/tree/master/observer)



博客订阅的功能，抽象主题中维护订阅关系，同时引入普通和vip观察者。

```java
//抽象主题
@Data
public abstract class Subject {

    //主题订阅者们
    private List<Observer> observerList = Lists.newArrayList();

    //订阅
    public void register(Observer observer) {
        observerList.add(observer);
    }

    //取消订阅
    public void remove(Observer observer) {
        observerList.remove(observer);
    }

    //发布东西
    public abstract void publish(String msg);
}


//抽象观察者
@Slf4j
@Data
@AllArgsConstructor
public abstract class Observer {

    //观察者名称
    private String name;

    //更新状态，由主题调度
    public void update(Object subject, Object args) {
        log.info("{}获取到变更通知：{}", name, args);
    }
}

//博客主题
@Slf4j
public class Blog extends Subject {

    @Override
    public void publish(String msg) {
        log.info("发布msg:{}", msg);
        //通知订阅者
        getObserverList().forEach(observer -> observer.update(this, msg));
    }
}

//普通用户观察者
@Slf4j
public class NormalObserver extends Observer {

    public NormalObserver(String name) {
        super(name);
    }

    @Override
    public void update(Object subject, Object args) {
        super.update(subject, args);
        log.info("{}获取到变更通知：普通用户可以不缓存", getName());
    }
}

//vip用户观察者
@Slf4j
public class VipObserver extends Observer {

    public VipObserver(String name) {
        super(name);
    }

    @Override
    public void update(Object subject, Object args) {
        super.update(subject, args);
        log.info("{}获取到变更通知：vip可以缓存", getName());
    }
}


@Slf4j
public class Application {

    public static void main(String[] args) {
        Blog blog = new Blog();
        VipObserver wang = new VipObserver("老王99");
        VipObserver lee = new VipObserver("老李");
        NormalObserver four = new NormalObserver("小四");
        NormalObserver twoEgg = new NormalObserver("二蛋");
        log.info("---------------------begin--------------------");

        // 用户订阅博客，普通和vip用户
        new Thread(() -> {
            blog.register(wang);
            sleep(2);
            blog.register(lee);
            sleep(2);
            blog.register(four);
        }).start();

        // 博客线程每隔2秒发布一次文章,  总共发布4次
        new Thread(() -> {
            IntStream.rangeClosed(1, 4).forEach(i -> {
                blog.publish(String.format("新把戏第%s次", i));
                sleep(2);
            });
        }).start();

        // 有用户退出订阅博客，也有二蛋加入订阅
        new Thread(() -> {
            sleep(3);
            blog.remove(lee);
            sleep(2);
            blog.register(twoEgg);
        }).start();
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error("error : ", e);
        }
    }
}
```



![image-20190124170206758](https://ws2.sinaimg.cn/large/006tNc79gy1fzhs6tl0jxj31s80qegxs.jpg)



jdk内置的Obverser和Observable

```java
@Slf4j
public class ObserverApplication {

    public static void main(String[] args) {
        log.info("Enter Text: ");
        EventSource eventSource = new EventSource();

        eventSource.addObserver((obj, arg) -> {
            log.info("Received response: {}", arg);
        });

        eventSource.addObserver((obj, arg) -> {
            log.info("Received response2: {},,{}", arg, obj
            );
        });

        new Thread(eventSource).start();
    }
}

class EventSource extends Observable implements Runnable {
    public void run() {
        while (true) {
            String response = new Scanner(System.in).next();
            setChanged();
            notifyObservers(response);
        }
    }
}
```





## Java中的使用

它使得一个对象可以灵活的将消息发送给感兴趣的对象

> java.util.EventListener
>
> javax.servlet.http.HttpSessionBindingListener
>
> javax.servlet.http.HttpSessionAttributeListener
>
> javax.faces.event.PhaseListener

Listener从名字上看就明白是监听的意思了。

### JDK中内置的Obverser和Observable

jdk中内置的观察者模式。即是java.util.Observer(接口)和java.util.Observable(类)。

简单的说说这个。观察者接口(java.util.Observer)，主题(java.util.Observable)。实现观察者接口和继承主题。通过抽象主题的addObserver()注册观察者，deleteObserver()移除观察者。

首先通过调用主题类的setChange()告知状态变更，随后调用notifyObservers方法（可传可不传参数）去通知观察者，最后因为主题在notifyObservers时会主动调用观察者的update()方法，改方法有2个参数，第一个为主题对象，第二个为可变参数。





## 总结

观察者模式是一种使用频率非常高的设计模式，JDK中就带有实现。无论是移动应用、Web应用或者桌面应用，观察者模式几乎无处不在，它为实现对象之间的联动提供了一套完整的解决方案，凡是涉及到一对一或者一对多的对象交互场景都可以使用观察者模式。观察者模式广泛应用于各种编程语言的GUI事件处理的实现，在基于事件的XML解析技术（如SAX2）以及Web事件处理中也都使用了观察者模式。

### 观察者模式的优点

(1) 观察者模式可以实现表示层和数据逻辑层的分离，定义了稳定的消息更新传递机制，并抽象了更新接口，使得可以有各种各样不同的表示层充当具体观察者角色。 
(2) 观察者模式在观察目标和观察者之间建立一个抽象的耦合。观察目标只需要维持一个抽象观察者的集合，无须了解其具体观察者。由于观察目标和观察者没有紧密地耦合在一起，因此它们可以属于不同的抽象化层次。 
(3) 观察者模式支持广播通信，观察目标会向所有已注册的观察者对象发送通知，简化了一对多系统设计的难度。 
(4) 观察者模式满足“开闭原则”的要求，增加新的具体观察者无须修改原有系统代码，在具体观察者与观察目标之间不存在关联关系的情况下，增加新的观察目标也很方便。

### 观察者模式的缺点

(1) 如果一个观察目标对象有很多直接和间接观察者，将所有的观察者都通知到会花费很多时间。 
(2) 如果在观察者和观察目标之间存在循环依赖，观察目标会触发它们之间进行循环调用，可能导致系统崩溃。 
(3) 观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。

### 注意事项

1、JAVA 中已经有了对观察者模式的支持类。 2、避免循环引用。 3、如果顺序执行，某一观察者错误会导致系统卡壳，一般采用异步方式。



## 参考

[Observer pattern](https://en.wikipedia.org/wiki/Observer_pattern)

[观察者模式|菜鸟教程](http://www.runoob.com/design-pattern/observer-pattern.html)

[细数JDK里的设计模式](https://www.cnblogs.com/tinyking/p/5938547.html)

[设计模式总结（Java）—— 观察者模式](https://www.cnblogs.com/renhui/p/6479748.html)