### 责任链模式

> 顾名思义，责任链模式（Chain of Responsibility Pattern）为请求创建了一个接收者对象的链。这种模式给予请求的类型，对请求的发送者和接收者进行解耦。这种类型的设计模式属于行为型模式。在这种模式中，通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。

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

```java
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
```

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


### 学习Web项目的Filter

待补充...

补充补充遗留的Filter过滤器中的责任链处理。

本次主要是对Tomcat中的Filter处理简单的梳理，如有不正确的地方，还望指出来，大家互勉，共进。

老项目大家可以在web.xml中配置filter，现使用Springboot后，也有两种配置filter方式，通过创建FilterRegistrationBean的方式和通过注解@WebFilter+@ServletComponentScan的方式。

三个主要的角色

FIlter，不多介绍了。

FilterChain  servlet容器提供的开发调用链的过滤请求的资源。通过调用下一个filter实现过滤，在整体链上。

FilterConfig  filter的配置器，在servlet容器在Filter初始化的时候传递信息。

具体的filter，主要说说Spring中的两个抽象Filter，GenericFilterBean和OncePerRequestFilter。

前者主要是做init和destroy的操作，重点还是init方法，destroy只是空实现而已。

后者主要是做真正的doFilter操作，也是我们在Spring中创建Filter通常继承的。



而ApplicationFilterChain就算Tomcat中的FilterChain实现。



```java
    /**
     * The int which is used to maintain the current position
     * in the filter chain.
     */
    private int pos = 0;


    /**
     * The int which gives the current number of filters in the chain.
     */
    private int n = 0;

@Override
public void doFilter(ServletRequest request, ServletResponse response)
    throws IOException, ServletException {
	//安全相关的，暂不关注
    if( Globals.IS_SECURITY_ENABLED ) {
        final ServletRequest req = request;
        final ServletResponse res = response;
        try {
            java.security.AccessController.doPrivileged(
                new java.security.PrivilegedExceptionAction<Void>() {
                    @Override
                    public Void run()
                        throws ServletException, IOException {
                        internalDoFilter(req,res);
                        return null;
                    }
                }
            );
        } catch( PrivilegedActionException pe) {
            Exception e = pe.getException();
            if (e instanceof ServletException)
                throw (ServletException) e;
            else if (e instanceof IOException)
                throw (IOException) e;
            else if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            else
                throw new ServletException(e.getMessage(), e);
        }
    } else {
        //真正的doFilter
        internalDoFilter(request,response);
    }
}


private void internalDoFilter(ServletRequest request,
                              ServletResponse response)
    throws IOException, ServletException {
	//pos 调用链中当前连接点所在的位置
    //n 调用链总节点长度
    // Call the next filter if there is one
    if (pos < n) {
        //对节点进行自增 pos++
        ApplicationFilterConfig filterConfig = filters[pos++];
        try {
            //当前节点小于总长度后，从filter配置类中取出filter
            Filter filter = filterConfig.getFilter();

            if (request.isAsyncSupported() && "false".equalsIgnoreCase(
                    filterConfig.getFilterDef().getAsyncSupported())) {
                request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR, Boolean.FALSE);
            }
            if( Globals.IS_SECURITY_ENABLED ) {
                final ServletRequest req = request;
                final ServletResponse res = response;
                Principal principal =
                    ((HttpServletRequest) req).getUserPrincipal();

                Object[] args = new Object[]{req, res, this};
                SecurityUtil.doAsPrivilege ("doFilter", filter, classType, args, principal);
            } else {
                //真正的filter
                filter.doFilter(request, response, this);
            }
        } catch (IOException | ServletException | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            e = ExceptionUtils.unwrapInvocationTargetException(e);
            ExceptionUtils.handleThrowable(e);
            throw new ServletException(sm.getString("filterChain.filter"), e);
        }
        return;
    }

    // We fell off the end of the chain -- call the servlet instance
	//到了调用链结尾处，就真正调用servlet实例的servlet.service(request, response);
    try {
        if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
            lastServicedRequest.set(request);
            lastServicedResponse.set(response);
        }

        if (request.isAsyncSupported() && !servletSupportsAsync) {
            request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR,
                    Boolean.FALSE);
        }
        // Use potentially wrapped request from this point
        if ((request instanceof HttpServletRequest) &&
                (response instanceof HttpServletResponse) &&
                Globals.IS_SECURITY_ENABLED ) {
            final ServletRequest req = request;
            final ServletResponse res = response;
            Principal principal =
                ((HttpServletRequest) req).getUserPrincipal();
            Object[] args = new Object[]{req, res};
            SecurityUtil.doAsPrivilege("service",
                                       servlet,
                                       classTypeUsedInService,
                                       args,
                                       principal);
        } else {
            servlet.service(request, response);
        }
    } catch (IOException | ServletException | RuntimeException e) {
        throw e;
    } catch (Throwable e) {
        e = ExceptionUtils.unwrapInvocationTargetException(e);
        ExceptionUtils.handleThrowable(e);
        throw new ServletException(sm.getString("filterChain.servlet"), e);
    } finally {
        if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
            lastServicedRequest.set(null);
            lastServicedResponse.set(null);
        }
    }
}

/**
* Prepare for reuse of the filters and wrapper executed by this chain.
* 重复使用filter调用链，pos重设为0
*/
void reuse() {
    pos = 0;
}
```

重点从ApplicationFilterChain中挑出几个重要的方法拿出来分析下Filter的调用链，其实还有几处没有具体讲到，ApplicationFilterChain是合适创建的，Filter是怎么加入到ApplicationFilterChain中的。这涉及到Tomcat是怎样加载Content的，下次分析Tomcat的时候，再来具体分析，它是如何运作的，如何加载web.xml。



### 参考

[维基的责任链模式](https://en.wikipedia.org/wiki/Chain-of-responsibility_pattern)

[责任链模式|菜鸟教程](http://www.runoob.com/design-pattern/chain-of-responsibility-pattern.html)

[Filter、FilterConfig、FilterChain|菜鸟教程](http://www.runoob.com/w3cnote/filter-filterchain-filterconfig-intro.html)

[南乡清水的实际项目运用之Responsibility-Chain模式](https://www.jianshu.com/p/e52d1005d8f1)

[一起学设计模式 - 责任链模式](https://segmentfault.com/a/1190000012148496)

