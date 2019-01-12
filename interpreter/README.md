---
title: 折腾Java设计模式之解释器模式
copyright: true
comment: true
date: 2019-01-10 11:01:10
categories:
tags:
---

### 解释器模式

> 解释器模式是类的行为模式。给定一个语言之后，解释器模式可以定义出其文法的一种表示，并同时提供一个解释器。客户端可以使用这个解释器来解释这个语言中的句子。

<!-- more -->

**意图** 给定一个语言，定义它的文法表示，并定义一个解释器，这个解释器使用该标识来解释语言中的句子。

**主要解决** 对于一些固定文法构建一个解释句子的解释器。

**何时使用** 如果一种特定类型的问题发生的频率足够高，那么可能就值得将该问题的各个实例表述为一个简单语言中的句子。这样就可以构建一个解释器，该解释器通过解释这些句子来解决该问题。

**如何解决** 构件语法树，定义终结符与非终结符。

**关键代码** 构件环境类，包含解释器之外的一些全局信息，一般是 HashMap。



### 解释器模式相关UML图

类图

![Interpreter UML class diagram.svg](https:////upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Interpreter_UML_class_diagram.svg/536px-Interpreter_UML_class_diagram.svg.png)



![img](https:////upload.wikimedia.org/wikipedia/commons/3/33/W3sDesign_Interpreter_Design_Pattern_UML.jpg)



可以看出右侧的协作图(object collaboration diagram)展现出运行时的解释器模式。Client向右侧抽象语法树发送解释请求，请求被转发并向下到树结构的所有对象。



### 解释器模式的主要角色

> 抽象解释器(AbstractExpression/Expression)：声明一个所有具体表达式都要实现的抽象接口（或者抽象类），接口中主要是一个interpret()方法，称为解释操作。具体解释任务由它的各个实现类来完成，具体的解释器分别由终结符解释器TerminalExpression和非终结符解释器NonterminalExpression完成。
>
> 终结符表达式(TerminalExpression)：实现与文法中的元素相关联的解释操作，通常一个解释器模式中只有一个终结符表达式，但有多个实例，对应不同的终结符。终结符一半是文法中的运算单元，比如有一个简单的公式R=R1+R2，在里面R1和R2就是终结符，对应的解析R1和R2的解释器就是终结符表达式。 
>
> 非终结符表达式(NonterminalExpression)：文法中的每条规则对应于一个非终结符表达式，非终结符表达式一般是文法中的运算符或者其他关键字，比如公式R=R1+R2中，+就是非终结符，解析+的解释器就是一个非终结符表达式。非终结符表达式根据逻辑的复杂程度而增加，原则上每个文法规则都对应一个非终结符表达式。
>
> 环境角色(Context)：这个角色的任务一般是用来存放文法中各个终结符所对应的具体值，比如R=R1+R2，我们给R1赋值100，给R2赋值200。这些信息需要存放到环境角色中，很多情况下我们使用Map来充当环境角色就足够了。

### 干货代码

[跳转到源码地址](https://github.com/DamonChow/design_pattern/tree/master/interpreter)

简单的一个解释器计算加减乘除算法，环境上下文没有用好，其实计算规则更多的是人为设定的了。

本次的抽象接收器用的是抽象类，用接口代替也可以。

```java
//抽象解释器
public abstract class AbstractExpression {

    public abstract int interpreter(Context context);
}

//非终结表达式：加法
@Data
@AllArgsConstructor
public class Add extends AbstractExpression {

    private final AbstractExpression left;

    private final AbstractExpression right;

    @Override
    public int interpreter(Context context) {
        return left.interpreter(context) + right.interpreter(context);
    }
}

//非终结表达式：减法
@Data
@AllArgsConstructor
public class Subtract extends AbstractExpression {

    private final AbstractExpression left;

    private final AbstractExpression right;

    @Override
    public int interpreter(Context context) {
        return left.interpreter(context) - right.interpreter(context);
    }
}

//非终结表达式：乘法
@Data
@AllArgsConstructor
public class Multiply extends AbstractExpression {

    private final AbstractExpression left;

    private final AbstractExpression right;

    @Override
    public int interpreter(Context context) {
        return left.interpreter(context) * right.interpreter(context);
    }
}

//非终结表达式：除法
@Data
@AllArgsConstructor
public class Division extends AbstractExpression {

    private final AbstractExpression left;

    private final AbstractExpression right;

    @Override
    public int interpreter(Context context) {
        int right = this.right.interpreter(context);
        if (right != 0) {
            return left.interpreter(context) / right;
        }

        return -1;
    }
}

//终结表达式：变量
@Data
@AllArgsConstructor
public class Variable extends AbstractExpression {

    private final String key;

    @Override
    public int interpreter(Context context) {
        return context.getValue(key);
    }
}

//环境上下文
@Getter
public class Context {

    private final Map<String, Integer> valueMap = Maps.newHashMap();

    public void addValue(final String key, final int value) {
        valueMap.put(key, Integer.valueOf(value));
    }

    public int getValue(final String key) {
        return valueMap.get(key).intValue();
    }
}


//
public class Application {

    public static void main(String[] args) {
        Context context = new Context();
        context.addValue("a", 6);
        context.addValue("b", 9);
        context.addValue("c", 1);

        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Variable c = new Variable("c");

        AbstractExpression multiplyValue = new Multiply(a, b);
        AbstractExpression subtractValue = new Subtract(a, b);
        AbstractExpression addValue = new Add(subtractValue, c);
        AbstractExpression divisionValue = new Division(multiplyValue, addValue);

        log.info("{}", context.getValueMap());
        log.info("(a*b)/(a-b+c) = {}", divisionValue.interpreter(context));
    }
}
```

结果如下：

![image-20190112095954448](https://ws2.sinaimg.cn/large/006tNc79gy1fz3kjs5hgrj31eu036dgk.jpg)





### Java中的应用



#### **java中的表达式引擎**

[parsii](https://github.com/scireum/parsii)

[JEval](http://jeval.sourceforge.net/)

[JEPLite](http://jeplite.sourceforge.net/)

[expr](https://github.com/darius/expr)

[Janino](http://docs.codehaus.org/display/JANINO/Basic)

[MathEval](http://softwaremoncg.org/code/matheval)

[Java表达式引擎fel/groovy/expression4j/java脚本引擎的性能对比](http://www.findsrc.com/java/detail/8664)

#### JDK中的应用

这个模式通常定义了一个语言的语法，然后解析相应语法的语句。 

> java.util.Pattern 
>
> java.text.Normalizer 
>
> java.text.Format



### 参考

[解释器模式|菜鸟教程](http://www.runoob.com/design-pattern/interpreter-pattern.html)

[Interpreter pattern](https://en.wikipedia.org/wiki/Interpreter_pattern)

[细数JDK里的设计模式](https://www.cnblogs.com/tinyking/p/5938547.html)

[23种设计模式（14）：解释器模式](https://blog.csdn.net/zhengzhb/article/details/7666020)

