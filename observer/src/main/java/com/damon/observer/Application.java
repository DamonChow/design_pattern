package com.damon.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-01-24 15:19
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Blog blog = new Blog();
        VipObserver wang = new VipObserver("老王99");
        VipObserver lee = new VipObserver("老李");
        NormalObserver four = new NormalObserver("小四");
        NormalObserver twoEgg = new NormalObserver("二蛋");
        log.info("---------------------begin--------------------");

        // 用户订阅博客，普通和vip用户
        new Thread(() -> {
            blog.register(wang);
            sleep(2);
            blog.register(lee);
            sleep(2);
            blog.register(four);
        }).start();

        // 博客线程每隔2秒发布一次文章,  总共发布4次
        new Thread(() -> {
            IntStream.rangeClosed(1, 4).forEach(i -> {
                blog.publish(String.format("新把戏第%s次", i));
                sleep(2);
            });
        }).start();

        // 有用户退出订阅博客，也有二蛋加入订阅
        new Thread(() -> {
            sleep(3);
            blog.remove(lee);
            sleep(2);
            blog.register(twoEgg);
        }).start();
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error("error : ", e);
        }
    }
}
