# 中介者模式

> 中介者模式（Mediator Pattern）是用来降低多个对象和类之间的通信复杂性。这种模式提供了一个中介类，该类通常处理不同类之间的通信，并支持松耦合，使代码易于维护。中介者模式属于行为型模式。

通俗点来讲就是提供一个中介平台，说到平台，那其实很容易联系到我们很熟悉的房地产中介。我们可以直接通过这个平台得到我们想要的信息，不用对象自身与其他对象交互。

买房子租房子就不需要去找房东，只需要在中介那里获取相应的房产信息。如下图那样，两方只需要找中介即可。

![房地产中介](https://ws1.sinaimg.cn/large/006tNc79gy1fz7b4m32qsj30dw0av74t.jpg)



再来看一张对比图。

![中介者前后对比](https://ws2.sinaimg.cn/large/006tNc79gy1fz7b6s1hx1j30o709x3zn.jpg)

有没有感觉没有使用之前，对象间互相依赖互相调用，错综复杂，盘根错节，当加入中介者后，对象间的关系一目了然，清晰明了。由中介对象来封装一系列对象之间的交互关系。中介者使各个对象之间不需要显式地相互引用，从而使耦合性降低，而且可以独立地改变它们之间的交互行为。



# 中介者模式UML图

## UML类图和时序图

![类图和时序图](https://ws2.sinaimg.cn/large/006tNc79gy1fz7bbp35cxj30go06o74t.jpg)

collague1和collague2类不直接相互依赖，它们是用起控制和协调交互作用的公共中介接口（mediate（）方法），这使它们有独立交互的执行方式。mediate1类实现collague1和collague2之间的依赖。



## 中介者模式角色

![角色](https://ws3.sinaimg.cn/large/006tNc79gy1fz7btn37e2j30hm05c3ys.jpg)

> 抽象中介者(Mediator): 定义了同事对象到中介者对象之间的接口。
>
> 具体中介者(ConcreteMediator): 实现抽象中介者的方法，它需要知道所有的具体同事类，同时需要从具体的同事类那里接收信息，并且向具体的同事类发送信息。
>
> 抽象同事类(Colleague): 定义了中介者对象的接口，它只知道中介者而不知道其他的同事对象。
>
> 具体同事类(ConcreteColleague) : 每个具体同事类都只需要知道自己的行为即可，但是他们都需要认识中介者。



# 示例代码

[源码地址](https://github.com/DamonChow/design_pattern/tree/master/mediator)

抽象中介者

```java
@Slf4j
public abstract class Mediator {

    /**
     * 房东map
     */
    protected Map<People, Message> landlordMap = Maps.newHashMap();

    /**
     * 租户列表
     */
    protected List<People> renterList = Lists.newArrayList();

    /**
     * 注册房东信息
     *
     * @param landlord 房东
     * @param message  房屋信息
     */
    public void registerLandlord(People landlord, Message message) {
        landlordMap.put(landlord, message);
        log.info("中介信息：房东|{}|加入到中介平台，房屋信息：{}", landlord.getName(), message);
    }

    /**
     * 变更房东信息
     *
     * @param landlord 房东
     * @param message  房屋信息
     */
    protected void modifyLandlordInfo(People landlord, Message message) {
        landlordMap.put(landlord, message);
        log.info("中介信息：房东|{}|修改他在中介平台的房屋信息，现房屋信息：{}", landlord.getName(), message);
    }

    /**
     * 注册租户信息
     *
     * @param renter 租户
     */
    public void registerRenter(People renter) {
        renterList.add(renter);
        log.info("中介信息：租户|{}|来中介平台租房", renter.getName());
    }


    /**
     * 声明抽象方法 由具体中介者子类实现 消息的中转和协调
     */
    public abstract void operation(People people, Message message);

}
```

抽象同事类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class People {

    private Mediator mediator;

    private String name;

    /**
     * 向中介发送消息
     */
    protected abstract void sendMessage(Message message);

    /**
     * 从中介获取消息
     */
    protected abstract void getMessage(Message message);
}
```

具体同事类 房东和租户

```java
@Slf4j
public class Landlord extends People {

    public Landlord(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    protected void sendMessage(Message message) {
        getMediator().operation(this, message);
    }

    @Override
    protected void getMessage(Message message) {
        log.info("房东|{}|从中介获取到租户的信息：{}", getName(), message);
    }
}
```

```java
@Slf4j
public class Renter extends People {

    public Renter(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    protected void sendMessage(Message message) {
        getMediator().operation(this, message);
    }

    @Override
    protected void getMessage(Message message) {
        log.info("租户|{}|从中介获取到房东的信息：{}", getName(), message);
    }
}
```

具体中介者

```java
public class RealEstateAgent extends Mediator {

    @Override
    public void operation(People people, Message message) {
        if (people instanceof Renter) {
            // 将租户的租房条件信息发送给房东们
            landlordMap.keySet().forEach(landlord -> landlord.getMessage(message));

            // 租户收到中介那里房东的房屋信息
            landlordMap.values().forEach(messages -> people.getMessage(messages));
        } else if (people instanceof Landlord) {
            // 将房东的房屋信息发送给租户们
            renterList.forEach(renter -> renter.getMessage(message));
            // 变更中介里的房东房屋信息
            modifyLandlordInfo(people, message);
        }
    }
}
```

客户端

```java
@Slf4j
public class Client {

    public static void main(String[] args) {
        Mediator mediator = new RealEstateAgent();
        People laoWang = new Landlord(mediator, "老王");
        People laoLee = new Landlord(mediator, "老李");
        People laoBai = new Landlord(mediator, "老白");

        People xiaoSan = new Renter(mediator, "小3");
        People xiaoWang = new Renter(mediator, "小王");

        mediator.registerLandlord(laoWang, Message.builder().msg("我这有2500的房子，市中心").build());
        mediator.registerLandlord(laoBai, Message.builder().msg("我这有2000的房子，地铁旁").build());
        mediator.registerLandlord(laoLee, Message.builder().msg("我这有2000的房子，落地阳台，大空间，采光好，地铁旁").build());

        mediator.registerRenter(xiaoSan);

        log.info("小3开始找房子");
        xiaoSan.sendMessage(Message.builder().msg("想找个月租2000块的房子，靠近地铁").build());
        log.info("没过多久---------老白升级了房屋信息");
        laoBai.sendMessage(Message.builder().msg("我这有2000的房子，地铁旁，我又加了空调和热水器").build());
        mediator.registerRenter(xiaoWang);
        log.info("小王开始找房子");
        xiaoWang.sendMessage(Message.builder().msg("想找个月租2500块的房子，靠近地铁").build());
        log.info("没过多久---------老李也升级了房屋信息");
        laoBai.sendMessage(Message.builder().msg("我这有2000的房子，落地阳台，大空间，采光好，地铁旁，我也加了空调和热水器").build());

    }
}
```

最终出效果的如下

![效果](https://ws3.sinaimg.cn/large/006tNc79gy1fz8fv2diwej326f0u04iw.jpg)



现在我来分析下里面各个角色的作用：

首先先分析两个抽象类。抽象同事类，含有名称和中介者的引用，有2个方法从中介拿信息和发信息给中介。抽象中介者，其中含有房东的map信息，key为房东数据，value为房东的房屋信息，用来房东注册和房东房屋信息变更；租户的列表信息，供租户注册，同时还有个协调方法，用于协调房东和租户。

具体抽象类（房地产中介），实现了抽象中介者的协调方法，当租户发送消息时，将租户的租房条件信息发送给所有房东们同时该租户收到中介那里所有房东的房屋信息；当房东发送消息时，将房东的房屋信息发送给所有租户们同时变更中介里的改房东房屋信息。

具体同事实现类（房东和租户），实现了抽象同事类的读取消息方法和发送消息方法（该房屋就是依靠中介者的协调方法来实现）。





# JDK中的应用

通过使用一个中间对象来进行消息分发以及减少类之间的直接依赖。

> java.util.Timer
>
> java.util.concurrent.Executor#execute()
>
> java.util.concurrent.ExecutorService#submit()
>
> java.lang.reflect.Method#invoke()



# 总结

## 优点

使用中介者模式可以把对个同事对象之间的交互封装到中介者对象里面，从而使得同事对象之间松散耦合。
中介者模式可以将原先多对多的同事对象关系变成中介者对象一对多同事对象的关系，这样会让对象之间的关系更容易理解和实现。
同事对象之间的交互都被封装到中介者对象里面集中管理，集中了控制交互。当交互发生改变时，着重修改的是中介者对象。当需要扩展中介者对象时，其他同事对象不需要做修改。



## 缺点

如果同事对象多了，交互也复杂了。中介者会庞大，变得复杂难以维护。



# 参考

[中介者模式|菜鸟教程](http://www.runoob.com/design-pattern/mediator-pattern.html)

[Mediator pattern](https://en.wikipedia.org/wiki/Mediator_pattern)

[细数JDK里的设计模式](https://www.cnblogs.com/tinyking/p/5938547.html)

[JAVA设计模式之 中介者模式【Mediator Pattern】](https://blog.csdn.net/janice0529/article/details/41685175)