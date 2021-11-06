package com.umc.hugo.model.Food;

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
