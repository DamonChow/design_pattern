### 命令模式

> wiki上的描述 Encapsulate a request as an object, thereby allowing for the parameterization of clients with different requests, and the queuing or logging of requests. It also allows for the support of undoable operations.

> 翻译意思，把请求封装成一个对象，从而允许我们可以对客户端的不同请求进行参数化，以及对请求进行排队或记录。还允许支持撤销操作。看起来好像很复杂，很难理解。<!--more-->
>
> 通俗简单理解，它就是将请求封装成一个对象，在这里就是这个对象就是命令，而这个命令就是将请求方和执行方分离隔开。从而每一个命令其实就是操作，而这样的流程就是请求方发出请求要求执行某操作，接收方收到请求后并执行对应的操作。这样下来，请求方和接收方就解耦了，使得请求方完全不知道接受的操作方法，从也不会知道接收方是何时接受到请求的，又是何时执行操作的，又是怎么执行操作的。

### 具体的角色

> Command（抽象命令类）：抽象命令类一般是一个抽象类或接口，在其中声明了用于执行请求的execute()等方法，通过这些方法可以调用请求接收者的相关操作。
>
> ConcreteCommand（具体命令类）：具体命令类是抽象命令类的子类，实现了在抽象命令类中声明的方法，它对应具体的接收者对象，将接收者对象的动作绑定其中。在实现execute()方法时，将调用接收者对象的相关操作(Action)。
>
> Invoker（请求方）：调用者即请求发送者，它通过命令对象来执行请求。一个调用者并不需要在设计时确定其接收者，因此它只与抽象命令类之间存在关联关系。在程序运行时可以将一个具体命令对象注入其中，再调用具体命令对象的execute()方法，从而实现间接调用请求接收者的相关操作。
>
> Receiver（接收方）：接收者执行与请求相关的操作，它具体实现对请求的业务处理。
>
> Client（客户端）：创建具体命令的对象并且设置命令对象的接受者。

### 再来看看UML图

![img](https://upload.wikimedia.org/wikipedia/commons/c/c8/W3sDesign_Command_Design_Pattern_UML.jpg)

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Command_pattern.svg/700px-Command_pattern.svg.png)

从上方的时序图中可以看出运行的顺序，Invoker执行execute方法，调用Command1对象，Command1执行action1方法调用Receiver1对象。

### 干货代码

