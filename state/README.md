### 状态模式

> 在状态模式（State Pattern）中，类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。在状态模式中，我们创建表示各种状态的对象和一个行为随着状态对象改变而改变的 context 对象。通俗点就是一个对象在内部状态发生改变时改变它的行为。

<!--more-->

### 介绍

**意图** 允许对象在内部状态发生改变时改变它的行为，对象看起来好像修改了它的类。

**主要解决** 对象的行为依赖于它的状态（属性），并且可以根据它的状态改变而改变它的相关行为。

**何时使用** 代码中包含大量与对象状态有关的条件语句。

**如何解决** 将各种具体的状态类抽象出来。

**关键代码** 通常命令模式的接口中只有一个方法。而状态模式的接口中有一个或者多个方法。而且，状态模式的实现类的方法，一般返回值，或者是改变实例变量的值。也就是说，状态模式一般和对象的状态有关。实现类的方法有不同的功能，覆盖接口中的方法。状态模式和命令模式一样，也可以用于消除 if...else 等条件选择语句。

**UML图**

![image.png](https://upload-images.jianshu.io/upload_images/3344200-0407b24a011bc7fd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**主要角色**

> 1）Context(环境类)：环境类拥有各种不同状态的对象，作为外部使用的接口，负责调用状态类接口。 
>
> 2）State(抽象状态)：抽象状态既可以为抽象类，也可以直接定义成接口。主要用于定义状态抽象方法，具体实现由子类负责。 
>
> 3）ConcreteState(具体状态类):具体状态类为抽象状态的实现者，不同的状态类对应这不同的状态，其内部实现也不相同。环境类中使用不同状态的对象时，能实现不同的处理逻辑

**应用实例** 

>  1、打篮球的时候运动员可以有正常状态、不正常状态和超常状态。
>
>  2、曾侯乙编钟中，'钟是抽象接口','钟A'等是具体状态，'曾侯乙编钟'是具体环境（Context）。

### **优点** 

> 1、封装了转换规则。 
>
> 2、枚举可能的状态，在枚举状态之前需要确定状态种类。 
>
> 3、将所有与某个状态有关的行为放到一个类中，并且可以方便地增加新的状态，只需要改变对象状态即可改变对象的行为。 
>
> 4、允许状态转换逻辑与状态对象合成一体，而不是某一个巨大的条件语句块。 
>
> 5、可以让多个环境对象共享一个状态对象，从而减少系统中对象的个数。

### **缺点** 

> 1、状态模式的使用必然会增加系统类和对象的个数。 
>
> 2、状态模式的结构与实现都较为复杂，如果使用不当将导致程序结构和代码的混乱。
>
>  3、状态模式对"开闭原则"的支持并不太好，对于可以切换状态的状态模式，增加新的状态类需要修改那些负责状态转换的源代码，否则无法切换到新增状态，而且修改某个状态类的行为也需修改对应类的源代码。

**使用场景**

> -  行为随状态改变而改变的场景。
>
> - 条件、分支语句的代替者。

### 状态模式和策略模式的对比

现在我们知道，状态模式和策略模式的结构是相似的，但它们的意图不同。让我们重温一下它们的主要不同之处：

> 1. 策略模式封装了一组相关算法，它允许Client在运行时使用可互换的行为；状态模式帮助一个类在不同的状态显示不同的行为。
> 2. 状态模式封装了对象的状态，而策略模式封装算法或策略。因为状态是跟对象密切相关的，它不能被重用；而通过从Context中分离出策略或算法，我们可以重用它们。
> 3. 在状态模式中，每个状态通过持有Context的引用，来实现状态转移；但是每个策略都不持有Context的引用，它们只是被Context使用。
> 4. 策略实现可以作为参数传递给使用它的对象，例如Collections.sort()，它的参数包含一个Comparator策略。另一方面，状态是Context对象自己的一部分，随着时间的推移，Context对象从一个状态转移到另一个状态。
> 5. 虽然它们都符合OCP原则，策略模式也符合SRP原则（单一职责原则），因为每个策略都封装自己的算法，且不依赖其他策略。一个策略的改变，并不会导致其他策略的变化。
> 6. 另一个理论上的不同：策略模式定义了对象“怎么做”的部分。例如，排序对象怎么对数据排序。状态模式定义了对象“是什么”和“什么时候做”的部分。例如，对象处于什么状态，什么时候处在某个特定的状态。
> 7. 状态模式中很好的定义了状态转移的次序；而策略模式并无此需要：Client可以自由的选择任何策略。
> 8. 一些常见的策略模式的例子是封装算法，例如排序算法，加密算法或者压缩算法。如果你看到你的代码需要使用不同类型的相关算法，那么考虑使用策略模式吧。而识别何时使用状态模式是很简单的：如果你需要管理状态和状态转移，但不想使用大量嵌套的条件语句，那么就是它了。
> 9. 最后但最重要的一个不同之处是，策略的改变由Client完成；而状态的改变，由Context或状态自己。

### 项目实例

[跳转到我的源码地址](https://github.com/DamonChow/design_pattern/tree/master/state)

simple1包中主要是对风扇的开关状态进行转换，其实我们是把状态放在状态类中进行按照固定的逻辑转换，但是这种模式其实他不符合开闭原则，为什么了，因为一旦我们发生新增、修改或者删除状态的时候，就需要修改状态类中的状态转换。

```java
public class Application {

    public static void main(String[] args) {
        Context context = new Context(new CloseLevelState());
        context.right();
        context.right();
        context.right();
        context.left();
        context.right();
        context.right();
    }
}
```

![image-20181226103419685](https://ws2.sinaimg.cn/large/006tNbRwgy1fyjy0dgqvcj31cc07egoh.jpg)

抽象状态

```java
public interface LevelState {

    /**
     * 左转
     *
     * @param context
     */
    void left(Context context);

    /**
     * 右转
     *
     * @param context
     */
    void right(Context context);

    /**
     * 当前档位
     * @return
     */
    String info();
}
```

具体档位状态，我只列了2个，其他的类似

```java
@Slf4j
public class OneLevelState implements LevelState {

    @Override
    public void left(Context context) {
        LevelState levelState = new CloseLevelState();
        context.setLevelState(levelState);
        log.info("风扇左转到{}", levelState.info());
    }

    @Override
    public void right(Context context) {
        LevelState levelState = new TwoLevelState();
        context.setLevelState(levelState);
        log.info("风扇右转到{}", levelState.info());
    }

    @Override
    public String info() {
        return "1档";
    }
}
```

```java
@Slf4j
public class CloseLevelState implements LevelState {

    @Override
    public void left(Context context) {
        LevelState levelState = new ForeLevelState();
        context.setLevelState(levelState);
        log.info("风扇左转到{}", levelState.info());
    }

    @Override
    public void right(Context context) {
        LevelState levelState = new OneLevelState();
        context.setLevelState(levelState);
        log.info("风扇右转到{}", levelState.info());
    }

    @Override
    public String info() {
        return "0档";
    }

}
```

真正的开关也就是上下文

```java
@Data
@AllArgsConstructor
public class Context {

    private LevelState levelState;

    public void left() {
        levelState.left(this);
    }

    public void right() {
        levelState.right(this);
    }

    public String info() {
        return levelState.info();
    }
}
```



### 参考

[Java中，状态模式和策略模式的区别](https://blog.csdn.net/songwenbinasdf/article/details/51831926)

[状态模式|菜鸟教程](http://www.runoob.com/design-pattern/state-pattern.html)

