package com.umc.hugo.src.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatchFoodReq {
    private int foodIdx;
    private String name;
}
