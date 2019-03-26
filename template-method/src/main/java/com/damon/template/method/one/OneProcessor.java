package com.damon.template.method.one;

import com.damon.template.method.AbstractProcessor;
import com.damon.template.method.ProcessorFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 *
 * @author Damon
 * @since 2019-03-25 17:50
 */
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
