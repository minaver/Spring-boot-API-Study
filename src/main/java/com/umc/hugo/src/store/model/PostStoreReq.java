package com.umc.hugo.src.store.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
public class PostStoreReq {
    private int foodIdx;
    private int ownerIdx;
    private String name;
    private String storeImgUrl;
    private String storeInfoMsg;
    private String availableWay;
    private float storeStar;
    private int starNum;
    private int reviewNum;
    private String deliveryTimeMsg;
    private int leastPriceMsg;
    private String deliveryTipMsg;
}
