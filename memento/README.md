# 备忘录模式

>Without violating encapsulation, capture and externalize an object's internal state allowing the object to be restored to this state later.
>
>在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。


所谓备忘录模式就是在不破坏封装的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，这样可以在以后将对象恢复到原先保存的状态。很多时候我们总是需要记录一个对象的内部状态，这样做的目的就是为了允许用户取消不确定或者错误的操作，能够恢复到他原先的状态，使得他有"后悔药"可吃。通过一个备忘录类专门存储对象状态。客户不与备忘录类耦合，与备忘录管理类耦合。



# 备忘录模式UML

![uml](https://ws2.sinaimg.cn/large/006tNc79gy1fzjzi674u6j30iw06omxu.jpg)

## UML类图

Caretaker类是指用于保存(createMemento())和还原(restore(memento))发起方内部状态的Originator类。
发起方类实现
（1）createMemento()，通过创建和返回存储发起方当前内部状态的memento对象
（2）通过从传入的memento对象还原状态来还原(memento)。


## UML时序图

（1）保存发起人的内部状态：Caretaker对Originator调用createMemento()，创建memento对象，保存其当前内部状态(setState())，并将memento返回给Caretaker。

（2）恢复发起人的内部状态：Caretaker对Originator调用restore(memento)，并指定存储应恢复状态的memento对象。发起者从memento获取状态(getState())以设置其自己的状态。



# 备忘录模式角色结构

(1) 备忘录（Memento）角色：备忘录角色存储“备忘发起角色”的内部状态。“备忘发起角色”根据需要决定备忘录角色存储“备忘发起角色”的哪些内部状态。为了防止“备忘发起角色”以外的其他对象访问备忘录。备忘录实际上有两个接口，“备忘录管理者角色”只能看到备忘录提供的窄接口——对于备忘录角色中存放的属性是不可见的。“备忘发起角色”则能够看到一个宽接口——能够得到自己放入备忘录角色中属性。

(2) 备忘发起（Originator）角色：“备忘发起角色”创建一个备忘录，用以记录当前时刻它的内部状态。在需要时使用备忘录恢复内部状态。

(3) 备忘录管理者（Caretaker）角色：负责保存好备忘录。不能对备忘录的内容进行操作或检查。



# 干货示例

源码地址



```java
public class Caretaker {

​    public static void main(String[] args) {
​        List<Memento> savedStates = new ArrayList();
​        Originator originator = new Originator();
​        originator.set("State1");
​        originator.set("State2");
​        savedStates.add(originator.saveToMemento());

​        originator.set("State3");
​        savedStates.add(originator.saveToMemento());

​        originator.set("State4");
​        originator.restoreFromMemento(savedStates.get(1));
​    }
}


@Slf4j
public class Originator {

​    private String state;

​    //状态更改
​    public void set(String state) {
​        this.state = state;
​        log.info("Originator: Setting state to {}", state);
​    }

​    //将状态保存到备忘录里
​    public Memento saveToMemento() {
​        log.info("Originator: Saving to Memento.");
​        return new Memento(this.state);
​    }

​    //从备忘录里取出状态并回滚
​    public void restoreFromMemento(Memento memento) {
​        this.state = memento.getState();
​        log.info("Originator: State after restoring from Memento: {}", state);
​    }
}


@Data
@AllArgsConstructor
public class Memento {

​    //状态维护
​    private String state;

}

```



示例结果

![image-20190126161658817](https://ws1.sinaimg.cn/large/006tNc79gy1fzk24eet38j3258090gof.jpg)

从上述代码中看的出，随着状态变更，用List维护发起者的状态列表，从备忘录中取出状态以便回退状态。





# java中的使用

生成对象状态的一个快照，以便对象可以恢复原始状态而不用暴露自身的内容。Date对象通过自身内部的一个long值来实现备忘录模式。

java.util.Date

java.io.Serializable



# 总结

## **优点**

 1、给用户提供了一种可以恢复状态的机制，可以使用户能够比较方便地回到某个历史的状态。 2、实现了信息的封装，使得用户不需要关心状态的保存细节。

## **缺点**

消耗资源。如果类的成员变量过多，势必会占用比较大的资源，而且每一次保存都会消耗一定的内存。

## **使用场景** 

1、需要保存/恢复数据的相关状态场景。 2、提供一个可回滚的操作。

## **注意事项** 

1、为了符合迪米特原则，还要增加一个管理备忘录的类。 2、为了节约内存，可使用原型模式+备忘录模式。



# 参考

[备忘录模式|菜鸟教程](http://www.runoob.com/design-pattern/memento-pattern.html)

[Memento pattern](https://en.wikipedia.org/wiki/Memento_pattern)

[细数JDK里的设计模式](https://www.cnblogs.com/tinyking/p/5938547.html)