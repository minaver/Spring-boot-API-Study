package com.umc.hugo.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostFoodReq {
    private String name;
    private String foodImgUrl;
}
