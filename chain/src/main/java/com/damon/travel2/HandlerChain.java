package com.damon.travel2;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 功能：链管理者，链上所有节点都在此管理
 *
 * @author Damon
 * @since 2018-12-23 21:54
 */
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
