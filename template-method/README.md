---
title: 折腾Java设计模式之模板方法模式
copyright: true
comment: true
date: 2019-03-11 18:05:47
categories: 设计模式
tags:
- 设计模式
- 模板方法
---



# 模板方法模式

> Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.
>
> 翻译过来就是，把算法的框架定义好，可以将某些步抽象出来放到子类去实现。模板方法允许子类在不改变算法框架的情况下重新实现算法的某些步骤。
>

<!-- more -->



## 模板方法模式UML图

### UML图

![UML](https://ws3.sinaimg.cn/large/006tKfTcgy1g1g0ekbtxcj30dw06ot90.jpg)



抽象类`AbstractClass`定义了算法框架`templateMethod()`方法，其中有2个方法`primitve1()`和`primitve2()`被抽象出来，子类`SubClass1`继承了抽象类`AbstractClass`，从而实现了`primitve1()`和`primitve2()`。



## 模板方法模式角色

**抽象类(AbstractClass)：** 定义了算法核心框架，同时把局部的算法行为封装成步骤，让子类去实现。

**子类(SubClass):** 继承了抽象类，实现抽象类中的抽象方法，具体实现了算法部分逻辑。



## 模板方法模式源码示例

源码地址：[Template-method](https://github.com/DamonChow/design_pattern/tree/master/template-method)



### 抽象方法

先定义抽象类，抽象类`AbstractProcessor`中核心算法`handle`方法中大体分3部，第一先校验参数具体怎么校验放在子类中实现，第二获取结果也放在子类实现，第三获取结果后的操作也放在子类实现。

```java
@Slf4j
public abstract class AbstractProcessor<P extends Request, R extends Response> {

    /**
     * 逻辑处理
     *
     * @param request
     * @return
     */
    public R handle(P request) {
        // 1.校验参数
        log.info("开始处理, 请求参数={}", request);
        validRequest(request);

        // 2.获取结果
        R response = getResponse(request);
        log.info("获取结果, 响应结果={}", response);

        // 3.结果之后的处理
        afterHandle(response);
        return response;
    }

    /**
     * 结果之后的处理 可以更新其他业务或者处理缓存
     *
     * @param response
     */
    protected abstract void afterHandle(R response);

    /**
     * 校验请求参数
     *
     * @param request
     */
    protected void validRequest(P request) {
        if (Objects.isNull(request.getToken())) {
            throw new RuntimeException("token不能为空");
        }
        if (Objects.isNull(request.getVersion())) {
            throw new RuntimeException("version不能为空");
        }

        validRequestParam(request);
    }

    /**
     * 校验请求真正的参数
     * @param request
     */
    protected abstract void validRequestParam(P request);

    /**
     * 获取到结果
     * @param request
     * @return
     */
    protected abstract R getResponse(P request);
}
```



{% tabs %}

<!-- tab Request -->

基本请求

```java
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

     private String version;

     private String token;
}
```

基本响应

```java
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

     private String msg;

     private int code;

     private boolean success;

}
```

### 子类实现

第一个子类实现

```java
@Slf4j
public class OneProcessor extends AbstractProcessor<OneRequest, OneResponse> {

    public OneProcessor() {
        ProcessorFactory.putProcess("two", this);
    }

    @Override
    protected void afterHandle(OneResponse response) {
        log.info("处理One结果： {}", response.getOne());
    }

    @Override
    protected void validRequestParam(OneRequest request) {
        log.info("校验one参数...省略......");
    }

    @Override
    protected OneResponse getResponse(OneRequest request) {
        String name = request.getName();
                return OneResponse.builder()
                .one(name + "one")
                .success(true)
                .code(0)
                .msg("成功")
                .build();
    }
}
```

第一个子类的请求

```java
@Data
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OneRequest extends Request {

    private String name;

    @Builder.Default
    private int a = 0;

}
```

第一个子类的响应

```java
@Data
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OneResponse extends Response {

    private String one;

}
```

第二个子类实现

```java
@Slf4j
public class TwoProcessor extends AbstractProcessor<TwoRequest, TwoResponse> {

    public TwoProcessor() {
        ProcessorFactory.putProcess("two", this);
    }

    @Override
    protected void afterHandle(TwoResponse response) {
        log.info("处理结果TWO, {}", response);
    }

    @Override
    protected void validRequestParam(TwoRequest request) {
        log.info("校验TWO参数...省略......");
    }

    @Override
    protected TwoResponse getResponse(TwoRequest request) {
        Long id = request.getId();
        return TwoResponse.builder()
                .two("id为"+id)
                .success(true)
                .code(0)
                .msg("成功")
                .build();
    }
}
```

第二个子类的请求

```java
@Data
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TwoRequest extends Request {

    private Long id;
}
```

第二个子类的响应

```java
@Data
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TwoResponse extends Response {

    private String two;
}
```

### 扩展为工厂

有的时候我们定义的子类在Spring容器的时候由Spring定义好后，我们其实可以借用工厂模式方法，在子类初始化的时候就把子类放置在`ProcessorFactory`中，后续只需要根据key从中拿取即可，实际项目中用这种方式还是比较多的。

```java
public class ProcessorFactory {

    private static Map<String, AbstractProcessor> factories = new HashMap();

    public static void putProcess(String key, AbstractProcessor process) {
        factories.put(key, process);
    }

    public static AbstractProcessor selectProcess(String key) {
        return factories.get(key);
    }

}
```

### 执行程序

```java
@Slf4j
public class TemplateMethodApplication {

    public static void main(String[] args) {
        OneRequest oneRequest = OneRequest.builder()
                .version("2312312")
                .token("23423")
                .name("张三")
                .build();
        new OneProcessor().handle(oneRequest);

        log.info("--------------------------");

        TwoRequest twoRequest = TwoRequest.builder()
                .version("2312312")
                .token("23423")
                .id(23456L)
                .build();
        new TwoProcessor().handle(twoRequest);


    }
}
```

### 结果

![image-20190326112956730](https://ws1.sinaimg.cn/large/006tKfTcgy1g1g1dxi2iyj32du0ccdll.jpg)

总体上来讲，抽象类中定义了算法的框架，然后把部分算法步骤抽象出来供子类实现，有的时候有些方法只有个别子类会去实现，我们可以在抽象类中实现为空实现，在有需要的子类中我们可以覆盖抽象类的实现，这样避免了所有的子类都去实现，其实不关心的话都是空实现了。本示例用到了`lombok`的`@SuperBuilder`特性，可能在下载完完整的代码后会出现编译错误，这是因为`Lombok`的插件暂时还不支持`@SuperBuilder`。



## 模板方法模式总结

模板方法经常和其他模式混合使用，比如工厂、策略等等。其实在Spring中大量使用了模板方法模式，其实也不光在Spring中，像平时jdbcTemplate或者RedisTemplate，像这种带有Template的。

**优点：** 1、封装不变部分，扩展可变部分。 2、提取公共代码，便于维护。 3、行为由父类控制，子类实现。

**缺点：**每一个不同的实现都需要一个子类来实现，导致类的个数增加，使得系统更加庞大。

**使用场景：** 1、有多个子类共有的方法，且逻辑相同。 2、重要的、复杂的方法，可以考虑作为模板方法。

**注意事项：**为防止恶意操作，一般模板方法都加上 final 关键词。



## 参考

[模板模式|菜鸟教程](http://www.runoob.com/design-pattern/template-pattern.html)

[Template method pattern](https://en.wikipedia.org/wiki/Template_method_pattern)