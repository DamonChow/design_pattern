package com.damon.template.method;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 功能：抽象实现
 * P 请求
 * R 响应
 *
 * @author Damon
 * @since 2019-03-11 18:37
 */
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
