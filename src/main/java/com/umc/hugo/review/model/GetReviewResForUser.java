package com.umc.hugo.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetReviewResForUser {
    private String name;
    private float reviewStar;
    private String reviewImgUrl;
    private String reviewMsg;
}
