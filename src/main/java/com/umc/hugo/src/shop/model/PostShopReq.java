package com.umc.hugo.src.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostShopReq {
    public PostShopReq(){}

    private int userIdx;
    private int menuIdx;
    private int menuNum;
}
