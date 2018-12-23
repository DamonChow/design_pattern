### 责任链模式

> 顾名思义，责任链模式（Chain of Responsibility Pattern）为请求创建了一个接收者对象的链。这种模式给予请求的类型，对请求的发送者和接收者进行解耦。这种类型的设计模式属于行为型模式。
>
> 在这种模式中，通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。

<!--more-->

### 简介

**意图**  避免请求发送者与接收者耦合在一起，让多个对象都有可能接收请求，将这些对象连接成一条链，并且沿着这条链传递请求，直到有对象处理它为止。

**主要解决**  职责链上的处理者负责处理请求，客户只需要将请求发送到职责链上即可，无须关心请求的处理细节和请求的传递，所以职责链将请求的发送者和请求的处理者解耦了。

**何时使用**  在处理消息的时候以过滤很多道。

**如何解决**  拦截的类都实现统一接口。

**关键代码**  Handler 里面聚合它自己，在 handleRequest 里判断是否合适，如果没达到条件则向下传递。

**纯责任链与不纯责任链**

- 纯：纯责任链中的节点只有两种行为，一处理责任，二将责任传递到下一个节点。不允许出现某一个节点处理部分或全部责任后又将责任向下传递的情况。
- 不纯：允许某个请求被一个节点处理部分责任后再向下传递，或者处理完后其后续节点可以继续处理该责任，而且一个责任可以最终不被任何节点所处理。

**主要角色**

> - Handler（抽象处理者）： 定义一个处理请求的接口，提供对后续处理者的引用
>
> - ConcreteHandler（具体处理者）： 抽象处理者的子类，处理用户请求，可选将请求处理掉还是传给下家；在具体处理者中可以访问链中下一个对象，以便请求的转发

**应用实例** 

> 1、红楼梦中的"击鼓传花"。 
>
> 2、JS 中的事件冒泡。 
>
> 3、JAVA WEB 中 Apache Tomcat 对 Encoding 的处理，Struts2 的拦截器，jsp servlet 的 Filter。

**优点**  

> 1、降低耦合度。它将请求的发送者和接收者解耦。 
>
> 2、简化了对象。使得对象不需要知道链的结构。
>
> 3、增强给对象指派职责的灵活性。通过改变链内的成员或者调动它们的次序，允许动态地新增或者删除责任。 
>
> 4、增加新的请求处理类很方便。

**缺点** 

>  1、不能保证请求一定被接收。
>
>  2、系统性能将受到一定影响，而且在进行代码调试时不太方便，可能会造成循环调用。
>
>  3、可能不容易观察运行时的特征，有碍于除错。

**使用场景**  

> 1、有多个对象可以处理同一个请求，具体哪个对象处理该请求由运行时刻自动确定。 
>
> 2、在不明确指定接收者的情况下，向多个对象中的一个提交一个请求。 
>
> 3、可动态指定一组对象处理请求。

### Github项目描述

