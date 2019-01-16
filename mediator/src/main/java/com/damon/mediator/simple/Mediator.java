package com.damon.mediator.simple;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 功能：抽象中介者
 *
 * @author Damon
 * @since 2019-01-16 11:57
 */
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
