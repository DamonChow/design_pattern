package com.damon.template.method.app;

import com.damon.template.method.one.OneProcessor;
import com.damon.template.method.one.OneRequest;
import com.damon.template.method.two.TwoProcessor;
import com.damon.template.method.two.TwoRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-25 18:14
 */
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
