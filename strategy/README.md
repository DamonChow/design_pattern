### 简介

> 在策略模式（Strategy Pattern）中，一个类的行为或其算法可以在运行时更改。这种类型的设计模式属于行为型模式。简单理解就是一组算法，可以互换，再简单点策略就是封装算法。

<!--more-->

**意图** 定义一系列的算法,把它们一个个封装起来, 并且使它们可相互替换。

**主要解决** 在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。

**何时使用** 一个系统有许多许多类，而区分它们的只是他们直接的行为。

**如何解决** 将这些算法封装成一个一个的类，任意地替换。

**主要角色** 

- 上下文Context，拥有一个Strategy的引用
- 抽象策略Strategy，往往是一个接口(占大部分情况)或者抽象类，通常提供各种具体策略的接口
- 具体策略，这就是重点了，封装了各种具体的算法

**UML**

![img](https://upload.wikimedia.org/wikipedia/commons/4/45/W3sDesign_Strategy_Design_Pattern_UML.jpg)



**应用实例** 

- 诸葛亮的锦囊妙计，每一个锦囊就是一个策略；
- 旅行的出游方式，选择骑自行车、坐汽车，每一种旅行方式都是一个策略；
- JAVA AWT 中的 LayoutManager；

**优点** 1、算法可以自由切换。 2、避免使用多重条件判断。 3、扩展性良好。

**缺点**  1、策略类会增多。 2、所有策略类都需要对外暴露。

**使用场景** 

1. 如果在一个系统里面有许多类，它们之间的区别仅在于它们的行为，那么使用策略模式可以动态地让一个对象在许多行为中选择一种行为。
2. 一个系统需要动态地在几种算法中选择一种。
3. 如果一个对象有很多的行为，如果不用恰当的模式，这些行为就只好使用多重的条件选择语句来实现。

> **注意事项：** 如果一个系统的策略多于四个，就需要考虑使用混合模式，解决策略类膨胀的问题。



### **项目描述**  

[跳转到我的策略模式GitHub](https://github.com/DamonChow/design_pattern/tree/master/strategy)

#### 1.操作行为

simple1包，主要对操作行为包装了加减乘除方法。

```java
@Slf4j
public class Application {

    public static void main(String[] args) {
        Context context = new Context(new AddStrategy());
        log.info("10 + 5 = {}", context.executeStrategy(10, 5));

        context.setStrategy(new SubstractStrategy());
        log.info("10 - 5 = {}", context.executeStrategy(10, 5));

        context.setStrategy(new MultiplyStrategy());
        log.info("10 * 5 = {}", context.executeStrategy(10, 5));

        context.setStrategy(new DivideStrategy());
        log.info("10 / 5 = {}", context.executeStrategy(10, 5));
    }
}
```

执行结果

![img](https://ws4.sinaimg.cn/large/006tNbRwgy1fyfcvvwjkfj317a04wmyd.jpg)



#### 2.出现方式

simple2包描述，主要对出行方式的包装，包装了3种出行方式，

执行类

```java
public class TravelApplication {

    public static void main(String[] args) {
        Context context = new Context(new BusStrategy());
        context.executeStrategy("老王");

        context.setStrategy(new BicycleStrategy());
        context.executeStrategy("老张");

        context.setStrategy(new WalkStrategy());
        context.executeStrategy("老李");
    }
}
```

执行结果

<u>![image-20181222104657926](https://ws1.sinaimg.cn/large/006tNbRwgy1fyfcryetvxj31es04475r.jpg)</u>



策略上下文

```java
@Data
public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 出行
     *
     * @return
     */
    public void executeStrategy(String name) {
        strategy.travel(name);
    }

}
```

抽象策略

```java
public interface Strategy {

    /**
     * 出现方法
     *
     * @return
     */
    void travel(String name);
}
```





### 参考

[策略模式](http://www.runoob.com/design-pattern/strategy-pattern.html)

[维基里的策略模式](https://en.wikipedia.org/wiki/Strategy_pattern)

[南乡清水的实际项目运用之Strategy模式](https://www.jianshu.com/p/7b7de81cdfbe)

