package com.umc.hugo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatchReviewMsgReq {
    private int reviewIdx;
    private String reviewMsg;
}