[跳转到我的责任链设计模式源码](https://github.com/DamonChow/design_pattern/tree/master/chain)

#### 1.出行方式

travel包里主要对出行方式的责任链模式。跟进用户身上的钱，在优先级如飞机->火车->大巴的顺序下选择对应的出行模式。

~~~java
public class Application {

    public static void main(String[] args) {
        Handler planeHandler = new PlaneHandler();
        Handler trainHandler = new TrainHandler();
        Handler busHandler = new BusHandler();
        planeHandler.setNext(trainHandler);
        trainHandler.setNext(busHandler);

        planeHandler.handleRequest("老王", 40d);
        planeHandler.handleRequest("张三", 140d);
        planeHandler.handleRequest("李四", 240d);
        planeHandler.handleRequest("吴老五", 340d);
    }
}
~~~

![img](https://ws1.sinaimg.cn/large/006tNbRwgy1fyh0p6ch0qj31d2054tam.jpg)

抽象处理

```java
@Data
public abstract class Handler {

    /**
     * 下一个链节点
     */
    protected Handler next;

    public abstract void handleRequest(String name, Double wallet);
}
```

具体的处理者（飞机、火车、大巴）

```java
@Slf4j
public class PlaneHandler extends Handler {

    private double price = 280d;

    @Override
    public void handleRequest(String name, Double wallet) {
        if (price <= wallet) {
            log.info("{}身上的钱可以坐飞机。", name);
            return;
        }
        if (Objects.nonNull(next)) {
            next.handleRequest(name, wallet);
            return;
        }
        log.info("{}钱不够，只能徒步啦", name);
    }
}
```

```java
@Slf4j
public class TrainHandler extends Handler {

    private double price = 149.99d;

    @Override
    public void handleRequest(String name, Double wallet) {
        if (price <= wallet) {
           log.info("{}身上的钱只能坐火车。", name);
           return;
        }
        if (Objects.nonNull(next)) {
            next.handleRequest(name, wallet);
            return;
        }
        log.info("{}钱不够，只能徒步啦", name);
    }
}
```

```java
@Slf4j
public class BusHandler extends Handler {

    private double price = 59.99d;

    @Override
    public void handleRequest(String name, Double wallet) {
        if (price <= wallet) {
            log.info("{}身上的钱只能坐大巴。", name);
            return;
        }
        if (Objects.nonNull(next)) {
            next.handleRequest(name, wallet);
            return;
        }
        log.info("{}钱不够，只能徒步啦", name);
    }
}
```

#### 2.出行方式2，参考Filter链的写法

travel2包是对travel包的重新写法。

```java
public class Application {

    public static void main(String[] args) {
        HandlerChain chain = new HandlerChain();
        Handler planeHandler = new PlaneHandler();
        Handler trainHandler = new TrainHandler();
        Handler busHandler = new BusHandler();
        chain.addHandler(planeHandler);
        chain.addHandler(trainHandler);
        chain.addHandler(busHandler);

        chain.handle("老王", 40d);
        chain.handle("张三", 140d);
        chain.handle("李四", 240d);
        chain.handle("吴老五", 340d);
    }
}
```

![image-20181223222027677](https://ws4.sinaimg.cn/large/006tNbRwgy1fyh1k4ax4xj31i4060di2.jpg)

抽象处理者

```java
public interface Handler {

    void handleRequest(String name, Double wallet, HandlerChain chain);
}
```

具体处理者（飞机、火车、大巴）

```java
@Slf4j
public class PlaneHandler implements Handler {

    private double price = 280d;

    @Override
    public void handleRequest(String name, Double wallet, HandlerChain chain) {
        if (price <= wallet) {
            log.info("{}身上的钱可以坐飞机。", name);
            chain.reuse();
            return;
        }
        chain.handle(name, wallet);
    }
}
```

```java
@Slf4j
public class TrainHandler implements Handler {

    private double price = 149.99d;

    @Override
    public void handleRequest(String name, Double wallet, HandlerChain chain) {
        if (price <= wallet) {
            log.info("{}身上的钱只能坐火车。", name);
            chain.reuse();
            return;
        }
        chain.handle(name, wallet);
    }
}
```

```java
@Slf4j
public class BusHandler implements Handler {

    private double price = 59.99d;

    @Override
    public void handleRequest(String name, Double wallet, HandlerChain chain) {
        if (price <= wallet) {
            log.info("{}身上的钱只能坐大巴。", name);
            chain.reuse();
            return;
        }
        chain.handle(name, wallet);
    }
}
```

责任链管理者

```java
@Slf4j
public class HandlerChain {

    private List<Handler> handlerList = new ArrayList<>();

    /**
     * 维护当前链上位置
     */
    private int pos;

    /**
     * 链的长度
     */
    private int handlerLength;

    public void addHandler(Handler handler) {
        handlerList.add(handler);
        handlerLength = handlerList.size();
    }

    public void handle(String name, double wallet) {
        if (CollectionUtils.isEmpty(handlerList)) {
            log.error("有钱，但没提供服务，{}也估计就只能步行了。", name);
            return;
        }
        if (pos >= handlerLength) {
            log.error("身上钱不够，{}也估计就只能步行了。", name);
            reuse();
            return;
        }
        Handler handler = handlerList.get(pos++);
        if (Objects.isNull(handler)) {
            log.error("假服务，{}也估计就只能步行了。", name);
            reuse();
            return;
        }

        handler.handleRequest(name, wallet, this);
    }

    /**
     * 链重新使用
     */
    public void reuse() {
        pos = 0;
    }
}
```

### 学习Java的Filter

待补充...



### 参考

[维基的责任链模式](https://en.wikipedia.org/wiki/Chain-of-responsibility_pattern)

[菜鸟教程的责任链模式](http://www.runoob.com/design-pattern/chain-of-responsibility-pattern.html)

[南乡清水的实际项目运用之Responsibility-Chain模式](https://www.jianshu.com/p/e52d1005d8f1)

[一起学设计模式 - 责任链模式](https://segmentfault.com/a/1190000012148496)