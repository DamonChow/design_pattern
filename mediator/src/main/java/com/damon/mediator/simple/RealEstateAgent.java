package com.damon.mediator.simple;

/**
 * 功能：房地产中介（具体中介实现）
 *
 * @author Damon
 * @since 2019-01-16 14:11
 */
public class RealEstateAgent extends Mediator {

    @Override
    public void operation(People people, Message message) {
        if (people instanceof Renter) {
            // 将租户的租房条件信息发送给房东们
            landlordMap.keySet().forEach(landlord -> landlord.getMessage(message));

            // 租户收到中介那里房东的房屋信息
            landlordMap.values().forEach(messages -> people.getMessage(messages));
        } else if (people instanceof Landlord) {
            // 将房东的房屋信息发送给租户们
            renterList.forEach(renter -> renter.getMessage(message));
            // 变更中介里的房东房屋信息
            modifyLandlordInfo(people, message);
        }
    }
}
