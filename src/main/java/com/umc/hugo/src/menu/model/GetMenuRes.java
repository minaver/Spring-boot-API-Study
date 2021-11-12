package com.umc.hugo.src.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetMenuRes {
    private String name;
    private String menuImgUrl;
    private String menuInfoMsg;
    private int menuPrice;

}
