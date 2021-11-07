package com.umc.hugo.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostMenuReq {
    private int storeIdx;
    private String name;
    private String menuImgUrl;
    private String menuInfoMsg;
    private int menuPrice;
}
