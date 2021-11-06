package com.umc.hugo.model.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetReviewRes {
    private String storeName;
    private String userName;
    private float reviewStar;
    private String reviewImgUrl;
    private String reviewMsg;
}