[源码在我的GitHub地址](https://github.com/DamonChow/design_pattern/tree/master/command)

#### 普通的命令模式

现在结合下上回说到的[状态模式](https://damonchow.github.io/2018/12/24/design-pattern-state)一起来实现这个风扇的左转和右转功能，这次把他用命令模式来代替之前风扇的转动，把它当做命令来。

客户端简单的定义请求方和接收方以及对于的左转命令和右转命令，设置命令后对应的执行命令。

```java
public class Client {

    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver();
        Command leftCommand = new LeftCommand(receiver);
        Command rightCommand = new RightCommand(receiver);

        invoker.setCommand(rightCommand);
        invoker.execute();
        invoker.execute();
        invoker.execute();

        invoker.setCommand(leftCommand);
        invoker.execute();
        invoker.execute();
    }
}
```

![image-20190102115207650](https://ws4.sinaimg.cn/large/006tNbRwgy1fys3lfn4bnj31es0740v1.jpg)

请求方

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoker {

    private Command command;

    public void execute() {
        command.execute();
    }

}
```

抽象命令

```java
public interface Command {

    void execute();
}
```

开关左转

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeftCommand implements Command {

    private Receiver receiver;

    @Override
    public void execute() {
        receiver.left();
    }
}
```

开关右转

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RightCommand implements Command {

    private Receiver receiver;

    @Override
    public void execute() {
        receiver.right();
    }
}
```

接收方

```java
public class Receiver {

    private Context context = new Context(new CloseLevelState());

    public void left() {
        context.left();
    }

    public void right() {
        context.right();
    }
}
```

通过命令模式把左转和右转封装成命令，以及之前的状态模式变更风扇的状态。本次就是通过状态模式和命令模式实现了一个风扇开关左右转的功能。

#### 宏命令或者叫做组合命令

设计一组命令，简单的处理事情，打印一句话，封装成一组命令。这次我们用了Java8来写，可以使用lambda。

```java
@Slf4j
public class Client {

    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        log.info("初始化ABC3个命令");
        Command aCommand = () -> log.info("A处理这个请求");
        invoker.addCommand(aCommand);
        invoker.addCommand(() -> log.info("B处理这个请求"));
        invoker.addCommand(() -> log.info("C处理这个请求"));
        invoker.execute();

        log.info("---------------------------");
        log.info("加入新命令D");
        invoker.addCommand(() -> log.info("D处理这个请求"));
        invoker.execute();

        log.info("---------------------------");
        log.info("加入新命令E");
        invoker.addCommand(() -> log.info("E处理这个请求"));
        invoker.execute();

        log.info("---------------------------");
        log.info("移除命令A");
        invoker.removeCommand(aCommand);
        invoker.execute();
    }
}
```

打印语句。

![image-20190102143032334](https://ws2.sinaimg.cn/large/006tNbRwgy1fys86bil8zj31kw0skwov.jpg)

抽象命令

```java
@FunctionalInterface
public interface Command {

    void execute();
}
```

请求方

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoker {

    private List<Command> commandList = Lists.newArrayList();

    public void addCommand(Command command) {
        commandList.add(command);
    }

    public void removeCommand(Command command) {
        commandList.remove(command);
    }

    public void execute() {
        if(CollectionUtils.isEmpty(commandList)) {
            return;
        }
        commandList.stream().forEach(command -> command.execute());
    }

}
```

#### 撤销操作

在普通的命令模式的基础上，增加了撤销操作，在这里的撤销操作，其实即为左转时的右转，右转时的左转。

```java
@Slf4j
public class Client {

    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver();
        Command leftCommand = new LeftCommand(receiver);
        Command rightCommand = new RightCommand(receiver);

        invoker.setCommand(rightCommand);
        invoker.execute();
        invoker.execute();
        invoker.execute();
        invoker.undo();
        invoker.undo();

        invoker.setCommand(leftCommand);
        invoker.execute();
        invoker.undo();
    }
}
```

![image-20190102145507099](https://ws3.sinaimg.cn/large/006tNbRwgy1fys8vu4smbj319o08s0w2.jpg)

抽象命令增加了撤销操作

```java
public interface Command {

    void execute();

    void undo();
}
```

具体左转时

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeftCommand implements Command {

    private Receiver receiver;

    @Override
    public void execute() {
        receiver.left();
    }

    @Override
    public void undo() {
        receiver.right();
    }
}
```

右转时

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RightCommand implements Command {

    private Receiver receiver;

    @Override
    public void execute() {
        receiver.right();
    }

    @Override
    public void undo() {
        receiver.left();
    }
}
```

请求方

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoker {

    private Command command;

    public void execute() {
        command.execute();
    }

    public void undo() {
        command.undo();
    }

}
```

接收方

```java
public class Receiver {

    private Context context = new Context(new CloseLevelState());

    public void left() {
        context.left();
    }

    public void right() {
        context.right();
    }
}
```



### 命令模式总结

#### 优点 

> (1) **降低系统的耦合度**。由于请求者与接收者之间不存在直接引用，因此请求者与接收者之间实现完全解耦，相同的请求者可以对应不同的接收者，同样，相同的接收者也可以供不同的请求者使用，两者之间具有良好的独立性。
>
> (2) **新的命令可以很容易地加入到系统中**。由于增加新的具体命令类不会影响到其他类，因此增加新的具体命令类很容易，无须修改原有系统源代码，甚至客户类代码，满足“开闭原则”的要求。
>
> (3) **可以比较容易地设计一个命令队列或宏命令（组合命令）**。
>
> (4) **为请求的撤销(Undo)和恢复(Redo)操作提供了一种设计和实现方案**。

#### 缺点

> **使用命令模式可能会导致某些系统有过多的具体命令类。**因为针对每一个对请求接收者的调用操作都需要设计一个具体命令类，因此在某些系统中可能需要提供大量的具体命令类，这将影响命令模式的使用。

#### 适用场景

> (1) 系统需要将请求调用者和请求接收者解耦，使得调用者和接收者不直接交互。请求调用者无须知道接收者的存在，也无须知道接收者是谁，接收者也无须关心何时被调用。
>
> (2) 系统需要在不同的时间指定请求、将请求排队和执行请求。一个命令对象和请求的初始调用者可以有不同的生命期，换言之，最初的请求发出者可能已经不在了，而命令对象本身仍然是活动的，可以通过该命令对象去调用请求接收者，而无须关心请求调用者的存在性，可以通过请求日志文件等机制来具体实现。
>
> (3) 系统需要支持命令的撤销(Undo)操作和恢复(Redo)操作。
>
> (4) 系统需要将一组操作组合在一起形成宏命令。
>
> (5)线程池有一个addTash方法，将任务添加到待完成的队列中，队列中的元素就是命令对象，通常的就是一个公共接口，像我们常用的java.lang.Runnable接口。
>
> (6)java8之后，最好在Command接口中@FunctionalInterface修饰，这样具体的命令就可以使用lambda表达式啦。

#### Java中的使用

将操作封装到对象内，以便存储，传递和返回。

> java.lang.Runnable 
>
> javax.swing.Action

### 文章参考

[java设计模式之命令模式](https://www.cnblogs.com/lfxiao/p/6825644.html)

[细数JDK里的设计模式](https://www.cnblogs.com/tinyking/p/5938547.html)

[wiki的命令模式](https://en.wikipedia.org/wiki/Command_pattern)
