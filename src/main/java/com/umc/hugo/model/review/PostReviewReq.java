package com.umc.hugo.model.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostReviewReq {
    private int userIdx;
    private int storeIdx;
    private String reviewImgUrl;
    private float reviewStar;
    private String reviewMsg;
    private String ownerComment;
}
