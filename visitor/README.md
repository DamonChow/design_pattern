### 访问者模式

> Represent an operation to be performed on the elements of an object structure. Visitor lets a new operation be defined without changing the classes of the elements on which it operates.
>
> **访问者模式的目的是封装一些施加于某种数据结构元素之上的操作。一旦这些操作需要修改的话，接受这个操作的数据结构则可以保持不变。**

<!--more-->

**意图** 主要将数据结构与数据操作分离。

**主要解决** 稳定的数据结构和易变的操作耦合问题。

**何时使用** 需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免让这些操作"污染"这些对象的类，使用访问者模式将这些封装到类中。

### **访问者模式的主要角色**

> Visitor（抽象访问者）：抽象访问者为对象结构中每一个具体元素类ConcreteElement声明一个访问操作，从这个操作的名称或参数类型可以清楚知道需要访问的具体元素的类型，具体访问者需要实现这些操作方法，定义对这些元素的访问操作。
>
> ConcreteVisitor（具体访问者）：具体访问者实现了每个由抽象访问者声明的操作，每一个操作用于访问对象结构中一种类型的元素。
>
> Element（抽象元素）：抽象元素一般是抽象类或者接口，它定义一个accept()方法，该方法通常以一个抽象访问者作为参数。
>
> ConcreteElement（具体元素）：具体元素实现了accept()方法，在accept()方法中调用访问者的访问方法以便完成对一个元素的操作。
>
> ObjectStructure（对象结构）：对象结构是一个元素的集合，它用于存放元素对象，并且提供了遍历其内部元素的方法。它可以结合组合模式来实现，也可以是一个简单的集合对象，如一个List对象或一个Set对象。



**使用场景：** 1、对象结构中对象对应的类很少改变，但经常需要在此对象结构上定义新的操作。 2、需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免让这些操作"污染"这些对象的类，也不希望在增加新操作时修改这些类。

### 访问者模式相关UML图

UML类图和时序图

