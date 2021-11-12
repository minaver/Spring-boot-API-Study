package com.umc.hugo.src.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Review {
    public Review(){}

    private int reviewIdx;
    private int userIdx;
    private int storeIdx;
    private String reviewImgUrl;
    private float reviewStar;
    private String reviewMsg;
    private String ownerComment;
    private String status;

}
