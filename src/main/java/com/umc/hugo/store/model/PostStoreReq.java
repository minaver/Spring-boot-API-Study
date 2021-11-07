package com.umc.hugo.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostStoreReq {
    private int foodIdx;
    private String name;
    private String storeImgUrl;
    private String storeInfoMsg;
    private String availableWay;
    private float storeStar;
    private String starNum;
    private int reviewNum;
    private String deliveryTimeMsg;
    private String leastPriceMsg;
    private String deliveryTipMsg;
}
