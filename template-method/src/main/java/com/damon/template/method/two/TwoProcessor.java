package com.damon.template.method.two;

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
