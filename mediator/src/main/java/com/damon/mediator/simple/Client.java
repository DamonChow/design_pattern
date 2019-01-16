package com.damon.mediator.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：示例
 *
 * @author Damon
 * @since 2019-01-16 14:27
 */
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