![img](https:////upload.wikimedia.org/wikipedia/commons/0/00/W3sDesign_Visitor_Design_Pattern_UML.jpg)

在类图中可以看出，ElementA实现了接口Element的accept(visitor)方法，而通过visitor.visitElementA(this)，相同visitor1类通过实现visitElementA(ElementA a)方法与ElementA关联。相同的ElementB亦是如此原理。

右上角的时序图，Client对象有一组Element的数据结构，通过循环对每个元素Element调用accept(visitor)方法，例如先是ElementA调用accept(visitor)，实际上就是调用visitor1的visitElementA(A)，同样情况对ElementB。

更加清晰的类图如下

![img](https:////upload.wikimedia.org/wikipedia/en/thumb/e/eb/Visitor_design_pattern.svg/430px-Visitor_design_pattern.svg.png)



### 实例干货

[跳转到对应的源码](https://github.com/DamonChow/design_pattern/tree/master/visitor)

```java
//抽象元素
public interface Element {

    void accept(ElementVisitor visitor);
}

//具体元素-车轮
@Data
@AllArgsConstructor
public class Wheel implements Element {

    private String name;

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visit(this);
    }
}

//具体元素-车身
public class Body implements Element {

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visit(this);
    }
}

//具体元素-引擎
public class Engine implements Element {

    @Override
    public void accept(ElementVisitor visitor) {
        visitor.visit(this);
    }
}

//具体元素-整车
public class Car implements Element {

    public void accept(final ElementVisitor visitor) {
        visitor.visit(this);
    }

}

//抽象访问者
public interface ElementVisitor {

    void visit(Body body);

    void visit(Engine engine);

    void visit(Wheel wheel);

    void visit(Car car);
}

//具体的一个访问者，纯打印
@Slf4j
public class DoElementVisitor implements ElementVisitor {

    @Override
    public void visit(Body body) {
        log.info("Moving my body");
    }

    @Override
    public void visit(Engine engine) {
        log.info("Starting my engine");
    }

    @Override
    public void visit(Wheel wheel) {
        log.info("Kicking my " + wheel.getName() + " wheel");
    }

    @Override
    public void visit(Car car) {
        log.info("Starting my car");
    }
}

//单独还定义对象结构，其实完全就可以使用列表就可以
@Data
public class ElementStructure {

    private List<Element> list = Lists.newArrayList();

    public void addElement(Element element){
        list.add(element);
    }

    public void accept(ElementVisitor visitor) {
        for (Element elem : list) {
            elem.accept(visitor);
        }
    }
}
```

上述就是针对访问者模式做的一个对于汽车零件的一个打印效果。

```java
public class ClientWithVisitor {

    public static void main(String[] args) {
        ElementStructure structure = new ElementStructure();
        structure.addElement(new Wheel("front left"));
        structure.addElement(new Wheel("front right"));
        structure.addElement(new Wheel("back left"));
        structure.addElement(new Wheel("back right"));
        structure.addElement(new Body());
        structure.addElement(new Engine());
        structure.addElement(new Car());

        structure.accept(new DoElementVisitor());
    }
}

@Slf4j
public class ClientWithoutVisitor {

    public static void main(String[] args) {
        ElementStructure structure = new ElementStructure();
        structure.addElement(new Wheel("front left"));
        structure.addElement(new Wheel("front right"));
        structure.addElement(new Wheel("back left"));
        structure.addElement(new Wheel("back right"));
        structure.addElement(new Body());
        structure.addElement(new Engine());
        structure.addElement(new Car());

        structure.getList().forEach(e -> {
            if (e instanceof Body) {
                log.info("Moving my body");
            } else if (e instanceof Engine) {
                log.info("Starting my engine");
            } else if (e instanceof Car) {
                log.info("Starting my car");
            } else if (e instanceof Wheel) {
                log.info("Kicking my " + ((Wheel)e).getName() + " wheel");
            }
        });
    }

}
```

打印结果都是一样的

![image-20190107164438915](https://ws1.sinaimg.cn/large/006tNc79gy1fyy45dgfcoj31ma092gon.jpg)



### 总结分析

在上面的例子中分别用了访问者模式和非访问者模式两种方法。

1、使用VIsitor的好处一目了然，当需要修改某些元素的业务逻辑时，只需要修改Visitor类中相对应的操作函数即可。例如假设要修改Wheel的逻辑，只需要修改Visitor的visit(Wheel wheel)方法即可。

2、假设我们又需要新增一个汽车元素天窗的话，只需要在visitor中添加新的接口以处理新元素，而别的元素可以保持不动。  违背开闭原则。

3、当我们需要添加新的业务操作，只需要添加新的具体访问者，其他的依旧可以保持不变。符合开闭原则。

同样，有好处也就有缺陷，因为逻辑在visitor里面，所有visitor和Element高度耦合，同样针对visit方法返回类型，需要设计的优雅，如若不然，后期一旦修改返回类型，影响的范围就广，所有访问者接口和实现都波及到。访问者模式要求访问者对象访问并调用每一个元素对象的操作，这意味着元素对象有时候必须暴露一些自己的内部操作和内部状态，否则无法供访问者访问，这一点跟迪米特法则和依赖倒置原则相违背。

总的而言，访问者模式的使用条件较为苛刻，本身结构也较为复杂，因此在实际应用中使用频率不是特别高。



### JDK中含有的访问者模式

提供一个方便的可维护的方式来操作一组对象。它使得你在不改变操作的对象前提下，可以修改或者扩展对象的行为。 

> javax.lang.model.element.Element and javax.lang.model.element.ElementVisitor
>
> javax.lang.model.type.TypeMirror and javax.lang.model.type.TypeVisitor



### 参考

[访问者模式|菜鸟教程](http://www.runoob.com/design-pattern/visitor-pattern.html)

[Visitor pattern](https://en.wikipedia.org/wiki/Visitor_pattern)

[JAVA设计模式（23）：行为型-访问者模式（Visitor）](https://blog.csdn.net/taozi8023/article/details/51457616)