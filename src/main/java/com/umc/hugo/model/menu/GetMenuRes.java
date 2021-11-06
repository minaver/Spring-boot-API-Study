package com.umc.hugo.model.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetMenuRes {
    private String storeName;
    private String name;
    private String menuImgUrl;
    private String menuInfoMsg;
    private int menuPrice;

}
