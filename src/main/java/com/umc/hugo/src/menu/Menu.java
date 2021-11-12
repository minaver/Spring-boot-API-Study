package com.umc.hugo.src.menu;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Menu {
    private int menuIdx;
    private int storeIdx;
    private String name;
    private String menuImgUrl;
    private String menuInfoMsg;
    private int menuPrice;
    private String status;

}

