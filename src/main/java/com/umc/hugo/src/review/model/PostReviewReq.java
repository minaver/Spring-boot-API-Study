package com.umc.hugo.src.review.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
public class PostReviewReq {

    private int userIdx;
    private int storeIdx;
    private String reviewImgUrl;
    private float reviewStar;
    private String reviewMsg;
}
