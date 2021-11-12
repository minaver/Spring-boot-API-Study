package com.umc.hugo.src.food;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Food {
    private String name;
    private String foodImgUrl;
    private String status;
}
