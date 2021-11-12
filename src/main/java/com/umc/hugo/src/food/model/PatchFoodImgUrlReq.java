package com.umc.hugo.src.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatchFoodImgUrlReq {
    private int foodIdx;
    private String foodImgUrl;
}
