package com.umc.hugo.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetStoreRes {
    private String storeImgUrl;
    private String name;
    private String availableWay;
    private String storeStar;
    private String starNum;
    private String shortMenuMsg;
    private String leastPriceMsg;
    private String deliveryTipMsg;
    private String deliveryTimeMsg;
}
