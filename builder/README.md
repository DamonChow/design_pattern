---
title: 折腾Java设计模式之建造者模式
copyright: true
comment: true
date: 2019-03-29 19:18:39
categories:
- 设计模式
tags:
- 设计模式
- 建造者
---



## 建造者模式

> Separate the construction of a complex object from its representation, allowing the same construction process to create various representations.
> 
> 将复杂对象的构造与其表现分离，允许相同的构造过程用来创建不同的表现。通俗点就是，一个对象创建过程很复杂，我们将其每项元素创建过程抽离出来，通过相同的构造过程可以构造出不用的对象。还不懂可以看到如下的UML图。

<!-- more -->



## 建造者模式UML图

![image-20190329153338350](https://ws3.sinaimg.cn/large/006tKfTcly1g1jpagzw3jj30u00vtwhq.jpg)



此`AbstractPersonBuilder`就是如上的相同的构造，而不同的表现就是此处的`PersonOneBuilder`和`PersonTwoBuilder`两个相同方式的构造器，但是具体的实现是不一样而构造出不同的表现。所以就是相同的构造过程而构造出不同的对象。



## 建造者模式角色

抽象建造者(`AbstractPersonBuilder`或者`Builder`)：抽象类或者接口，复杂对象的属性的抽象方法，并不涉及具体的对象部件的创建；

具体建造者(`PersonOneBuilder`和`PersonTwoBuilder`)：实现抽象建造者，针对不同的业务，具体化复杂对象的各部分的创建。 在建造过程完成后，提供产品的实例；

指挥者(`Director`)：调用具体建造者来创建复杂对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完整创建或按某种顺序创建；

具体的产品(`Person`)：需创建的复杂对象；



## 建造者模式源码干货

源码地址：[请点击我](https://github.com/DamonChow/design_pattern/tree/master/builder)



在这里我分为三种情况讲讲建造者模式，第一种是我们最原始的建造者模式来构建，第二种是我们在实体对象时会使用的，第三种是我们平常对实体对象最常规使用方法借助lombok。



### 第一种建造者模式

使用的真是上面按照角色来建造的方式，稍微比如下的两种方法负责点。

抽象建造者

```java 
public abstract class AbstractPersonBuilder {

    protected Person product = new Person();

    public abstract void buildName();

    public abstract void buildAge();

    public abstract void buildChildren();

    public Person build() {

        return product;
    }
}
```

第一个具体建造者

```java
public class PersonOneBuilder extends AbstractPersonBuilder {

    public void buildName() {
        product.setName("老one");
    }

    public void buildAge() {
        product.setAge(44);
    }

    public void buildChildren() {
        product.setChildren(Lists.newArrayList("小one"));
    }

}
```

第二个具体建造者

```java
public class PersonTwoBuilder extends AbstractPersonBuilder {

    public void buildName() {
        product.setName("老two");
    }

    public void buildAge() {
        product.setAge(55);
    }

    public void buildChildren() {
        product.setChildren(Lists.newArrayList("小two"));
    }

}
```

Person类充当产品数据

```java
public class Person {

    private String name;

    private int age;

    private List<String> children;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", children=" + children +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
```

指挥者，指定具体的建造者用来建造对象

```java
public class Director {

    private AbstractPersonBuilder builder;

    public Director(AbstractPersonBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(AbstractPersonBuilder builder) {
        this.builder = builder;
    }

    public Person construct() {
        builder.buildName();
        builder.buildAge();
        builder.buildChildren();
        return builder.build();
    }
}
```

示例

```java
@Slf4j
public class Application {

    public static void main(String[] args) {
        Director director = new Director(new PersonOneBuilder());
        Person person = director.construct();
        log.info("person的信息：{}", person);

        director.setBuilder(new PersonTwoBuilder());
        person = director.construct();
        log.info("person的信息：{}", person);
    }
}
```

结果：

![image-20190329155504038](https://ws1.sinaimg.cn/large/006tKfTcly1g1jpwprt48j321409676a.jpg)



### 第二种建造者模式

第二种方式比上面那种简单些，因为我们只指定了一种构造方式，并且还可以借用第三方工具IDEA+Plugins。

在IDEA中可以搜索

![image-20190329160028861](https://ws4.sinaimg.cn/large/006tKfTcly1g1jq2chcbij31nu0b4wfq.jpg)



使用方法：

1.找到对应需要添加bulid的类通过自动生成快捷键可以查看到build

![image-20190329160149182](https://ws2.sinaimg.cn/large/006tKfTcly1g1jq3qlvo3j30ko0jeacw.jpg)

2.根据自己的风格可以定义bulid的名字，各个bulid方法的前缀以及包名，具体bulider如下代码中。

![image-20190329160247685](https://ws2.sinaimg.cn/large/006tKfTcly1g1jq4rb4rtj30xe0jkq7u.jpg)





PersonBuilder用来Person的构建者

```java
public final class PersonBuilder {
    private String name;
    private int age;
    private List<String> children;

    private PersonBuilder() {
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public PersonBuilder withChildren(List<String> children) {
        this.children = children;
        return this;
    }

    public Person build() {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setChildren(children);
        return person;
    }
}
```

Person类充当产品数据

```java
public class Person {

    private String name;

    private int age;

    private List<String> children;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", children=" + children +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
```

示例

```java
@Slf4j
public class Application {

    public static void main(String[] args) {
        Person wang = PersonBuilder.builder()
                .withAge(40)
                .withName("老王")
                .withChildren(Lists.newArrayList("李一一", "吴老三"))
                .build();
        log.info("老王的信息：{}", wang);
    }
}
```

结果如下：

![image-20190329155831453](https://ws1.sinaimg.cn/large/006tKfTcly1g1jq0ax082j323o090abc.jpg)



### 第三种建造者模式

第三种模式相对来说就简单非常多，因为我们借用的是lombok的@Builder注解。lombok在18.2版本中引入了@SuperBulider注解用来解决@Builder类的继承不生效的问题。详细的使用阔以看我上篇文章 [折腾Java设计模式之模板方法模式](http://damonchow.github.io/2019/03/11/design-pattern-template-method) 



```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;

    private int age;

    private List<String> children;

}
```

```java
@Slf4j
public class Application {

    public static void main(String[] args) {
        Person wang = Person.builder()
                .age(40)
                .name("老王")
                .children(Lists.newArrayList("李一一", "吴老三"))
                .build();
        log.info("老王的信息：{}", wang);
    }
}
```



结果：

![image-20190329160655205](https://ws4.sinaimg.cn/large/006tKfTcly1g1jq91mao3j320s05mmy7.jpg)



## 总结

第二、三种模式在我们经常操作像对VO、DO、DTO对象时，常用如此定义。第一种标准的建造者模式，其实本身指挥者这个角色是不关心具体的产品实现的，相对于是一种解耦，对于如果新增一种建造者实现，可以方便扩展，符合开闭原则，但是无独有偶，实现了上述优点后，但是缺点也跟着来，新增了很多类，维护成本高，如果建造者内部发生变更，就不太适合建造者这种模式了。总体而言还是有很多使用场景的。像StringBulider其实也是一种。像之前在[spring-boot的spring-cache中的扩展redis缓存的ttl和key名](http://damonchow.github.io/2019/01/31/spring-boot-redis-cache-ext/)这篇文章中定义的RedisCacheManagerBuilder，以及我们常用的以后要讲的Feign的Builder等等。